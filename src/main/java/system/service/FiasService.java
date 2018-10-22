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
import org.springframework.stereotype.Service;
import system.dao.*;
import system.model.primary.*;
import system.model.primary.Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
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
    private ObjectDao objectDao;
    private VersionDao versionDao;
    private SteadDao steadDao;
    private HouseDao houseDao;
    private RoomDao roomDao;
    private UserDao userDao;

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

    @Secured("ROLE_ADMIN")
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

    @Secured("ROLE_ADMIN")
    public String getCurrentVersion() {
        return versionDao.getCurrentVersion();
    }

    @Secured("ROLE_ADMIN")
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


    public List<Object> getObjectsByParentGuid(String guid) {
        return objectDao.getObjectsByParentGuid(guid);
    }

    public List<Stead> getSteadsByParentGuid(String guid) {
        return steadDao.getSteadsByParentGuid(guid);
    }

    public List<House> getHousesByParentGuid(String guid) {
        return houseDao.getHousesByParentGuid(guid);
    }

    public List<Room> getRoomsListByParentGuid(String guid) {
        return roomDao.getRoomsListByParentGuid(guid);
    }

    public String toMD5(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public List<java.lang.Object> searchObjects(LinkedHashMap<String, String> params) {
        List<java.lang.Object> res = new ArrayList<>();
        List<Object> objects = objectDao.getObjectsByParams(params);
        if (objects != null) res.addAll(objects);
        List<House> houses = houseDao.getHousesByParams(params);
        if (houses != null) res.addAll(houses);
        List<Stead> steads = steadDao.getSteadsByParams(params);
        if (steads != null) res.addAll(steads);
        List<Room> rooms = roomDao.getRoomsByParams(params);
        if (rooms != null) res.addAll(rooms);
        return res;
    }
}
