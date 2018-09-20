package system.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateEntityManagerFactory {

    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("persistence");;


    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
