package system.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import system.dao.*;
import system.model.*;
import system.model.Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class FiasService {

    private static final Logger logger = LogManager.getLogger(FiasService.class);
    private String mainPath = "D:\\Fias\\";
    private Downloader downloader;
    private Unrarrer unrarrer;
    private Installer installer;
    private TokenAuthenticationManager tokenAuthenticationManager;
    private ObjectDao objectDao;
    private VersionDao versionDao;
    private SteadDao steadDao;
    private HouseDao houseDao;
    private RoomDao roomDao;
    private UserDao userDao;

    @Secured("ROLE_ADMIN")
    public boolean installComplete() {
        String lastVersion = getLastVersion();
//        downloader.downloadLastComplete(mainPath, lastVersion);
//        unrarrer.unrarLastComplete(mainPath, lastVersion);
//        installer.installLastComplete(mainPath, lastVersion);
        return true;
    }

    @Secured("ROLE_ADMIN")
    public boolean installOneUpdate() {
        String deltaVersion = getListOfNewVersions().get(0);
//        downloader.downloadDeltaByVersion(mainPath, deltaVersion);
//        unrarrer.unrarDeltaByVersion(mainPath, deltaVersion);
//        installer.installDeltaByVersion(mainPath, deltaVersion);
        return true;
    }

    @Secured("ROLE_ADMIN")
    public boolean installUpdates() {
        List<String> listOfNewVersions = getListOfNewVersions();
        for (String deltaVersion : listOfNewVersions){
//            downloader.downloadDeltaByVersion(mainPath, deltaVersion);
//            unrarrer.unrarDeltaByVersion(mainPath, deltaVersion);
//            installer.installDeltaByVersion(mainPath, deltaVersion);
        }
        return true;
    }

    public String getLastVersion() {
        StringBuilder res = new StringBuilder();
        try {
            URL url = new URL("https://fias.nalog.ru/Public/Downloads/Actual/VerDate.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String[] strings = in.readLine().split("\\.");
            for (int i = strings.length - 1; i >= 0; i--) res.append(strings[i]);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return res.toString();
    }

    public String getCurrentVersion() {
        return versionDao.getCurrentVersion();
    }

    public List<String> getListOfNewVersions() {
        String url = "https://fias.nalog.ru/WebServices/Public/DownloadService.asmx";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("Host", "fias.nalog.ru");
        post.setHeader("Content-Type", "text/xml;charset=utf-8");
        post.setHeader("SOAPAction", "http://fias.nalog.ru/WebServices/Public/DownloadService.asmx/GetAllDownloadFileInfo");

        StringEntity xmlString = null;
        try {
            xmlString = new StringEntity(
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "  <soap:Body>\n" +
                            "    <GetAllDownloadFileInfo xmlns=\"http://fias.nalog.ru/WebServices/Public/DownloadService.asmx\" />\n" +
                            "  </soap:Body>\n" +
                            "</soap:Envelope>"
            );
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        post.setEntity(xmlString);

        List<String> versions = new ArrayList<>();
        StringBuilder res = new StringBuilder();
        if (getCurrentVersion() == null) return null;
        try {
            HttpResponse response = client.execute(post);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = br.readLine().replaceAll("/", "");
            String[] text = line.split("<TextVersion>");
            for (int i = 0; i < text.length; i++) {
                if (i % 2 != 0) {
                    String[] strings = text[i].split(" ")[3].split("\\.");
                    for (int j = strings.length - 1; j >= 0; j--) res.append(strings[j]);
                    if (Integer.parseInt(res.toString()) > Integer.parseInt(getCurrentVersion())) {
                        versions.add(res.toString());
                    }
                    res.setLength(0);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return versions;
    }

    public List<Object> getObjectsByParentGuid(String guid, boolean isActual) {
        return objectDao.getObjectsByParentGuid(guid, isActual);
    }

    public List<Stead> getSteadsByParentGuid(String guid, boolean isActual) {
        return steadDao.getSteadsByParentGuid(guid, isActual);
    }

    public List<House> getHousesByParentGuid(String guid, boolean isActual) {
        return houseDao.getHousesByParentGuid(guid, isActual);
    }

    public List<Room> getRoomsListByParentGuid(String guid, boolean isActual) {
        return roomDao.getRoomsListByParentGuid(guid, isActual);
    }

    public List<java.lang.Object> searchObjects(LinkedHashMap<String, String> params) {
        List<java.lang.Object> res = new ArrayList<>();
        String searchType = params.get("searchType");
        boolean isActual = Boolean.parseBoolean(params.get("actual"));
        params.remove("searchType");
        params.remove("actual");
        if (searchType.contains("object")) {
            List<Object> objects = objectDao.getObjectsByParams(params, isActual);
            if (objects != null) res.addAll(objects);
        }
        if (searchType.contains("house")) {
            List<House> houses = houseDao.getHousesByParams(params, isActual);
            if (houses != null) res.addAll(houses);
        }
        if (searchType.contains("stead")) {
            List<Stead> steads = steadDao.getSteadsByParams(params, isActual);
            if (steads != null) res.addAll(steads);
        }
        if (searchType.contains("room")) {
            List<Room> rooms = roomDao.getRoomsByParams(params, isActual);
            if (rooms != null) res.addAll(rooms);
        }
        return res;
    }

    public boolean submitRegistration(User user) {
        User oldUser = userDao.getUser(user.getName());
        if (oldUser == null) {
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setPassword(bCrypt(user.getPassword()));
            newUser.setRole("ROLE_USER");
            userDao.save(newUser);
            SecurityContextHolder.getContext().setAuthentication(tokenAuthenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())));
            return true;
        }
        return false;
    }

    public String getUserName(){
        return tokenAuthenticationManager.getUserName();
    }

    private String bCrypt(String string) {
        return BCrypt.hashpw(string, BCrypt.gensalt());
    }

    @Autowired
    public void setHouseDao(HouseDao houseDao) {
        this.houseDao = houseDao;
    }
    @Autowired
    public void setRoomDao(RoomDao roomDao) {
        this.roomDao = roomDao;
    }
    @Autowired
    public void setSteadDao(SteadDao steadDao) {
        this.steadDao = steadDao;
    }
    @Autowired
    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }
    @Autowired
    public void setUnrarrer(Unrarrer unrarrer) {
        this.unrarrer = unrarrer;
    }
    @Autowired
    public void setInstaller(Installer installer) {
        this.installer = installer;
    }
    @Autowired
    public void setObjectDao(ObjectDao objectDao) {
        this.objectDao = objectDao;
    }
    @Autowired
    public void setVersionDao(VersionDao versionDao) {
        this.versionDao = versionDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    @Autowired
    public void setTokenAuthenticationManager(TokenAuthenticationManager tokenAuthenticationManager) {
        this.tokenAuthenticationManager = tokenAuthenticationManager;
    }
}
