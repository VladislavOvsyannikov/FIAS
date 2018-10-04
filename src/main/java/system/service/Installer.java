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

    private GenericDao<Version> genericDao;

    @Autowired
    public void setGenericDao(GenericDao<Version> genericDao) {
        this.genericDao = genericDao;
    }


    public void installLastComplete(String mainPath, String lastVersion) {
        installDatabase(mainPath, "complete", lastVersion, 100_000);
    }

    public void installDeltaByVersion(String mainPath, String deltaVersion){
        installDatabase(mainPath, "delta", deltaVersion, 10_000);
    }

    private void installDatabase(String mainPath, String databaseType, String databaseVersion, int numberOfObjects) {
        String path = mainPath + databaseType + databaseVersion;
        File folder = new File(path);
        if (folder.exists()) {
            try {
                SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
                for (File file : folder.listFiles()) {
                    String fileName = file.getName();
//                    if (!fileName.contains("_DEL_") && !fileName.contains("_NORMDOC_")){
//                    if (fileName.contains("AS_ADDROBJ_")) {
//                        MySAXParser mySAXParser = new MySAXParser(fileName, databaseType, numberOfObjects);
//                        saxParser.parse(file, mySAXParser);
//                    }
                }
                Version version = new Version();
                version.setVersion(databaseVersion);
                genericDao.save(version);
                logger.info("Update to version " + databaseVersion + " is completed");
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else logger.warn("Folder not found");
    }
}
