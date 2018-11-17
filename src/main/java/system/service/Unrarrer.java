package system.service;

import com.github.junrar.Junrar;
import com.github.junrar.extract.ExtractArchive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class Unrarrer {

    private static final Logger logger = LogManager.getLogger(Unrarrer.class);

    public void unrarLastComplete(String path, String lastVersion) throws FiasException {
        String fileName = "complete" + lastVersion;
        unRarFile(path, fileName);
    }

    public void unrarDeltaByVersion(String path, String deltaVersion) throws FiasException {
        String fileName = "delta" + deltaVersion;
        unRarFile(path, fileName);
    }

    private void unRarFile(String path, String fileName) throws FiasException {
        logger.info("Start unrar " + fileName + ".rar");
        File rar = new File(path + fileName + ".rar");
        if (rar.exists()) {
            try {
                File folder = new File(path + fileName);
                if (!folder.exists()) folder.mkdir();
                Junrar.extract(rar, folder);
                logger.info("Unrar " + fileName + ".rar is completed");
            } catch (Exception e) {
                logger.error(e.getClass().getName() + ": " + e.getMessage());
                throw new FiasException();
            }
        } else {
            logger.warn(fileName + ".rar not found");
        }
    }
}