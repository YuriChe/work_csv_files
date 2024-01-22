package appCSV.config;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

        private static final SessionFactory sf = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        public static jakarta.persistence.EntityManager getEntityManager () {
            return (EntityManager) sf.createEntityManager();
        }

        public static Session getSession () {
            return sf.openSession();
        }
    }

