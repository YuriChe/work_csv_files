package appCSV.writers;

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

    @Override
    public long writeListToTable(List<T> customerList, int begin, int end) {

        Session session = sessionFactory.openSession();
//    записывает в таблицу postresql List<T> c с индекса begin до end включительно
        if (end <= 0) { // значит все
            end = customerList.size();
        }
        long countPersist = 0;
        long countRecords = 0;
        Transaction transaction = session.beginTransaction();

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
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return -1;

        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return countPersist;
    }

    public long writeListToTable(List<T> customerList) {
        return writeListToTable(customerList, 0, 0);
    }
}
