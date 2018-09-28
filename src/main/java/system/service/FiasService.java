package system.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.GenericDao;
import system.model.Version;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class FiasService {

    private static final Logger logger = Logger.getLogger(FiasService.class);

    private String mainPath = "D:\\Fias\\";

    private Downloader downloader = new Downloader();

    private Unrarrer unrarrer = new Unrarrer();

    private Installer installer = new Installer();

    private GenericDao genericDao = new GenericDao();

    @Autowired
    public void setGenericDao(GenericDao genericDao) {
        this.genericDao = genericDao;
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


    public void checkUpdate(){
        String lastVersion = getLastVersion();
        Version currentVersion = (Version) genericDao.getEntity(
                "select * from history_of_update order by id desc limit 1", Version.class);
        if (currentVersion==null) logger.info("Complete database download is required");
        else {
            if (Integer.parseInt(lastVersion)>Integer.parseInt(currentVersion.getVersion())) {
                logger.info("Update required");
                logger.info("Current version: " + currentVersion.getVersion());
                logger.info("Last version: " + lastVersion);
            }
            else logger.info("All data are actual");
        }
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
        installer.installLastComplete(mainPath, getLastVersion());
    }

    public void installDeltaByVersion() {
        String deltaVersion = getLastVersion();
        installer.installDeltaByVersion(mainPath, deltaVersion);
    }

}
