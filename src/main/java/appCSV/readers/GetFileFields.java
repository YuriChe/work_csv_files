package appCSV.readers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class GetFileFields {
    /**
     * Читает оглавления столбцов csv файла в первой строке
     * @param file
     * @return массив строк с оглавлениями
     */
    public static String[] fileFields(String file) {
        String[] nextLine;
//
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).build()) {
            nextLine = reader.readNext();
            int i = 0;
            for (String field : nextLine) {
                System.out.print(i + " " + field);
                i++;
                System.out.println();
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Ошибка файла при чтении заголовков");
            return null;
        }
        return nextLine;
    }
}
