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

    private GenericDao genericDao = new GenericDao();

    @Autowired
    public void setGenericDao(GenericDao genericDao) {
        this.genericDao = genericDao;
    }


    private int numberOfObjects = 100_000;
    private Object[] objects;
    private boolean isSaveBatchEnd = true;


    public void installLastComplete(String mainPath, String lastVersion){
//        String lastVersion = downloader.getLastVersion();
        String path = mainPath+"complete"+lastVersion;
        File folder = new File(path);
        if (folder.exists()) {
            File[] files = folder.listFiles();
            try {
                SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
                for (File file : files) {
                    String fileName = file.getName();
//                    if (!fileName.contains("_DEL_")&&!fileName.contains("_NORMDOC_")) {
                    if(fileName.contains("AS_FLATTYPE_20180916_3904c8e7-853d-4197-9ead-c42fa9a1b55a.XML")){


                        MySAXParser mySAXParser = new MySAXParser(fileName, "complete");
                        saxParser.parse(file, mySAXParser);


                    }
                }
//                Version version = new Version();
//                version.setVersion(lastVersion);
//                genericDao.save(version);
                logger.info("Install is completed");
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else logger.warn("Folder not found or new version exists");
    }





//    public void installDeltaByVersion(String mainPath, String deltaVersion){
//        installDelta(mainPath, deltaVersion);
//    }
//
//    private void installDelta(String mainPath, String deltaVersion){
//        String path = mainPath+"delta"+deltaVersion;
//        File folder = new File(path);
//        if (folder.exists()) {
//            File[] files = folder.listFiles();
//            try {
//                SAXParserFactory factory = SAXParserFactory.newInstance();
//                SAXParser saxParser = factory.newSAXParser();
//                for (File file : files) {
//                    String fileName = file.getName();
//                    MySAXParser mySAXParser = new MySAXParser(fileName, "delta");
//                    saxParser.parse(file, mySAXParser);
//                }
////                Version version = new Version();
////                version.setVersion(deltaVersion);
////                genericDao.save(version);
//                logger.info("Update to version "+deltaVersion+" is completed");
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//            }
//        } else logger.warn("Folder not found or new version exists");
//    }
}
