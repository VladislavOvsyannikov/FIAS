package system.service;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import system.dao.GenericDao;

import java.util.Arrays;


public class MySAXParser extends DefaultHandler {

    private static final Logger logger = Logger.getLogger(MySAXParser.class);

    private String[] numbersOfNoExistHouses = {"2","3","15","16","18","27","34","35","38","39"};
    private GenericDao<Object> genericDao = new GenericDao<>();
    private String xmlFile;
    private String databaseType;
    private Object[] objects;
    private int index = -1;
    private boolean isSaveBatchEnd = true;

    MySAXParser(String xmlFile, String databaseType, int numberOfObjects) {
        this.xmlFile = xmlFile;
        this.databaseType = databaseType;
        this.objects = new Object[numberOfObjects];
    }

    public void startDocument() {
        logger.info("Start parse " + xmlFile);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        Object object = ReflectionHelper.createInstance(qName);
        if (object != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String field = attributes.getLocalName(i);
                String value = attributes.getValue(i);
                if (databaseType.equals("complete") && field.equals("LIVESTATUS") && value.equals("0")) return;
                if (databaseType.equals("complete") && field.equals("STATSTATUS")
                        && (Arrays.stream(numbersOfNoExistHouses).anyMatch(value::equals))) return;
                if (field.endsWith("ID")) value = value.replaceAll("-","");
                ReflectionHelper.setFieldValue(object, field, value);
            }
            objects[++index] = object;
            if (index == objects.length - 1) {
                while (!isSaveBatchEnd){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                }
                Object[] entities = objects;
                isSaveBatchEnd = false;
                new Thread(() -> {
                    if (databaseType.equals("complete")) genericDao.saveBatch(entities);
                    else genericDao.saveOrUpdateBatch(entities);
                    isSaveBatchEnd = true;
                }).start();
                objects = new Object[objects.length];
                index = -1;
            }
        }
    }

    public void endDocument() {
        if (index > -1) {
            while (!isSaveBatchEnd){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
            if (databaseType.equals("complete")) genericDao.saveBatch(objects);
            else genericDao.saveOrUpdateBatch(objects);
        }
        logger.info("Stop parse " + xmlFile);
    }
}