package fias.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Log4j2
@Service
public class Deleter {

    public void deleteDeltaFiles(String path, String version) {
        deleteFile(path, "delta" + version + ".rar");
        deleteFolder(path, "delta" + version);
    }

    public void deleteCompleteFiles(String path, String version) {
        deleteFile(path, "complete" + version + ".rar");
        deleteFolder(path, "complete" + version);
    }

    private void deleteFile(String path, String fileName) {
        File file = new File(path + fileName);
        if (file.exists()) {
            if (file.delete()) log.info("Archive " + fileName + " deleted ");
            else log.warn("Archive " + fileName + " not deleted ");
        } else log.warn("Archive " + fileName + " not exists");
    }

    private void deleteFolder(String path, String folderName) {
        File folder = new File(path + folderName);
        if (folder.exists()) {
            for (File file : Objects.requireNonNull(folder.listFiles()))
                if (!file.delete()) log.warn("File " + file.getName() + " not deleted ");
            if (folder.delete()) log.info("Folder " + folderName + " deleted ");
            else log.warn("Folder " + folderName + " not deleted ");
        } else log.warn("Folder " + folderName + " not exists");
    }
}