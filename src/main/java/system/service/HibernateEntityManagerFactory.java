package system.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Service
public class HibernateEntityManagerFactory {

    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("persistence");;


    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
