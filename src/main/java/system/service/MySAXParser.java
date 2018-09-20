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

    private int counter = 0;
    private int length = 100_000;
    private Object[] objects = new Object[length];


    public MySAXParser(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public void startDocument(){
        System.out.println("Start parse "+xmlFile);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes){
        if (Arrays.stream(models).anyMatch(qName::equals)) {
            counter++;
            object = ReflectionHelper.createInstance(qName);
            ReflectionHelper.setFieldValue(object, "id", String.valueOf(counter));
            for (int i=0; i<attributes.getLength(); i++) {
                String field = attributes.getLocalName(i);
                String value = attributes.getValue(i);
                ReflectionHelper.setFieldValue(object, field, value);
            }
//            System.out.println(object.toString());
//            genericDao.save(object);

            int t = (counter%objects.length)-1;
            if (t==-1) t = objects.length-1;
            objects[t] = object;
            if (t==objects.length-1){
                genericDao.saveBatch(objects);
                objects = new Object[length];
            }
        }
    }

    public void endDocument() {
        if (counter > 0 && counter%objects.length!=0){
            genericDao.saveBatch(objects);
        }
        System.out.println("Stop parse "+xmlFile);
        System.out.println(counter);
    }
}
