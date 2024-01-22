package appCSV.readers;

import appCSV.Entity.Customer;
import appCSV.config.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class WriteToTable implements IWriteToTable {


    @Override
    public boolean writeToTable(String file) {
        //    SessionFactory sessionFactory = new HibernateConfig().getSession.buildSessionFactory();
        SessionFactory mySession = HibernateConfig.getSession().getSessionFactory();

        // Создание сессии
        try (
                Session session = mySession.openSession();) {
            // Ваши операции с базой данных, например, сохранение объекта
            Customer entity = new Customer();
            entity.setName("Example");
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }
}
