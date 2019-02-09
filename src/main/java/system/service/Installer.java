package system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import system.domain.Version;
import system.repository.VersionRepository;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class Installer {

    private final VersionRepository versionRepository;
    private final MySAXParser mySAXParser;

    public void installLastComplete(String mainPath, String lastVersion) throws FiasException {
        File folder = new File(mainPath + "complete" + lastVersion);
        installDatabase(folder, "complete", lastVersion, 100_000);
    }

    public void installDeltaByVersion(String mainPath, String deltaVersion) throws FiasException {
        File folder = new File(mainPath + "delta" + deltaVersion);
        installDatabase(folder, "delta", deltaVersion, 10_000);
    }

    public void installDatabase(File folder, String databaseType, String version, int numberOfObjects) throws FiasException {
        if (folder.exists()) {
            try {
                SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
                mySAXParser.setDatabaseType(databaseType);
                mySAXParser.setNumberOfObjects(numberOfObjects);
                for (File file : Objects.requireNonNull(folder.listFiles())) {
                    String fileName = file.getName();
                    if (!fileName.contains("_DEL_")) {
                        mySAXParser.setFileName(fileName);
                        saxParser.parse(file, mySAXParser);
                    }
                }
                Version ver = new Version();
                ver.setVersion(version);
                versionRepository.save(ver);
                log.info("Update to version " + version + " is completed");
            } catch (Exception e) {
                log.error(e);
                throw new FiasException();
            }
        } else log.warn(folder.getName() + " not exists");
    }
}