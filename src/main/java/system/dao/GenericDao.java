package system.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import system.service.HibernateEntityManagerFactory;
import system.service.Installer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

@Repository
public class GenericDao<T> {

    private static final Logger logger = Logger.getLogger(GenericDao.class);

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

    public void saveOrUpdate(T entity){
    }

    public void saveBatch(T[] entities){
        EntityManager entityManager = HibernateEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            for (int i = 0; i < entities.length; i++) {
                if (i > 0 && i % 200 == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                if (entities[i]!=null) {
                    entityManager.persist(entities[i]);
                }
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
}
