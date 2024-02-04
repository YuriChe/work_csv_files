package appCSV.readers;

import appCSV.config.Config;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.validators.RowValidator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ReadCSV_ByLine implements IReadCSV<String[]> {

    public static long countTotalRows = 0;

    public static boolean fileFields(String file) {
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

    public File[] getArrFiles() {
        try {
            File dir = new File(Config.PATH);
            File[] files = dir.listFiles();
            if (files == null) {
                System.out.println("Нет файлов с данными");
            }
            return files;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String[]> reader(String file) {
        return reader(file, 0, 0);
    }

    public List<String[]> reader(String file, int skip, int stop) {

        long i = 0;

        RowValidator rowValidator = new RowValidator() {
            @Override
            public boolean isValid(String[] arrStr) {
//                return arrStr.length == 12;
                return arrStr.length > 0;
//          && !arrStr[2].isBlank() && !arrStr[1].isBlank() && arrStr[7].isBlank();
            }

            @Override
            public void validate(String[] arrStr) throws CsvValidationException {
                if (!isValid(arrStr)) {
                    throw new CsvValidationException("Строка с ошибкой, счетчик =");
                }
            }
        };

        LinkedList<String[]> resultList = new LinkedList<>();
        String[] nextLine;

        CSVParser parser = new CSVParserBuilder().build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).withCSVParser(parser).build()) {
            while ((nextLine = reader.readNext()) != null) {

                rowValidator.validate(nextLine);

                if (i <= skip) {
                    i++;
                    continue;
                }
                resultList.add(nextLine);
//                previousLine = Arrays.copyOf(nextLine, nextLine.length);
                i++;
                if (stop != 0 && stop <= i) {
                    break;
                }
            }
        } catch (CsvValidationException e) {
            System.err.println(e.getMessage() + " " + i);
        } catch (IOException e) {
            System.err.println("!!! Ошибка файла " + file + " " + e.getMessage());
//            System.out.println(Arrays.toString(nextLine));
//            e.printStackTrace(System.err);
        } finally {
            countTotalRows += i;
            System.out.println(file + " ПРОЧИТАНО " + resultList.size() + " операций чтения: " + countTotalRows);
        }
        return resultList;
    }
}
