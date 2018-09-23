package system.service;

import com.github.junrar.extract.ExtractArchive;

import java.io.File;

public class Unrarrer {

    private String path = "D:\\Fias\\";
    private String lastVersion = new Downloader().getLastVersion();

    public void unRarDelta(){
        String fileName = "delta"+lastVersion;
        unRarFile(fileName);
    }

    public void unRarComplete(){
        String fileName = "complete"+lastVersion;
        unRarFile(fileName);
    }

    private void unRarFile(String fileName){
        System.out.println("Start unrar "+fileName+".rar");
        File rar = new File(path+fileName+".rar");
        if (rar.exists()) {
            File folder = new File(path + fileName);
            if (!folder.exists()) folder.mkdir();
            new ExtractArchive().extractArchive(rar, folder);
            System.out.println("Unrar is complete");
        }else {
            System.out.println("File not found");
        }
    }

}
