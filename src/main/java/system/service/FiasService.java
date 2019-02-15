package system.service;

import com.google.common.base.Charsets;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import system.domain.AddrObject;
import system.domain.House;
import system.domain.Room;
import system.domain.Stead;
import system.domain.User;
import system.dto.AddrObjectDto;
import system.dto.HouseDto;
import system.dto.RoomDto;
import system.dto.SteadDto;
import system.dto.UserDto;
import system.mapper.AddrObjectMapper;
import system.mapper.HouseMapper;
import system.mapper.RoomMapper;
import system.mapper.SteadMapper;
import system.mapper.UserMapper;
import system.repository.AddrObjectRepository;
import system.repository.HouseRepository;
import system.repository.RoomRepository;
import system.repository.SteadRepository;
import system.repository.UserRepository;
import system.repository.VersionRepository;
import system.security.TokenAuthenticationManager;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Log4j2
@Service
@RequiredArgsConstructor
public class FiasService {

    static final boolean AUTO_UPDATE = false;
    private static final String MAIN_PATH = "D:/Fias/";

    private FiasModuleStatus fiasModuleStatus = FiasModuleStatus.WORKING;
    private final Downloader downloader;
    private final Unrarrer unrarrer;
    private final Installer installer;
    private final Deleter deleter;
    private final FiasHelper fiasHelper;
    private final VersionRepository versionRepository;
    private final AddrObjectRepository addrObjectRepository;
    private final HouseRepository houseRepository;
    private final EntityManager entityManager;
    private final SteadRepository steadRepository;
    private final RoomRepository roomRepository;
    private final AddrObjectMapper addrObjectMapper;
    private final HouseMapper houseMapper;
    private final SteadMapper steadMapper;
    private final RoomMapper roomMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenAuthenticationManager tokenAuthenticationManager;

    public void installComplete() {
        if (isNull(getCurrentVersion())) {
            String lastVersion = getLastVersion();
            if (nonNull(lastVersion)) {
                fiasModuleStatus = FiasModuleStatus.UPDATING;
                try {
                    String mainPath = getMainPath();
                    downloader.downloadLastComplete(mainPath, lastVersion);
                    unrarrer.unrarLastComplete(mainPath, lastVersion);
                    installer.installLastComplete(mainPath, lastVersion);
                    deleter.deleteCompleteFiles(mainPath, lastVersion);
                } catch (FiasException e) {
                    fiasModuleStatus = FiasModuleStatus.UPDATE_ERROR;
                    return;
                }
                fiasModuleStatus = FiasModuleStatus.WORKING;
            }
        } else log.info("Complete database exists, install update");
    }

    public void installUpdates(Boolean isOneUpdate) {
        if (nonNull(getCurrentVersion())) {
            List<String> listOfNewVersions = getListOfNewVersions();
            if (nonNull(listOfNewVersions)) {
                fiasModuleStatus = FiasModuleStatus.UPDATING;
                try {
                    for (String deltaVersion : listOfNewVersions) {
                        String mainPath = getMainPath();
                        downloader.downloadDeltaByVersion(mainPath, deltaVersion);
                        unrarrer.unrarDeltaByVersion(mainPath, deltaVersion);
                        installer.installDeltaByVersion(mainPath, deltaVersion);
                        deleter.deleteDeltaFiles(mainPath, deltaVersion);
                        if (isOneUpdate) break;
                    }
                } catch (FiasException e) {
                    fiasModuleStatus = FiasModuleStatus.UPDATE_ERROR;
                    return;
                }
                fiasModuleStatus = FiasModuleStatus.WORKING;
            }
        } else log.info("Complete database not exist");
    }

    String getMainPath() {
        if (new File(MAIN_PATH).exists()) return MAIN_PATH;
        log.warn(String.format("Путь %s не найдён", MAIN_PATH));
        return System.getProperty("java.io.tmpdir");
    }

    public String getCurrentVersion() {
        return versionRepository.getCurrentVersion();
    }

