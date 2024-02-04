package appCSV;

import appCSV.config.Config;
import appCSV.config.HibernateConfig;
import appCSV.entity.CustomerWB;
import appCSV.readers.ReadCSV_ByLine;
import appCSV.readers.ReadCSV_Entity;
import appCSV.writers.*;
import org.hibernate.SessionFactory;

import java.io.File;
import java.util.List;

import static appCSV.config.Config.DEBUG;

public class AppReadFilesToTable {

    public static void main(String[] args) {
        Config config = new Config();
        ReorganizeList<CustomerWB> reorganizeList = new ReorganizeListImpl();
        SessionFactory sessionFactory = HibernateConfig.getSession().getSessionFactory();

        ReadCSV_Entity readCSVEntity = new ReadCSV_Entity();
        WriteListToTable<CustomerWB> write = new WriteListToTableImpl<>(sessionFactory);

        for (File file : readCSVEntity.getArrFiles()) {//чтение из файла клиентов в список

            List<CustomerWB> listCustomersOne = readCSVEntity.readerToCustomers(String.valueOf(file));
//            List<CustomerWB> listCustomerUnique = reorganizeList.toUniqueList(listCustomersOne);

            if (DEBUG) {
                ReadCSV_ByLine.fileFields(file.getPath());
                listCustomersOne.stream().limit(10).forEach(System.out::println);
                System.out.println("Количество полей в списках " + listCustomersOne.size() + " " + listCustomersOne.size());
            }
//            запись в базу списка
            int countRecords = write.writeListToTable(listCustomersOne, 0, -1);
            if (countRecords == -1) {
                System.err.println(" Записей не произведено! ");
            }
            System.out.println(" Передано в БД " + countRecords);
        }

        sessionFactory.close();
    }
}
