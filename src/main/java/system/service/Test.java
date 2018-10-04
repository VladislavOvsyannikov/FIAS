package system.service;

import org.apache.log4j.Logger;

public class Test {

    private FiasService fiasService = new FiasService();
    private static final Logger logger = Logger.getLogger(Test.class);

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        Test test = new Test();

//        test.fiasService.checkUpdate();
        test.fiasService.installLastComplete();
//        test.fiasService.installDeltaByVersion();

        logger.info(((System.currentTimeMillis()-startTime)/1000) + " seconds");
    }
}
