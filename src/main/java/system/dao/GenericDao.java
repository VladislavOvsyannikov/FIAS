package system.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class GenericDao<T> {

    private static final Logger logger = LogManager.getLogger(GenericDao.class);
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveBatch(T[] entities, int numberOfObjects){
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
            if (entityTransaction.isActive())
                entityTransaction.rollback();
            logger.error(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void saveOrUpdateBatch(T[] entities, int numberOfObjects){
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
            if (entityTransaction.isActive())
                entityTransaction.rollback();
            logger.error(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void save(T entity){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
        }catch (RuntimeException e){
            if (entityTransaction.isActive())
                entityTransaction.rollback();
            logger.error(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    void saveOrUpdate(T entity){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.merge(entity);
            entityTransaction.commit();
        } catch (RuntimeException e) {
            if (entityTransaction.isActive())
                entityTransaction.rollback();
            logger.error(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void remove(T entity){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.remove(entityManager.contains(entity)? entity : entityManager.merge(entity));
            entityTransaction.commit();
        }catch (RuntimeException e){
            if (entityTransaction.isActive())
                entityTransaction.rollback();
            logger.error(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    T getEntity(String q, Class c){
        T t = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery(q, c);
        try {
            t = (T) query.getResultList().get(0);
        }catch (Exception e){
            e.getMessage();
        } finally {
            entityManager.close();
        }
        entityManager.close();
        return t;
    }

    List<T> getEntities(String q, Class c) {
        List<T> list = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNativeQuery(q, c);
        try {
            list = query.getResultList();
        }catch (Exception e){
            logger.error(e.getMessage());
        } finally {
            entityManager.close();
        }
        return list;
    }
}
