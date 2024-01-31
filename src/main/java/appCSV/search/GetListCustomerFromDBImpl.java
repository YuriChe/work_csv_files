package appCSV.search;

import appCSV.config.HibernateConfig;
import appCSV.entity.CustomerWB;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class GetListCustomerFromDBImpl implements GetListFromDB<CustomerWB>{

    private String address;
    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public List<CustomerWB> resultList(SessionFactory sessionFactory, int number) {

        Session session = sessionFactory.openSession();
        List<CustomerWB> resultList;
        if (number < 1) {
            resultList = session.createQuery(
                            "SELECT DISTINCT c FROM CustomerWB c " +
                                    "WHERE c.address ILIKE :addressPattern AND LENGTH(c.phone_number) = 11 " +
                                    "AND (c.phone_number LIKE '79%' OR c.comment LIKE '9%')", CustomerWB.class)
                    .setParameter("addressPattern", address)
                    .getResultList();
        } else resultList = session.createQuery(
                        "SELECT DISTINCT c FROM CustomerWB c " +
                                "WHERE c.address ILIKE :addressPattern AND LENGTH(c.phone_number) = 11 " +
                                "AND (c.phone_number LIKE '79%' OR c.comment LIKE '9%')", CustomerWB.class)
                .setParameter("addressPattern", address)
                .setMaxResults(number)
                .getResultList();

        /*CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = builder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);
        criteriaQuery.select(root).where(builder.equal(root.get("yourPropertyName"), yourValue));

        List<Customer> resultList = session.createQuery(criteriaQuery).getResultList();*/
        return resultList;
    }
}