    public String getLastVersion() {
        StringBuilder res = new StringBuilder();
        String updUrlStr = "https://fias.nalog.ru/Public/Downloads/Actual/VerDate.txt";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(updUrlStr).openStream()))) {
            String[] strings = in.readLine().split("\\.");
            for (int i = strings.length - 1; i >= 0; i--) res.append(strings[i]);
        } catch (IOException e) {
            log.error(e);
            return null;
        }
        return res.toString();
    }

    public List<String> getListOfNewVersions() {
        String currentVersion = getCurrentVersion();
        if (currentVersion == null) return null;
        String url = "https://fias.nalog.ru/WebServices/Public/DownloadService.asmx";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("Host", "fias.nalog.ru");
        post.setHeader("Content-Type", "text/xml;charset=utf-8");
        post.setHeader("SOAPAction", "https://fias.nalog.ru/WebServices/Public/DownloadService.asmx/GetAllDownloadFileInfo");

        StringEntity xmlString = null;
        try {
            xmlString = new StringEntity(
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "  <soap:Body>\n" +
                            "    <GetAllDownloadFileInfo xmlns=\"https://fias.nalog.ru/WebServices/Public/DownloadService.asmx\" />\n" +
                            "  </soap:Body>\n" +
                            "</soap:Envelope>"
            );
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        post.setEntity(xmlString);

        List<String> versions = new ArrayList<>();
        StringBuilder res = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(client.execute(post).getEntity().getContent()))) {
            String line = br.readLine().replaceAll("/", "");
            String[] text = line.split("<TextVersion>");
            for (int i = 0; i < text.length; i++) {
                if (i % 2 != 0) {
                    String[] strings = text[i].split(" ")[3].split("\\.");
                    for (int j = strings.length - 1; j >= 0; j--) res.append(strings[j]);
                    if (Integer.parseInt(res.toString()) > Integer.parseInt(currentVersion)) {
                        versions.add(res.toString());
                    }
                    res.setLength(0);
                }
            }
        } catch (IOException e) {
            log.error(e);
            return null;
        }
        return versions;
    }


    public List<AddrObjectDto> getAddrObjectsStartList() {
        List<AddrObject> addrObjects = addrObjectRepository.getAddrObjectsStartList();
        for (AddrObject addrObject : addrObjects) {
            addrObject.setSHORTNAME(fiasHelper.getFullAddrObjectType(addrObject));
            if (addrObject.getFORMALNAME().contains("Чувашская")) addrObject.setFORMALNAME("Чувашская");
        }
        return addrObjectMapper.toDto(addrObjects);
    }

    public List<AddrObjectDto> getAddrObjectsByParentGuid(String guid, Boolean isActual) {
        guid = guid.replaceAll("-", "").toLowerCase();
        if (guid.length() != 32) return new ArrayList<>();
        if (isNull(isActual)) isActual = false;
        List<AddrObject> addrObjects = isActual ? addrObjectRepository.getActualAddrObjectsByParentGuid(guid) :
                addrObjectRepository.getAddrObjectsByParentguid(guid);
        if (!isActual) addrObjects = fiasHelper.getObjectsWithMaxEnddate(addrObjects);
        for (AddrObject addrObject : addrObjects) addrObject.setSHORTNAME(fiasHelper.getFullAddrObjectType(addrObject));
        return addrObjectMapper.toDto(addrObjects);
    }

    public List<HouseDto> getHousesByParentGuid(String guid, Boolean isActual) {
        guid = guid.replaceAll("-", "").toLowerCase();
        if (guid.length() != 32) return new ArrayList<>();
        if (isNull(isActual)) isActual = false;
        List<House> houses = houseRepository.getHousesByParentguid(guid);
        if (isActual) {
            int currentDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
            houses.removeIf(house -> currentDate > house.getENDDATE());
        } else houses = fiasHelper.getObjectsWithMaxEnddate(houses);
        for (House house : houses) {
            house.setType(fiasHelper.getHouseType(house));
            house.setName(fiasHelper.getHouseName(house));
        }
        return houseMapper.toDto(houses);
    }

    public List<SteadDto> getSteadsByParentGuid(String guid, Boolean isActual) {
        guid = guid.replaceAll("-", "").toLowerCase();
        if (guid.length() != 32) return new ArrayList<>();
        if (isNull(isActual)) isActual = false;
        List<Stead> steads = isActual ? steadRepository.getActualSteadsByParentguid(guid) :
                steadRepository.getSteadsByParentguid(guid);
        if (!isActual) steads = fiasHelper.getObjectsWithMaxEnddate(steads);
        return steadMapper.toDto(steads);
    }

    public List<RoomDto> getRoomsListByParentGuid(String guid, Boolean isActual) {
        guid = guid.replaceAll("-", "").toLowerCase();
        if (guid.length() != 32) return new ArrayList<>();
        if (isNull(isActual)) isActual = false;
        List<Room> rooms = isActual ? roomRepository.getActualRoomsByHouseguid(guid) :
                roomRepository.getRoomsByHouseguid(guid);
        if (!isActual) rooms = fiasHelper.getObjectsWithMaxEnddate(rooms);
        for (Room room : rooms) room.setType(fiasHelper.getRoomType(room));
        return roomMapper.toDto(rooms);
    }

    public List<AddrObjectDto> getAddrObjectsByName(String name, NameSearchType type, Boolean isActual) {
        if (isNull(isActual)) isActual = false;
        if (isNull(type)) type = NameSearchType.ALL;
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        String queryPart = isActual ? " and a.LIVESTATUS=1" : "";
        queryPart += type.getQueryPart();
        List<AddrObject> addrObjects = entityManager.createQuery(
                "select new AddrObject(a.AOGUID, a.PARENTGUID, a.POSTALCODE, a.FORMALNAME, a.SHORTNAME, a.AOLEVEL, a.ENDDATE) " +
                        "from AddrObject a where a.FORMALNAME like '" + name + "%'" + queryPart, AddrObject.class).getResultList();
        if (!isActual) addrObjects = fiasHelper.getObjectsWithMaxEnddate(addrObjects);
        for (AddrObject addrObject : addrObjects)
            addrObject.setFullAddress(fiasHelper.getFullAddress(addrObject, addrObject.getPOSTALCODE()));
        return addrObjectMapper.toDto(addrObjects);
    }

    public CustomPair searchOldAddrObjectsByGuid(String guid) {
        guid = guid.replaceAll("-", "").toLowerCase();
        if (guid.length() != 32) return null;
        List<AddrObject> addrObjects = addrObjectRepository.getAddrObjectsByAoguid(guid);
        if (addrObjects.isEmpty()) return null;
        addrObjects.sort(Comparator.comparing(AddrObject::getENDDATE));
        AddrObject actual = addrObjects.get(addrObjects.size() - 1).getLIVESTATUS() == 1 ?
                addrObjects.remove(addrObjects.size() - 1) : null;
        List<AddrObject> old = !addrObjects.isEmpty() ? addrObjects : null;
        return new CustomPair(addrObjectMapper.toDto(old), addrObjectMapper.toDto(actual));
    }

    public List<Object> searchObjectsByParameters(String guid, String postalcode, List<ParameterSearchType> types, Boolean isActual) {
        if (isNull(guid) && isNull(postalcode)) return new ArrayList<>();
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        if (nonNull(guid)) {
            guid = guid.replaceAll("-", "").toLowerCase();
            if (guid.length() != 32) return new ArrayList<>();
            else params.put("guid", guid);
        }
        if (nonNull(postalcode)) {
            if (postalcode.length() != 6) return new ArrayList<>();
            else params.put("postalcode", postalcode);
        }
        if (params.isEmpty()) return new ArrayList<>();
        if (isNull(isActual)) isActual = false;
        if (isNull(types)) types = Collections.singletonList(ParameterSearchType.ALL);

        List<Object> res = new ArrayList<>();
        if (types.contains(ParameterSearchType.ALL) || types.contains(ParameterSearchType.ADDRESS_OBJECT)) {
            List<AddrObject> addrObjects = fiasHelper.getAddrObjectsByParams(params, isActual);
            if (!addrObjects.isEmpty()) res.addAll(addrObjectMapper.toDto(addrObjects));
        }
        if (types.contains(ParameterSearchType.ALL) || types.contains(ParameterSearchType.HOUSE)) {
            List<House> houses = fiasHelper.getHousesByParams(params, isActual);
            if (!houses.isEmpty()) res.addAll(houseMapper.toDto(houses));
        }
        if (types.contains(ParameterSearchType.ALL) || types.contains(ParameterSearchType.STEAD)) {
            List<Stead> steads = fiasHelper.getSteadsByParams(params, isActual);
            if (!steads.isEmpty()) res.addAll(steadMapper.toDto(steads));
        }
        if (types.contains(ParameterSearchType.ALL) || types.contains(ParameterSearchType.ROOM)) {
            List<Room> rooms = fiasHelper.getRoomsByParams(params, isActual);
            if (!rooms.isEmpty()) res.addAll(roomMapper.toDto(rooms));
        }
        return res;
    }

    public Object searchObjectByGuid(String guid) {
        guid = guid.replaceAll("-", "").toLowerCase();
        if (guid.length() != 32) return null;

        AddrObject addrObject = fiasHelper.getAddrObjectByGuid(guid);
        if (nonNull(addrObject)) {
            addrObject.setFullAddress(fiasHelper.getFullAddress(addrObject, addrObject.getPOSTALCODE()));
            return addrObjectMapper.toDto(addrObject);
        }
        House house = fiasHelper.getHouseByGuid(guid);
        if (nonNull(house)) {
            house.setFullAddress(fiasHelper.getFullAddress(house, house.getPOSTALCODE()));
            return houseMapper.toDto(house);
        }
        Stead stead = fiasHelper.getSteadByGuid(guid);
        if (nonNull(stead)) {
            stead.setFullAddress(fiasHelper.getFullAddress(stead));
            return steadMapper.toDto(stead);
        }
        Room room = fiasHelper.getRoomByGuid(guid);
        if (nonNull(room)) {
            room.setFullAddress(fiasHelper.getFullAddress(room));
            return roomMapper.toDto(room);
        }
        return null;
    }

    @SneakyThrows
    public String getFullAddress(String guid) {
        Object object = searchObjectByGuid(guid);
        if (object == null) return null;
        return (String) FieldUtils.readField(
                FieldUtils.getField(object.getClass(), "fullAddress", true), object, true);
    }

    public FiasModuleStatus getFiasModuleStatus() {
        return fiasModuleStatus;
    }

    public boolean signUp(String name, String password) {
        if (name.replaceAll(" ", "").equals("") ||
                password.replaceAll(" ", "").equals("")) return false;
        User oldUser = userRepository.findByName(name).orElse(null);
        if (isNull(oldUser)) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            newUser.setRole("ROLE_USER");
            newUser.setIsEnable(true);
            userRepository.save(newUser);
            return true;
        }
        return false;
    }

    public Boolean signIn(String name, String password) {
        SecurityContextHolder.getContext().setAuthentication(tokenAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(name, password)));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return nonNull(authentication);
    }

    public List<UserDto> getAllUsersWithoutPasswords() {
        return userMapper.toDto(userRepository.getByRole("ROLE_USER"));
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public void blockUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (isNull(user)) return;
        user.setIsEnable(!user.getIsEnable());
        userRepository.save(user);
    }

    public List<String> getCurrentUserInfo() {
        return tokenAuthenticationManager.getCurrentUserInfo();
    }

    @SneakyThrows
    public String lastLog() {
        return Files.lines(Paths.get(MAIN_PATH + "fiasLogs/lastLog.log"), Charsets.UTF_8)
                .reduce("", (str1, str2) -> str1 + "<br>" + str2)
                .replaceFirst("<br>", "");
    }
}