package system.service;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import system.dao.GenericDao;

import java.util.Arrays;

public class MySAXParser extends DefaultHandler {

    private String[] models = {"Room","Stead","ActualStatus","RoomType"};
    private GenericDao genericDao = new GenericDao();

    private String xmlFile;
    private Object object = null;

    private long counter = 0;

    public MySAXParser(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public void startDocument(){
        System.out.println("Start parse "+xmlFile);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes){
        if (Arrays.stream(models).anyMatch(qName::equals)) {
            object = ReflectionHelper.createInstance(qName);
            for (int i=0; i<attributes.getLength(); i++) {
                String field = attributes.getLocalName(i);
                String value = attributes.getValue(i);
                ReflectionHelper.setFieldValue(object, field, value);
            }
//            System.out.println(object.getClass());
//            System.out.println(object.toString());
            genericDao.save(object);
            counter++;

        }
    }

    public void endDocument() {
        System.out.println("Stop parse "+xmlFile);
        System.out.println(counter);
    }

    public Object getObject(){
        return object;
    }
}
