package appCSV.readers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV_All implements IReadCSV<String[]> {
    public static long countTotalRows;

    @Override
    public List<String[]> reader(String file) throws IOException {
        List<String[]> entries = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).build()) {

            entries = reader.readAll();
            System.out.println(file + " ПРОЧИТАНО " + entries.size());
        } catch (IOException e) {
            throw new IOException("Проблема с файлом");
        } catch (CsvException e) {
            e.getStackTrace();
        } finally {
            assert entries != null;
            countTotalRows += entries.size();
        }
        return entries;
    }
}
