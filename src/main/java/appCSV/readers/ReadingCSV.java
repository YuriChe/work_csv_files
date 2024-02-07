package appCSV.readers;

import appCSV.config.Config;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ReadingCSV<T> {

    public ReadCSV<T> getReader() {
        return reader;
    }

    private ReadCSV<T> reader;

    public ReadingCSV(ReadCSV<T> reader) {
        this.reader = reader;
    }

    public void setReader(ReadCSV<T> reader) {
        this.reader = reader;
    }

    /**
     * чтение из файлa *.csv в переданный список
     *
     * @param resultList
     * @return hashmap с файлом и количество операций чтния из него
     * @throws CsvException
     */
    public long read(File file, List<T> resultList) throws CsvException {
        Config config = new Config();
        long countRead = 0;

        if (reader != null) {

            List<T> listRead = new LinkedList<>();

                countRead += reader.readFile(file, listRead);

                if (config.debug) {
                    GetFileFields.fileFields(file.getPath());
                    resultList.stream().limit(10).forEach(System.out::println);
                    System.out.println("Количество полей в списке " + listRead.size());
                }

                resultList.addAll(listRead);

        } else {
            throw new RuntimeException("Reader isn't define");
        }
//            List<CustomerWB> listCustomerUnique = reorganizeList.toUniqueList(listCustomersOne);
        return countRead;
    }
}
