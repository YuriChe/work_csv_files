package appCSV.readers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvMalformedLineException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static appCSV.config.Config.isDeleteFirstLine;

/**
 * читает из файла через метод readAll
 * выдает
 */
public class ReadCSV_All implements ReadCSV<String[]> {

    @Override
    public long readFile(File file, List<String[]> entries) throws CsvException {
        long countRows = 0;

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).build()) {
            entries.addAll(reader.readAll());
            countRows = entries.size();

            if (isDeleteFirstLine) {
                entries.remove(0);//удаление строки с названиями столбцов
            }

        } catch (CsvMalformedLineException | CsvException e) {
            throw new CsvException();

        } catch (IOException e) {
            throw new RuntimeException();
        }

        return countRows;
    }
}
