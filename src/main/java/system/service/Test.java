package system.service;

public class Test {

    private FiasService fiasService = new FiasService();

    public static void main(String[] args){
        Test test = new Test();

        test.fiasService.checkUpdate();
        test.fiasService.downloadLastDelta();
        test.fiasService.unrarLastDelta();
    }
}
