package appCSV;

import appCSV.config.Config;
import appCSV.config.HibernateConfig;
import appCSV.entity.CustomerWBGeo;
import appCSV.entity.DataRecord;
import appCSV.readers.*;
import appCSV.writers.WriteListToTable;
import appCSV.writers.WriteListToTableImpl;
import com.opencsv.exceptions.CsvException;
import org.hibernate.SessionFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static appCSV.config.Config.debug;

public class    AppReadFilesToTableCustomerGeo {

//#ВНИМАНИЕ. у файла важно чтобы перчая строка была с верным количеством полей

    public static void main(String[] args) throws CsvException {

        Config.getInstance();
        SessionFactory sessionFactory = HibernateConfig.getSession().getSessionFactory();

        WriteListToTable<CustomerWBGeo> write = new WriteListToTableImpl<>(sessionFactory);

        ReadCSV<String[]> readCSV = new ReadCSV_All();// читает весь файл через framework openCVS
        ReadCSV<String[]> readCSVByLine = new ReadCSV_ByLine();// читает построчно через framework openCVS
        ReadCSV<String[]> readErrorCSV = new ReadErrorFilesByLines(); // читает построчно свой код

        HashMap<File, DataRecord> dataRecords = new HashMap<>();// output деталей чтения

        ReadingCSV<String[]> readingCSV = new ReadingCSV<>(readErrorCSV);// контроллер reader из файла

//        ReorganizeList<CustomerWB> reorganizeList = new ReorganizeListImpl();

        List<String[]> listFromFile = new ArrayList<>();
        List<CustomerWBGeo> customerWBGeos = new ArrayList<>();

        EnrollEntityGeo enrollEntity = new EnrollEntityGeo();

        long counterRead;
        int i = 0;
        File[] files = GetArrFilesInDir.getArrFiles();

        for (File file : files) {
            i++;
// устанавливается метод чтения по умолчанию
            readingCSV.setReader(readErrorCSV);

            listFromFile.clear();
            DataRecord record = new DataRecord();
            try {
                counterRead = readingCSV.read(file, listFromFile);// читает файл в список
//                listFromFile.stream().flatMap(Arrays::stream).forEach(System.out::println);
                record.setCounterRowsFile(counterRead);
                record.setMethodReading(readingCSV.getReader().getClass().getName());

            } catch (CsvException e) {
                System.out.println("Another method will be applied ...");
                readingCSV.setReader(readErrorCSV); //если чтение с ошибкой, читает метод для ошибочных
                listFromFile.clear();
                counterRead = readingCSV.read(file, listFromFile);
                record.setCounterRowsFile(counterRead);
                record.setMethodReading(readingCSV.getReader().getClass().getName());
            }

            System.out.println(i * 100 / files.length + "% complete.");
            customerWBGeos.clear();
            Long rec = enrollEntity.enrollToCustomersGeo(listFromFile, customerWBGeos);// преобразует список из строк файла в список entity

            if (debug) {
                System.out.println(rec + " ЗАНЕСЕНО В ENTITY");
            }

            long countTransaction = write.writeListToTable(customerWBGeos, 0, -1); //запись в БД

//            List<CustomerWB> listCustomerUnique = reorganizeList.toUniqueList(customerWBS);
            if (countTransaction == -1) {
                System.err.println("No data has been recorded!");
            } else {
                record.setCounterDBRecords(countTransaction);
            }
            dataRecords.put(file, record);
        }

        if (true) {
            dataRecords.forEach((key, value) -> System.out.println("file=" + key +
                    ", rows=" + value.getCounterRowsFile() +
                    ", dbRecods=" + value.getCounterDBRecords() +
                    ", methodreading=" + value.getMethodReading()));
        }

        sessionFactory.close();
    }
}
