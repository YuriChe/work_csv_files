package appCSV;

import appCSV.config.Config;
import appCSV.config.HibernateConfig;
import appCSV.entity.CustomerWB;
import appCSV.entity.DataRecord;
import appCSV.readers.*;
import appCSV.writers.*;
import com.opencsv.exceptions.CsvException;
import org.hibernate.SessionFactory;

import java.io.File;
import java.util.*;

public class AppReadFilesToTable {

    public static void main(String[] args) throws CsvException {

        final Config config = new Config();

        ReadCSV<String[]> readCSV = new ReadCSV_All();// читает весь файл через framework openCVS
        ReadCSV<String[]> readCSVByLine = new ReadCSV_ByLine();// читает построчно через framework openCVS
        ReadCSV<String[]> readErrorCSV = new ReadErrorFilesByLines(); // читает построчно свой код

        HashMap<File, DataRecord> dataRecords = new HashMap<>();// output деталей чтения

        ReadingCSV<String[]> readingCSV = new ReadingCSV<>(readErrorCSV);// контроллер reader из файла

//        ReorganizeList<CustomerWB> reorganizeList = new ReorganizeListImpl();

        List<String[]> listFromFile = new ArrayList<>();
        List<CustomerWB> listCustomerWB = new ArrayList<>();

        EnrollEntity enrollEntity = new EnrollEntity();

        SessionFactory sessionFactory = HibernateConfig.getSession().getSessionFactory();

        WriteListToTable<CustomerWB> write = new WriteListToTableImpl<>(sessionFactory);

        long counterRead;

        for (File file : GetArrFilesInDir.getArrFiles()) {

            readingCSV.setReader(readCSV);
            listFromFile.clear();
            DataRecord record = new DataRecord();

            try {
                counterRead = readingCSV.read(file, listFromFile);// читает файл в список
//                listFromFile.stream().flatMap(Arrays::stream).forEach(System.out::println);
                record.setCounterRowsFile(counterRead);
                record.setMethodReading(readingCSV.getReader().getClass().getName());

            } catch (CsvException e) {
                e.printStackTrace();
                readingCSV.setReader(readErrorCSV); //если чтение с ошибкой, читает метод для ошибочных
                listFromFile.clear();
                counterRead = readingCSV.read(file, listFromFile);
                record.setCounterRowsFile(counterRead);
                record.setMethodReading(readingCSV.getReader().getClass().getName());
            }

            listCustomerWB.clear();
            enrollEntity.enrollToCustomers(listFromFile, listCustomerWB);// преобразует список из строк файла в список entity

            long countTransaction = write.writeListToTable(listCustomerWB, 0, -1); //запись в БД

//            List<CustomerWB> listCustomerUnique = reorganizeList.toUniqueList(customerWBS);
            if (countTransaction == -1) {
                System.err.println("No data has been recorded!");
            } else {
                record.setCounterDBRecords(countTransaction);
            }
            dataRecords.put(file, record);
        }

        if (config.debug) {
            dataRecords.forEach((key, value) -> System.out.println("file=" + key +
                    ", rows=" + value.getCounterRowsFile() +
                    ", dbRecods=" + value.getCounterDBRecords() +
                    ", methodreading=" + value.getMethodReading()));
        }

        sessionFactory.close();
    }
}
