package system.service;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {

    private String[] paths = {
//            "AS_ROOMTYPE_20180916_95e7bd8d-1260-4c1a-a647-71cd91e937e9.xml"
//            "AS_ACTSTAT_20180916_cc209faf-68ae-4dde-8f03-9e72cce3e313.xml",
//            "AS_ROOM_20180916_4d368f2e-a3c9-475e-9d0b-44b64c649443.xml",
            "AS_STEAD_20180916_37eaf347-7140-4f4f-8d30-2466d6fc9b55.xml"
    };


    public static void main(String[] args){
        long startTime = System.currentTimeMillis();

        Main main = new Main();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            for (String path:main.paths) {
                MySAXParser mySAXParser = new MySAXParser(path);
                saxParser.parse("D:\\20180917\\" + path, mySAXParser);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis() - startTime);
    }
}
