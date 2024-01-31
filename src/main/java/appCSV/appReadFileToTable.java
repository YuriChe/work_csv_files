package appCSV;

import appCSV.config.HibernateConfig;
import appCSV.entity.CustomerWB;
import appCSV.readers.ReadCSV_Entity;
import appCSV.search.GetListCustomerFromDBImpl;
import appCSV.search.GetListFromDB;
import appCSV.writers.*;
import org.hibernate.SessionFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class appReadFileToTable {
    //    прочитать из файла в лист
//    из листа образовать в объект
//    лист объект перенести в таблицу
//    проверить размер файла, объекта, таблицы... вывести инфу
    public static void main(String[] args) {
        ReadCSV_Entity readCSVEntity = new ReadCSV_Entity();
        ReorganizeList<CustomerWB> reorganizeList = new ReorganizeListImpl();
        SessionFactory sessionFactory = HibernateConfig.getSession().getSessionFactory();
        WriteListToTable<CustomerWB> write = new WriteListToTableImpl<>(sessionFactory);


        /*for (File file : readCSVEntity.getArrFiles()) {
//            ReadCSV_ByLine.fileFields(file.getPath());
            List<CustomerWB> listCustomersOne = readCSVEntity.readerToCustomers(String.valueOf(file));
            List<CustomerWB> listCustomerWBS = reorganizeList.toUniqueList(listCustomersOne);
            System.out.println("Количество полей в списках " + listCustomersOne.size() + " " + listCustomerWBS.size());
            int x = write.writeListToTable(listCustomerWBS, 0, -1);
            if (x == -1) {
                System.out.println(" Записей не произведено! ");
            }
            System.out.println(" Передано в БД " + x);
        }*/

        GetListCustomerFromDBImpl getListCustomerFromDB = new GetListCustomerFromDBImpl();
        ArrayList<String> arrAddress = new ArrayList<>();
        arrAddress.add("%москва%бебеля%");
        arrAddress.add("%красноярск%свердловская%");

        List<CustomerWB> listResult = new ArrayList<>();
        for (String address : arrAddress) {
            getListCustomerFromDB.setAddress(address);
            listResult.addAll(getListCustomerFromDB.resultList(sessionFactory,0));
        }
        listResult.forEach(System.out::println);

        sessionFactory.close();
    }
}
