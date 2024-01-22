package appCSV;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class WriteToFile {
    private final String fileName;
    private final Path path;

    public WriteToFile(String file) {
//        src/main/resources/res0501.csv
        this.fileName = file;
        path = Paths.get(fileName);
        if (!Files.exists(path)) {
            // Если файла нет, создаем его
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean writeToFile(Collection<String[]> data) {
        // Запись данных в файл CSV
        /*WriteToFile writeToFile = new WriteToFile("src/main/resources/res0501.csv");
        if (!writeToFile.writeToFile(resultSetAll)) {
            throw new RuntimeException("Ошибка записи в файл!");
        }*/

        // Запись данных в файл CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName, false))) {
            writer.writeAll(data);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
