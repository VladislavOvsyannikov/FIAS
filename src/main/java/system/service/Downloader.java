package system.service;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;


public class Downloader {

    private String path = "D:\\Fias\\";
    private String completeXmlUrl = "http://fias.nalog.ru/Public/Downloads/Actual/fias_xml.rar";
    private String deltaXmlUrl = "http://fias.nalog.ru/Public/Downloads/Actual/fias_delta_xml.rar";

    public String getLastVersion(){
        String res = "";
        try {
            URL url = new URL("http://fias.nalog.ru/Public/Downloads/Actual/VerDate.txt");
            Scanner sc = new Scanner(url.openStream());
            String[] strings = sc.nextLine().split("\\.");
            for (int i=strings.length-1; i>=0; i--) res += strings[i];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void downloadDelta(){
        String fileName = "delta"+getLastVersion()+".rar";
        downloadFile(deltaXmlUrl, fileName);
    }

    public void downloadComplete(){
        String fileName = "complete"+getLastVersion()+".rar";
        downloadFile(completeXmlUrl, fileName);
    }

    private void downloadFile(String url, String fileName) {
        System.out.println("Start download "+fileName);
        try {
            ReadableByteChannel rbc = Channels.newChannel(new URL(url).openStream());
            FileOutputStream fos = new FileOutputStream(path + fileName);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            System.out.println("Download is complete");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
