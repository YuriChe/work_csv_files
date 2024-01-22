package appCSV.search;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class SearchCSV extends SearchCSV0 {

    public boolean fileFields(String file) {
//        чтение оглавления столбцов
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).build()) {
            String[] nextLine = reader.readNext();

            int i = 0;
            for (String field : nextLine) {
                System.out.print(i + " " + field);
                i++;
                System.out.println();
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Ошибка файла при чтении заголовков");
            return false;
        }
        return true;
    }

    public boolean isNumeric(String str) {
        // Проверяем, что каждый символ строки является цифрой
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
