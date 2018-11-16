package system.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import system.dao.GenericDao;

@Service
public class MySAXParser extends DefaultHandler {

    private static final Logger logger = LogManager.getLogger(MySAXParser.class);

    private GenericDao<Object> genericDao;

    @Autowired
    public void setGenericDao(GenericDao<Object> genericDao) {
        this.genericDao = genericDao;
    }

    private String fileName;
    private String databaseType;
    private int numberOfObjects;
    private Object[] objects;
    private int index = -1;
    private int counter = 0;

    public void startDocument() {
        objects = new Object[numberOfObjects];
        logger.info("Start parse " + fileName);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        Object object = ReflectionHelper.createInstance(qName);
        if (object != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String field = attributes.getLocalName(i);
                String value = attributes.getValue(i);
                if (field.endsWith("ID") || field.endsWith("DATE") || field.endsWith("NORMDOC"))
                    value = value.replaceAll("-", "");
                ReflectionHelper.setFieldValue(object, field, value);
            }
            objects[++index] = object;
            if (index == objects.length - 1) {
                if (databaseType.equals("complete")) genericDao.saveBatch(objects, objects.length);
                else genericDao.saveOrUpdateBatch(objects, objects.length);
                counter += index + 1;
                logger.info("Saved " + counter + " objects");
                objects = new Object[objects.length];
                index = -1;
            }
        }
    }

    public void endDocument() {
        if (index > -1) {
            if (databaseType.equals("complete")) genericDao.saveBatch(objects, index + 1);
            else genericDao.saveOrUpdateBatch(objects, index + 1);
            counter += index + 1;
            logger.info("Saved " + counter + " objects");
        }
        logger.info("Stop parse " + fileName);
        logger.info("Parsed " + counter + " objects");
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public void setNumberOfObjects(int numberOfObjects) {
        this.numberOfObjects = numberOfObjects;
    }
}