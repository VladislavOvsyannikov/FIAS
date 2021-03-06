package fias.service;

import com.google.common.base.Charsets;
import fias.domain.AddrObject;
import fias.domain.House;
import fias.domain.NormativeDocument;
import fias.domain.NormativeDocumentType;
import fias.domain.Room;
import fias.domain.Stead;
import fias.dto.AddrObjectDto;
import fias.dto.HouseDto;
import fias.dto.NormativeDocumentDto;
import fias.dto.RoomDto;
import fias.dto.SteadDto;
import fias.mapper.AddrObjectMapper;
import fias.mapper.HouseMapper;
import fias.mapper.NormativeDocumentMapper;
import fias.mapper.RoomMapper;
import fias.mapper.SteadMapper;
import fias.repository.AddrObjectRepository;
import fias.repository.HouseRepository;
import fias.repository.NormativeDocumentRepository;
import fias.repository.NormativeDocumentTypeRepository;
import fias.repository.RoomRepository;
import fias.repository.SteadRepository;
import fias.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

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
@PropertySource({"classpath:fias.properties", "classpath:log4j2.properties"})
@RequiredArgsConstructor
public class FiasService {

    private FiasModuleStatus fiasModuleStatus = FiasModuleStatus.WORKING;
    private final Downloader downloader;
    private final Unrarer unrarer;
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
    private final NormativeDocumentRepository normativeDocumentRepository;
    private final NormativeDocumentMapper normativeDocumentMapper;
    private final NormativeDocumentTypeRepository normativeDocumentTypeRepository;
    private final Environment environment;

    public void installComplete() {
        if (isNull(getCurrentVersion())) {
            String lastVersion = getLastVersion();
            if (nonNull(lastVersion)) {
                fiasModuleStatus = FiasModuleStatus.UPDATING;
                try {
                    String mainPath = getMainPath();
                    downloader.downloadLastComplete(mainPath, lastVersion);
                    unrarer.unrarLastComplete(mainPath, lastVersion);
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
                        unrarer.unrarDeltaByVersion(mainPath, deltaVersion);
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
        String mainPath = environment.getProperty("path");
        if (new File(mainPath).exists()) return mainPath;
        log.warn(String.format("Путь %s не найдён", mainPath));
        return System.getProperty("java.io.tmpdir");
    }

    public String getCurrentVersion() {
        return versionRepository.getVersions().get(0);
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
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
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

    public List<Object> searchObjectsByParameters(String guid, String postalcode, String cadnum, String okato,
                                                  String oktmo, String fl, String ul, List<ParameterSearchType> types,
                                                  Boolean isActual) {
        LinkedHashMap<String, String> params = getParameters(guid, postalcode, cadnum, okato, oktmo, fl, ul);
        if (isNull(params) || params.isEmpty()) return new ArrayList<>();
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

    private LinkedHashMap<String, String> getParameters(String guid, String postalcode, String cadnum, String okato,
                                                        String oktmo, String fl, String ul) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        if (nonNull(guid)) {
            guid = guid.replace("-", "").toLowerCase();
            if (guid.length() != 32) return null;
            else params.put("guid", guid);
        }
        if (nonNull(postalcode)) {
            if (postalcode.length() != 6) return null;
            else params.put("postalcode", postalcode);
        }
        if (nonNull(cadnum) && cadnum.length() > 10) params.put("cadnum", cadnum);
        if (nonNull(okato)) {
            if (okato.length() != 11) return null;
            else params.put("okato", okato);
        }
        if (nonNull(oktmo)) {
            if (oktmo.length() != 8 && oktmo.length() != 11) return null;
            else params.put("oktmo", oktmo);
        }
        if (nonNull(fl)) {
            if (fl.length() != 4) return null;
            else params.put("ifnsfl", fl);
        }
        if (nonNull(ul)) {
            if (ul.length() != 4) return null;
            else params.put("ifnsul", ul);
        }
        return params;
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

    public NormativeDocumentDto getNormativeDocument(String id) {
        id = id.replaceAll("-", "").toLowerCase();
        if (id.length() != 32) return null;
        NormativeDocument document = normativeDocumentRepository.findById(id).orElse(null);
        if (isNull(document)) return null;
        document.setType(normativeDocumentTypeRepository.findById(document.getDOCTYPE())
                .orElse(new NormativeDocumentType()).getNAME());
        return normativeDocumentMapper.toDto(document);
    }

    @SneakyThrows
    public String lastLog() {
        return Files.lines(Paths.get(environment.getProperty("property.path") + "/lastLog.log"), Charsets.UTF_8)
                .reduce("", (str1, str2) -> str1 + "<br>" + str2)
                .replaceFirst("<br>", "");
    }
}