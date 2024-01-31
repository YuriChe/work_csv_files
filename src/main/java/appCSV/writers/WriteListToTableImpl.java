package appCSV.writers;

import appCSV.config.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.ListIterator;

public class WriteListToTableImpl<T> implements WriteListToTable<T> {

    SessionFactory sessionFactory;

    public WriteListToTableImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //    записывает в таблицу postresql List<T> c с индекса begin до end включительно
    @Override
    public int writeListToTable(List<T> customerList, int begin, int end) {

        //    SessionFactory sessionFactory = new HibernateConfig().getSession.buildSessionFactory();

        if (end <= 0) {
            end = customerList.size();
        }
        // Создание сессии
        Session session = sessionFactory.openSession();
        int countPersist = 0;
        int countRecords = 0;
        try {
            Transaction transaction = session.beginTransaction();

            // Ваши операции с базой данных, например, сохранение объекта
            try {

                ListIterator<T> iterator = customerList.listIterator();
                T customer;
                while (iterator.hasNext() && countRecords <= end) {
                    countRecords++;
                    if (countRecords >= begin) {
                        customer = iterator.next();
                        session.persist(customer);
                        countPersist++;
                    }
                }
//                System.out.println(transaction.getStatus());
//                System.out.println(transaction.isActive());
                transaction.commit();


            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return countPersist;
    }

    public int writeListToTable(List<T> customerList) {
        return writeListToTable(customerList, 0, 0);
    }
}
