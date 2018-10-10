package system.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Service
public class Downloader {

    private static final Logger logger = LogManager.getLogger(Downloader.class);


    public void downloadLastComplete(String path, String lastVersion){
        String fileName = "complete"+lastVersion+".rar";
        String lastCompleteXmlUrl = "https://fias.nalog.ru/Public/Downloads/"+lastVersion+"/fias_xml.rar";
        downloadFile(path, lastCompleteXmlUrl, fileName);
    }

    public void downloadDeltaByVersion(String path, String deltaVersion){
        String fileName = "delta"+deltaVersion+".rar";
        String url = "https://fias.nalog.ru/Public/Downloads/"+deltaVersion+"/fias_delta_xml.rar";
        downloadFile(path, url, fileName);
    }

    private void downloadFile(String path, String url, String fileName) {
        File file = new File(path+fileName);
        if(!file.exists()) {
            logger.info("Start download " + fileName);
            try {
                ReadableByteChannel rbc = Channels.newChannel(new URL(url).openStream());
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                logger.info("Download is completed");
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }else logger.warn(fileName+" already exists");
    }
}
