package system.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
public class Deleter {

    private static final Logger logger = LogManager.getLogger(Deleter.class);

    public void deleteDeltaFiles(String path, String version) {
        String fileName = "delta" + version + ".rar";
        deleteFile(path, fileName);
        String folderName = "delta" + version;
        deleteFolder(path, folderName);

    }

    public void deleteCompleteFiles(String path, String version) {
        String fileName = "complete" + version + ".rar";
        deleteFile(path, fileName);
        String folderName = "complete" + version;
        deleteFolder(path, folderName);
    }

    private void deleteFile(String path, String fileName) {
        File file = new File(path + fileName);
        if (file.exists()) {
            if (file.delete()) logger.info("Archive " + fileName + " deleted ");
            else logger.warn("Archive " + fileName + " not deleted ");
        } else logger.warn("Archive " + fileName + " not exists");
    }

    private void deleteFolder(String path, String folderName) {
        File folder = new File(path + folderName);
        if (folder.exists()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) file.delete();
            if (folder.delete()) logger.info("Folder " + folderName + " deleted ");
            else logger.warn("Folder " + folderName + " not deleted ");
        } else logger.warn("Folder " + folderName + " not exists");
    }
}