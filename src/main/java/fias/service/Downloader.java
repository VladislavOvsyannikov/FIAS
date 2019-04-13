package fias.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Log4j2
@Service
public class Downloader {

    public void downloadLastComplete(String path, String lastVersion) throws FiasException {
        String url = "https://fias.nalog.ru/Public/Downloads/" + lastVersion + "/fias_xml.rar";
        downloadFile(path, url, "complete" + lastVersion + ".rar");
    }

    public void downloadDeltaByVersion(String path, String deltaVersion) throws FiasException {
        String url = "https://fias.nalog.ru/Public/Downloads/" + deltaVersion + "/fias_delta_xml.rar";
        downloadFile(path, url, "delta" + deltaVersion + ".rar");
    }

    private void downloadFile(String path, String url, String fileName) throws FiasException {
        File file = new File(path + fileName);
        if (!file.exists()) {
            log.info("Start download " + fileName);
            try (ReadableByteChannel rbc = Channels.newChannel(new URL(url).openStream());
                 FileOutputStream fos = new FileOutputStream(path + fileName)) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                log.info("Download " + fileName + " completed");
            } catch (Exception e) {
                log.error(e);
                throw new FiasException();
            }
        } else log.warn(fileName + " already exists");
    }
}