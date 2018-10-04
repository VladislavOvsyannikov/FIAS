package system.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import system.service.HibernateEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GenericDao<T> {

    private static final Logger logger = Logger.getLogger(GenericDao.class);


    public void saveBatch(T[] entities, int numberOfObjects){
        EntityManager entityManager = HibernateEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityManager.setFlushMode(FlushModeType.COMMIT);
        try {
            entityTransaction.begin();
            for (int i = 0; i < numberOfObjects; i++) {
                entityManager.persist(entities[i]);
            }
            entityTransaction.commit();
        } catch (RuntimeException e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            logger.error(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void saveOrUpdateBatch(T[] entities, int numberOfObjects){
        EntityManager entityManager = HibernateEntityManagerFactory.getEntityManagerFactory().createEntityManager();
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
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            logger.error(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void save(T entity){
        EntityManager entityManager = HibernateEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.persist(entity);
            entityTransaction.commit();
        }catch (RuntimeException e){
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            logger.error(e.getMessage());
        }
        entityManager.close();
    }

    public T getEntity(String q, Class c){
        T t = null;
        EntityManager entityManager = HibernateEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        Query query = entityManager.createNativeQuery(q, c);
        try {
            t = (T) query.getResultList().get(0);
        }catch (Exception e){
            e.getMessage();
        }
        entityManager.close();
        return t;
    }

    public List<T> getEntities(String q, Class c) {
        List<T> list = null;
        EntityManager entityManager = HibernateEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        Query query = entityManager.createNativeQuery(q, c);
        try {
            list = query.getResultList();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        entityManager.close();
        return list;
    }
}
