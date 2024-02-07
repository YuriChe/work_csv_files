package appCSV.readers;

import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.util.List;

public interface ReadCSV<T> {

    //считывает из файла .csv в список
    long readFile(File file, List<T> resultList) throws CsvException;
}
