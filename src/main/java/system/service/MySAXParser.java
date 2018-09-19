package system.service;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import system.dao.GenericDao;

public class MySAXParser extends DefaultHandler {

    private GenericDao genericDao = new GenericDao();
    private String xmlFile;
    private String element = null;
    private Object object = null;
    private long counter = 0;

    public MySAXParser(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public void startDocument(){
        System.out.println("Start parse "+xmlFile);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes){
        String className = null;
        if(qName.equals("RoomType") || qName.equals("ActualStatus") || qName.equals(system.model.Room.class.getSimpleName())) {
            className = qName;
//            System.out.println("Class name: " + className);
            object = ReflectionHelper.createInstance(className);
            for (int i=0; i<attributes.getLength(); i++) {
                element = attributes.getLocalName(i);
                String value = attributes.getValue(i);
                ReflectionHelper.setFieldValue(object, element, value);
            }
//            System.out.println(object.getClass());
//            System.out.println(object.toString());
//            genericDao.save(object);
            counter++;
        }
    }

    public void endElement(String uri, String localName, String qName){
        element = null;
    }

    public void endDocument() {
        System.out.println("Stop parse "+xmlFile);
        System.out.println(counter);
    }

    public Object getObject(){
        return object;
    }
}
