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
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e){
            e.getMessage();
        }
        session.close();
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
                if (i > 0 && i % 20 == 0) {
//                    entityTransaction.commit();
//                    entityTransaction.begin();
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
