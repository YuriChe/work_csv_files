package appCSV;

import appCSV.config.Config;
import appCSV.entity.DataRecord;
import appCSV.names.GetNamesImpl;
import appCSV.readers.*;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static appCSV.config.Config.debug;

public class GetNamesUniqueFile {

    public static void main(String[] args) throws CsvException {


        Config.getInstance();

        ReadCSV<String[]> readCSV = new ReadCSV_All();// читает весь файл через framework openCVS
        ReadCSV<String[]> readCSVByLine = new ReadCSV_ByLine();// читает построчно через framework openCVS
        ReadCSV<String[]> readErrorCSV = new ReadErrorFilesByLines(); // читает построчно свой код

        HashMap<File, DataRecord> dataRecords = new HashMap<>();// output деталей чтения

        ReadingCSV<String[]> readingCSV = new ReadingCSV<>(readErrorCSV);// контроллер reader из файла
        GetNamesImpl getNames = new GetNamesImpl();

//        ReorganizeList<CustomerWB> reorganizeList = new ReorganizeListImpl();

        List<String[]> listFromFile = new ArrayList<>();
        LinkedHashMap<String, Long> resultMapNames = new LinkedHashMap<>();

        long counterRead;
        int i = 0;
        File[] files = GetArrFilesInDir.getArrFiles();
        for (File file : files) {
            i++;

            // Здесь установить основной метод чтения
            readingCSV.setReader(readErrorCSV);

            listFromFile.clear();
            DataRecord record = new DataRecord();
            System.out.println(i * 100 / files.length + "% complete.");

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
//            List<CustomerWB> listCustomerUnique = reorganizeList.toUniqueList(customerWBS);
            dataRecords.put(file, record);

            HashMap<String, Long> mapFromFile = getNames.countNames(listFromFile);

            for (String key : mapFromFile.keySet()) {
                    resultMapNames.computeIfPresent(key, (k, v) -> v + mapFromFile.get(key));
                    resultMapNames.computeIfAbsent(key, v -> mapFromFile.get(key));
            }
        }

        LinkedHashMap <String, Long> resultNames = getNames.sortMapNames(resultMapNames);
        getNames.printSortNames(resultNames, 5000, 5000);
        System.out.println("Elements=" + resultNames.size());


        /*ArrayList<String[]> resultForFile = new ArrayList<>(resultMapNames.size());

        resultMapNames.entrySet()
                .forEach(entry -> {
                    String[] nameC = {entry.getKey(), String.valueOf(entry.getValue())};
                    resultNames.add(nameC);
                });*/

        if (debug) {
            dataRecords.forEach((key, value) -> System.out.println("file=" + key +
                    ", rows=" + value.getCounterRowsFile() +
                    ", dbRecods=" + value.getCounterDBRecords() +
                    ", methodreading=" + value.getMethodReading()));
        }
    }
}
