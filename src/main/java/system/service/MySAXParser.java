package system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Log4j2
@Service
@RequiredArgsConstructor
public class MySAXParser extends DefaultHandler {

    private final EntityManagerFactory entityManagerFactory;
    private String fileName;
    private String databaseType;
    private int numberOfObjects;
    private Object[] objects;
    private int index;
    private int counter;

    @Override
    public void startDocument() {
        index = -1;
        counter = 0;
        objects = new Object[numberOfObjects];
        log.info("Start parse " + fileName);
    }

    @Override
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
                if (databaseType.equals("complete")) saveBatch(objects, objects.length);
                else saveOrUpdateBatch(objects, objects.length);
                counter += index + 1;
                log.info("Saved " + counter + " objects");
                index = -1;
            }
        }
    }

    @Override
    public void endDocument() {
        if (index > -1) {
            if (databaseType.equals("complete")) saveBatch(objects, index + 1);
            else saveOrUpdateBatch(objects, index + 1);
            counter += index + 1;
            log.info("Saved " + counter + " objects");
        }
        log.info("Stop parse " + fileName);
        log.info("Parsed " + counter + " objects");
    }

    private void saveBatch(Object[] entities, int numberOfObjects) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            for (int i = 0; i < numberOfObjects; i++) {
                if (i > 0 && i % 200 == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(entities[i]);
            }
            entityTransaction.commit();
        } catch (RuntimeException e) {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            log.error(e);
        } finally {
            entityManager.close();
        }
    }

    private void saveOrUpdateBatch(Object[] entities, int numberOfObjects) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            for (int i = 0; i < numberOfObjects; i++) {
                if (i > 0 && i % 200 == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.merge(entities[i]);
            }
            entityTransaction.commit();
        } catch (RuntimeException e) {
            if (entityTransaction.isActive()) entityTransaction.rollback();
            log.error(e);
        } finally {
            entityManager.close();
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setNumberOfObjects(int numberOfObjects) {
        this.numberOfObjects = numberOfObjects;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }
}