package appCSV.search;

import org.hibernate.SessionFactory;

import java.util.List;

public interface GetListFromDB<T> {

    void setHQLAddress(String address);

    List<T> resultList(SessionFactory sessionFactory, int number);

    // Пример HQL-запроса для получения элементов, удовлетворяющих условию
}
