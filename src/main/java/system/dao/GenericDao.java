package system.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import system.service.HibernateSessionFactory;

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
}
