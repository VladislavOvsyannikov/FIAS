package system.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.*;
import system.model.Object;
import system.model.Stead;
import system.model.House;
import system.model.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class FiasService {

    private static final Logger logger = Logger.getLogger(FiasService.class);
    private String mainPath = "D:\\Fias\\";
    private Downloader downloader;
    private Unrarrer unrarrer;
    private Installer installer;
    private ObjectDao objectDao;
    private VersionDao versionDao;
    private SteadDao steadDao;
    private HouseDao houseDao;
    private RoomDao roomDao;

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


    public void checkUpdate(){
        String lastVersion = getLastVersion();
        String currentVersion = getCurrentVersion();
        if (currentVersion==null) logger.info("Complete database download is required");
        else {
            if (Integer.parseInt(lastVersion)>Integer.parseInt(currentVersion)) {
                logger.info("Current version: " + currentVersion);
                logger.info("Next updates required: ");
                logger.info(getNewVersions().toString());
            }
            else logger.info("All data are actual");
        }
    }


    public void downloadLastComplete(){
        downloader.downloadLastComplete(mainPath, getLastVersion());
    }

    public void downloadDeltaByVersion(){
        String deltaVersion = getLastVersion();
        downloader.downloadDeltaByVersion(mainPath, deltaVersion);
    }


    public void unrarLastComplete(){
        unrarrer.unrarLastComplete(mainPath, getLastVersion());
    }

    public void unrarDeltaByVersion(){
        String deltaVersion = getLastVersion();
        unrarrer.unrarDeltaByVersion(mainPath, deltaVersion);
    }


    public void installLastComplete(){
        installer.installLastComplete(mainPath, "20180917");
    }

    public void installDeltaByVersion() {
        String deltaVersion = getLastVersion();
        installer.installDeltaByVersion(mainPath, deltaVersion);
    }


    private String getLastVersion(){
        StringBuilder res = new StringBuilder();
        try {
            URL url = new URL("https://fias.nalog.ru/Public/Downloads/Actual/VerDate.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String[] strings = in.readLine().split("\\.");
            for (int i=strings.length-1; i>=0; i--) res.append(strings[i]);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return res.toString();
    }

    private String getCurrentVersion(){
        return versionDao.getCurrentVersion();
    }

    private List<String> getNewVersions(){
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
        try {
            HttpResponse response = client.execute(post);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = br.readLine().replaceAll("/", "");
            String[] text = line.split("<TextVersion>");
            for (int i=0; i<text.length; i++){
                if (i%2!=0){
                    String[] strings = text[i].split(" ")[3].split("\\.");
                    for (int j=strings.length-1; j>=0; j--) res.append(strings[j]);
                    if (Integer.parseInt(res.toString()) > Integer.parseInt(getCurrentVersion())){
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


    public List<Object> getObjectsListByGuid(String guid) {
        return objectDao.getObjectsListByGuid(guid);
    }

    public List<Stead> getSteadsListByGuid(String guid){
        return steadDao.getSteadsListByGuid(guid);
    }

    public List<House> getHousesListByGuid(String guid){
        return houseDao.getHousesListByGuid(guid);
    }

    public List<Room> getRoomsListByGuid(String guid){
        return roomDao.getRoomsListByGuid(guid);
    }
}
