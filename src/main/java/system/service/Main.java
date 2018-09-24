package system.service;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {


    private String mainPath = "D:\\Fias\\complete20180917\\";
    private String[] paths = {
//            "AS_SOCRBASE_20180916_23c768e0-c9cc-4563-bd9f-2f276c3904b8.xml",
//            "AS_CURENTST_20180916_ebbd1936-2018-41cf-b576-b8cab92d23bb.xml",
//            "AS_HSTSTAT_20180916_8d662fd9-f8e5-4c1a-b22a-54c20db9b213.xml",
//            "AS_NDOCTYPE_20180916_356e1900-9bd3-419d-98f9-18777eb38b7b.xml",
//            "AS_OPERSTAT_20180916_acf95eb1-f9a0-4ca0-a425-52b58bb4f204.xml",
//            "AS_CENTERST_20180916_0a690000-9345-4953-9685-e56a757184d6.xml",
//            "AS_FLATTYPE_20180916_3904c8e7-853d-4197-9ead-c42fa9a1b55a.xml",
//            "AS_ESTSTAT_20180916_b62cfc59-4c59-4082-83ef-dfbde56e2a0e.xml",
//            "AS_STRSTAT_20180916_f94cabc4-afd4-458c-a834-28bebb42f3f7.xml",
//            "AS_INTVSTAT_20180916_976839c0-1ba9-4daa-9c32-d9d6f81d7225.xml",
//            "AS_ROOMTYPE_20180916_95e7bd8d-1260-4c1a-a647-71cd91e937e9.xml",
//            "AS_ACTSTAT_20180916_cc209faf-68ae-4dde-8f03-9e72cce3e313.xml"

//            "AS_ROOM_20180916_4d368f2e-a3c9-475e-9d0b-44b64c649443.xml",
//            "AS_STEAD_20180916_37eaf347-7140-4f4f-8d30-2466d6fc9b55.xml",
//            "AS_HOUSE_20180916_858ebac4-d642-4db0-a7ae-0533c986447c.xml",
//            "AS_NORMDOC_20180916_c13ae0ec-a8d7-4fdd-9997-4ac9ef06b7c0.xml",
            "AS_ADDROBJ_20180916_6bdf671e-305b-4ada-9d9d-db78bb13ae21.xml"
    };


    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        Main main = new Main();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            for (String path:main.paths) {
                MySAXParser mySAXParser = new MySAXParser(path);
                saxParser.parse(main.mainPath + path, mySAXParser);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() - startTime);

//        Downloader downloader = new Downloader();
//        Unrarrer unrarrer = new Unrarrer();
//
//        downloader.downloadDelta();
//        unrarrer.unRarDelta();
    }
}
