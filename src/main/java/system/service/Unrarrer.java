package system.service;

import com.github.junrar.Junrar;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;

@Log4j2
@Service
public class Unrarrer {

    public void unrarLastComplete(String path, String lastVersion) throws FiasException {
        unRarFile(path, "complete" + lastVersion);
    }

    public void unrarDeltaByVersion(String path, String deltaVersion) throws FiasException {
        unRarFile(path, "delta" + deltaVersion);
    }

    private void unRarFile(String path, String fileName) throws FiasException {
        log.info("Start unrar " + fileName + ".rar");
        File rar = new File(path + fileName + ".rar");
        if (rar.exists()) {
            try {
                File folder = new File(path + fileName);
                if (!folder.exists() && !folder.mkdir()) {
                    log.error("Folder " + fileName + " not created");
                    throw new FiasException();
                }
                Junrar.extract(rar, folder);
                log.info("Unrar " + fileName + ".rar is completed");
            } catch (Exception e) {
                log.error(e);
                throw new FiasException();
            }
        } else log.warn(fileName + ".rar not found");
    }
}