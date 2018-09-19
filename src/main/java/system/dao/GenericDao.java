package system.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import system.service.HibernateSessionFactory;

import java.util.List;

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

    public void update(T entity){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(T entity){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    public List<T> getAll(String s){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from " + s);
        List<T> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public T getElement(String q, Object o){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(q);
        query.setParameter("n", o);
        T t = (T) query.list().get(0);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    public List<T> getList(String q, Object o){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery(q);
        query.setParameter("n", o);
        List<T> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
