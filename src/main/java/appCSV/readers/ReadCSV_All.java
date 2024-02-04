package appCSV.readers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV_All implements IReadCSV<String[]> {
    public static long countTotalRows;

    @Override
    public List<String[]> reader(String file) {
        List<String[]> entries = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).build()) {

            entries = reader.readAll();
            System.out.println(file + " ПРОЧИТАНО " + entries.size());
        } catch (Exception e) {
           e.getStackTrace();
        } finally {
            assert entries != null;
            countTotalRows += entries.size();
        }
        return entries;
    }
}
