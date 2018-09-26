package system.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.GenericDao;
import system.model.Version;

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
        String lastVersion = downloader.getLastVersion();
        Version currentVersion = (Version) genericDao.getEntity(
                "select * from history_of_update order by id desc limit 1", Version.class);
        if (currentVersion==null) logger.info("Необходима загрузка полной базы");
        else {
            if (Integer.parseInt(lastVersion)>Integer.parseInt(currentVersion.getVersion())) {
                logger.info("Необходимо обновление");
                logger.info("Текущая версия: " + currentVersion.getVersion());
                logger.info("Последняя версия: " + lastVersion);
            }
            else logger.info("Все данные актуальные");
        }
    }

    public void installLastComplete(){
        installer.installLastComplete(mainPath);
    }

    public void downloadLastComplete(){
        downloader.downloadLastComplete(mainPath);
    }

    public void downloadLastDelta(){
        downloader.downloadLastDelta(mainPath);
    }

    public void unrarLastComplete(){
        unrarrer.unrarLastComplete(mainPath);
    }

    public void unrarLastDelta(){
        unrarrer.unrarLastDelta(mainPath);
    }
}
