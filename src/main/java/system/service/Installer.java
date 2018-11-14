package system.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.dao.VersionDao;
import system.model.Version;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Objects;

@Service
public class Installer {

    private static final Logger logger = LogManager.getLogger(Installer.class);
    private VersionDao versionDao;
    private MySAXParser mySAXParser;

    @Autowired
    public void setMySAXParser(MySAXParser mySAXParser) {
        this.mySAXParser = mySAXParser;
    }
    @Autowired
    public void setVersionDao(VersionDao versionDao) {
        this.versionDao = versionDao;
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
                mySAXParser.setDatabaseType(databaseType);
                mySAXParser.setNumberOfObjects(numberOfObjects);
                for (File file : Objects.requireNonNull(folder.listFiles())) {
                    String fileName = file.getName();
                    if (!fileName.contains("_DEL_")){
                        mySAXParser.setFileName(fileName);
                        saxParser.parse(file, mySAXParser);
                    }
                }
                Version version = new Version();
                version.setVersion(databaseVersion);
                versionDao.save(version);
                logger.info("Update to version " + databaseVersion + " is completed");
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else logger.warn("Folder not found");
    }
}
