package system.service;

import com.github.junrar.extract.ExtractArchive;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class Unrarrer {

    private static final Logger logger = Logger.getLogger(Unrarrer.class);

    private String lastVersion = new Downloader().getLastVersion();

    public void unrarLastDelta(String path){
        String fileName = "delta"+lastVersion;
        unRarFile(path, fileName);
    }

    public void unrarDeltaByVersion(String path, String version){
        String fileName = "delta"+version;
        unRarFile(path, fileName);
    }

    public void unrarLastComplete(String path){
        String fileName = "complete"+lastVersion;
        unRarFile(path, fileName);
    }

    private void unRarFile(String path, String fileName){
        logger.info("Start unrar "+fileName+".rar");
        File rar = new File(path+fileName+".rar");
        if (rar.exists()) {
            try {
                File folder = new File(path + fileName);
                if (!folder.exists()) folder.mkdir();
                new ExtractArchive().extractArchive(rar, folder);
                logger.info("Unrar is completed");
            } catch(Exception e){
                logger.error(e.getMessage());
            }
        }else {
            logger.warn("File not found");
        }
    }

}
