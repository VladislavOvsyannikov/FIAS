//package system.service;
//
//import com.fasterxml.classmate.AnnotationConfiguration;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//import javax.persistence.EntityManager;
//
//public class HibernateUtil {
//
//    private static EntityManager entityManager;
//
//    public static synchronized EntityManager getEntityManager() {
//        if (entityManager == null) {
//            entityManager = new Configuration().configure("hibernate.cfg.xml").
//                    buildSessionFactory();
//        }
//        return entityManager;
//    }
//}