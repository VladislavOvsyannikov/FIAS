package system.service;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import system.dao.GenericDao;

import java.util.Arrays;


public class MySAXParser extends DefaultHandler {

    private static final Logger logger = Logger.getLogger(MySAXParser.class);

    private int length = 100_000;

    private String[] models = {"ActualStatus","AddressObjectType","CenterStatus","CurrentStatus",
            "EstateStatus","FlatType","House","HouseStateStatus","IntervalStatus",",NormativeDocument",
            "NormativeDocumentType","Object","OperationStatus","Room","RoomType","Stead","StructureStatus"};
    private GenericDao genericDao = new GenericDao();

    private String xmlFile;
    private int counter;
    private Object[] objects;
    private boolean isSaveBatchEnd = true;

    MySAXParser(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public void startDocument() {
        logger.info("Start parse " + xmlFile);
        objects = new Object[length];
        counter = 0;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (Arrays.stream(models).anyMatch(qName::equals)) {
            Object object = ReflectionHelper.createInstance(qName);
            for (int i = 0; i < attributes.getLength(); i++) {
                String field = attributes.getLocalName(i);
                String value = attributes.getValue(i);
                if (field.equals("LIVESTATUS") && value.equals("0")) return;
                ReflectionHelper.setFieldValue(object, field, value);
            }
            counter++;
            ReflectionHelper.setFieldValue(object, "id", String.valueOf(counter));
            int t = (counter % objects.length) - 1;
            if (t == -1) t = objects.length - 1;
            objects[t] = object;
            if (t == objects.length - 1) {
                while (!isSaveBatchEnd){
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                }
                Object[] entities = objects;
                new Thread(() -> {
                    isSaveBatchEnd = false;
                    genericDao.saveBatch(entities);
                    isSaveBatchEnd = true;
                }).start();
                objects = new Object[length];
            }
        }
    }

    public void endDocument() {
        if (counter > 0 && counter % objects.length != 0) {
            while (!isSaveBatchEnd){
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
            genericDao.saveBatch(objects);
        }
        logger.info("Stop parse " + xmlFile);
        logger.info("Saved "+counter+" objects");
    }
}
