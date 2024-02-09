package appCSV;

import appCSV.config.Config;
import appCSV.config.HibernateConfig;
import appCSV.entity.CustomerWB;
import appCSV.search.GetListCustomerFromDBImpl;
import appCSV.writers.ReorganizeList;
import appCSV.writers.ReorganizeListImpl;
import appCSV.writers.WriteToFile;
import org.hibernate.SessionFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static appCSV.config.Config.*;
import static appCSV.writers.ReorganizeListImpl.trimSplitStreets;

public class AppCullFromTable {
    public static void main(String[] args) {

        Config config = new Config();

        SessionFactory sessionFactory = HibernateConfig.getSession().getSessionFactory();

        ReorganizeList<CustomerWB> reorganizeList = new ReorganizeListImpl();

        GetListCustomerFromDBImpl getListCustomerFromDB = new GetListCustomerFromDBImpl();

        List<String> listStreets = trimSplitStreets(getCurrentStreets()); // формирование пула улиц для выборки

        if (debug) {
            System.out.println(city);
            listStreets.forEach(System.out::println);
        }

        // поиск и выборка в итоговый лист результата по всем
        List<CustomerWB> listResult = new ArrayList<>();
        int i = 1;
        for (String address : listStreets) {

            String hqlAddress = "%" + city + "%" + address + "%";

            getListCustomerFromDB.setAddress(hqlAddress);

            listResult.addAll(getListCustomerFromDB.resultList(sessionFactory, 0));

            System.out.println("Выполнено на " + (i * 100 / (listStreets.size() + 1)) + "%");
            i++;
        }

        List<CustomerWB> listResultUnique = listResult.stream()
                .distinct()
                .toList();

        if (debug) { // вывод примера результата
            listResult.stream()
                    .limit(20)
                    .forEach(System.out::println);
        }

        //        запись в файл списка через реструктуризацию в String[] + файл с запросом
        WriteToFile writeToFile = new WriteToFile();
        String strToTXT =
                "{DB=" + "wb_customers" + "}\n" +
                        "{Region=}" + city +
                        "{Query=" + getCurrentStreets().toLowerCase() + "}\n" +
                        "{Records=" + listResultUnique.size() + "}\n";

        Path recordToFile = writeToFile.writeDataToFile(reorganizeList.toArrStringForFile(listResultUnique), strToTXT);

        if (recordToFile != null) {
            System.out.println("Запись данных по запросу произведена в файл: " + recordToFile);
            System.out.println("Строк: " + listResultUnique.size());
        } else {
            System.err.println("Результат не получен! Запись в файл не осуществлена! ");
        }
        sessionFactory.close();
    }
}
