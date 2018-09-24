package system.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import system.service.HibernateEntityManagerFactory;
import system.service.HibernateSessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Repository
public class GenericDao<T> {

    public void save(T entity){
        EntityManager entityManager = HibernateEntityManagerFactory.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.persist(entity);
        }catch (Exception e){
            e.printStackTrace();
        }
        entityTransaction.commit();
        entityManager.close();
    }

    public void saveOrUpdate(T entity){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
        session.close();
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
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
