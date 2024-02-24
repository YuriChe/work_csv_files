package appCSV.search;

import appCSV.entity.CustomerWB;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class GetListCustomerFromDBImpl implements GetListFromDB<CustomerWB> {

    private String address;

    @Override
    public void setHQLAddress(String address) {
        this.address = address;
    }

    @Override
    public List<CustomerWB> resultList(SessionFactory sessionFactory, int number) {

        final String hql = """ 
                FROM CustomerWB c
                WHERE c.address ILIKE :addressPattern
                AND LENGTH(c.phone_number) = 11
                AND (c.phone_number LIKE '79%' OR c.comment LIKE '9%')
                """;

        Session session = sessionFactory.openSession();
        List<CustomerWB> resultList;

        if (number < 1) {
            resultList = session.createQuery(hql, CustomerWB.class)
                    .setParameter("addressPattern", address)
                    .getResultList();
        } else
            resultList = session.createQuery(hql, CustomerWB.class)
                    .setParameter("addressPattern", address)
                    .setMaxResults(number)
                    .getResultList();

        session.close();

        return resultList;
    }
}
