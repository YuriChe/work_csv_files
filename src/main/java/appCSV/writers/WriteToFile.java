package appCSV.writers;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;

import static appCSV.config.Config.resultPath;

public class WriteToFile {
    private Path fileTo;
    private Path fileToQuery;

    public Path writeDataToFile(Collection<String[]> data, String query) {

        LocalDate currentDate = LocalDate.now();
        String month = String.valueOf(currentDate.getMonth());
        month = month.substring(0, 3).toLowerCase();
        String day = String.valueOf(currentDate.getDayOfMonth());
        try {
            for (int meter = 1; true; meter++) {
                String fileDataResToStr = resultPath + "res_" + month + day + "_" + meter + ".csv";
                String fileTxtToStr = resultPath + "query_" + month  + day + "_" + meter + ".txt";
                fileTo = Paths.get(fileDataResToStr);
                fileToQuery = Paths.get(fileTxtToStr);

                if (!Files.exists(fileTo)) {
                    Files.createFile(fileTo);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Вывод результата в файлы
        try (CSVWriter writer = new CSVWriter(new FileWriter(String.valueOf(fileTo), false))) {
            writer.writeAll(data); // запись данных в файл
            Files.writeString(fileToQuery, query, StandardCharsets.UTF_8);// Запись строки в файл
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return fileTo;
    }
}
