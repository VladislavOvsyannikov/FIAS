package system.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import system.dao.GenericDao;

import java.util.Arrays;

public class MySAXParser extends DefaultHandler {

    private static final Logger logger = LogManager.getLogger(MySAXParser.class);

//    private String[] numbersOfNoExistHouses = {"2","3","15","16","18","27","34","35","38","39"};
    private GenericDao<Object> genericDao = new GenericDao<>();
    private String xmlFile;
    private String databaseType;
    private Object[] objects;
    private int index = -1;
    private boolean isSaveBatchEnd = true;
    private int counter = 0;

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
                counter += index+1;
                isSaveBatchEnd = false;
                new Thread(() -> {
                    if (databaseType.equals("complete")) genericDao.saveBatch(entities, objects.length);
                    else genericDao.saveOrUpdateBatch(entities, objects.length);
                    isSaveBatchEnd = true;
                    logger.info("Saved "+counter+" objects");
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
            if (databaseType.equals("complete")) genericDao.saveBatch(objects, index+1);
            else genericDao.saveOrUpdateBatch(objects, index+1);
            counter += index+1;
            logger.info("Saved "+counter+" objects");
        }
        logger.info("Stop parse " + xmlFile);
        logger.info("Parsed "+counter+" objects");
    }
}