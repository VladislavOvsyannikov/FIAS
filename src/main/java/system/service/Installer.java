package system.service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.GenericDao;
import system.model.Version;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

@Service
public class Installer {

    private static final Logger logger = Logger.getLogger(Installer.class);

    private Downloader downloader = new Downloader();

    private GenericDao genericDao = new GenericDao();

    @Autowired
    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }

    @Autowired
    public void setGenericDao(GenericDao genericDao) {
        this.genericDao = genericDao;
    }


    public void installLastComplete(String mainPath){
        String lastVersion = downloader.getLastVersion();
        String path = mainPath+"complete"+lastVersion;
        File folder = new File(path);
        if (folder.exists()) {
            File[] files = folder.listFiles();
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                for (File file : files) {
                    String fileName = file.getName();
                    if (!fileName.contains("_DEL_")&&!fileName.contains("_NORMDOC_")) {
                        MySAXParser mySAXParser = new MySAXParser(fileName);
                        saxParser.parse(mainPath + path, mySAXParser);
                    }
                }
                Version version = new Version();
                version.setVersion(lastVersion);
                genericDao.save(version);
                logger.info("Install is completed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else logger.warn("Folder not found or new version exists");
    }

//    public void installLastDelta(String mainPath){
//        String lastVersion = downloader.getLastVersion();
//        String path = mainPath+"delta"+lastVersion;
//        File folder = new File(path);
//        if (folder.exists()) {
//            File[] files = folder.listFiles();
//            try {
//                SAXParserFactory factory = SAXParserFactory.newInstance();
//                SAXParser saxParser = factory.newSAXParser();
//                for (File file : files) {
//                    String fileName = file.getName();
//                    if (!fileName.contains("_DEL_")) {
//                        MySAXParser mySAXParser = new MySAXParser(fileName);
//                        saxParser.parse(mainPath + path, mySAXParser);
//                    }
//                }
//                Version version = new Version();
//                version.setVersion(lastVersion);
//                genericDao.save(version);
//                logger.info("Update is completed");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else logger.warn("Folder not found or new version exists");
//    }
}
