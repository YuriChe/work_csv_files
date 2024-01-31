package appCSV.config;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

        private static final SessionFactory SESSION_FACTORY = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        public static jakarta.persistence.EntityManager getEntityManager () {
            return (EntityManager) SESSION_FACTORY.createEntityManager();
        }

        public static Session getSession () {
            return SESSION_FACTORY.openSession();
        }
    }

