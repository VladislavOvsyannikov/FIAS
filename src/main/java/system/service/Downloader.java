package system.service;

import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(Downloader.class);

    public void downloadLastDelta(String path){
        String lastVersion = getLastVersion();
        String fileName = "delta"+lastVersion+".rar";
        String lastDeltaXmlUrl = "https://fias.nalog.ru/Public/Downloads/"+lastVersion+"/fias_delta_xml.rar";
        downloadFile(path, lastDeltaXmlUrl, fileName);
    }

    public void downloadDeltaByVersion(String path, String version){
        String fileName = "delta"+version+".rar";
        String url = "https://fias.nalog.ru/Public/Downloads/"+version+"/fias_delta_xml.rar";
        downloadFile(path, url, fileName);
    }

    public void downloadLastComplete(String path){
        String lastVersion = getLastVersion();
        String fileName = "complete"+lastVersion+".rar";
        String lastCompleteXmlUrl = "https://fias.nalog.ru/Public/Downloads/"+lastVersion+"/fias_xml.rar";
        downloadFile(path, lastCompleteXmlUrl, fileName);
    }

    public String getLastVersion(){
        StringBuilder res = new StringBuilder();
        try {
            URL url = new URL("https://fias.nalog.ru/Public/Downloads/Actual/VerDate.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String[] strings = in.readLine().split("\\.");
            for (int i=strings.length-1; i>=0; i--) res.append(strings[i]);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return res.toString();
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
