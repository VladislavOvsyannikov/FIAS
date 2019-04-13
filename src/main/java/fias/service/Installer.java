package fias.service;

import fias.domain.Version;
import fias.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class Installer {

    private final VersionRepository versionRepository;
    private final FiasSAXParser fiasSAXParser;

    public void installLastComplete(String mainPath, String lastVersion) throws FiasException {
        File folder = new File(mainPath + "complete" + lastVersion);
        installDatabase(folder, DatabaseAction.SAVE, lastVersion, 100_000);
    }

    public void installDeltaByVersion(String mainPath, String deltaVersion) throws FiasException {
        File folder = new File(mainPath + "delta" + deltaVersion);
        installDatabase(folder, DatabaseAction.UPDATE, deltaVersion, 10_000);
    }

    public void installDatabase(File folder, DatabaseAction action, String version, int numberOfObjects) throws FiasException {
        if (folder.exists()) {
            try {
                SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
                fiasSAXParser.setAction(action);
                fiasSAXParser.setNumberOfObjects(numberOfObjects);
                for (File file : Objects.requireNonNull(folder.listFiles())) {
                    String fileName = file.getName();
                    if (!fileName.contains("_DEL_")) {
                        fiasSAXParser.setFileName(fileName);
                        saxParser.parse(file, fiasSAXParser);
                    }
                }
                fiasSAXParser.setAction(DatabaseAction.DELETE);
                fiasSAXParser.setNumberOfObjects(1000);
                for (File file : Objects.requireNonNull(folder.listFiles())) {
                    String fileName = file.getName();
                    if (fileName.contains("_DEL_")) {
                        fiasSAXParser.setFileName(fileName);
                        saxParser.parse(file, fiasSAXParser);
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