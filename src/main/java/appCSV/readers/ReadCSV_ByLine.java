package appCSV.readers;

import appCSV.config.Config;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvMalformedLineException;
import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.validators.RowValidator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static appCSV.config.Config.isDeleteFirstLine;

public class ReadCSV_ByLine implements ReadCSV<String[]> {

    public long readerFileByLine(File file, List<String[]> resultList, int skip, int stop) throws CsvException {

        long i = 0;

        /*RowValidator rowValidator = new RowValidator() {
            @Override
            public boolean isValid(String[] arrStr) {
//                return arrStr.length == 12;
                return arrStr.length > 0
                        && !arrStr[2].isBlank() && !arrStr[1].isBlank() && arrStr[7].isBlank();
            }

            @Override
            public void validate(String[] arrStr) throws CsvValidationException {
                if (!isValid(arrStr)) {
                    throw new CsvValidationException("Строка с ошибкой, счетчик =");
                }
            }
        };*/

        String[] nextLine;
        CSVParser parser = new CSVParserBuilder().build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file)).withCSVParser(parser).build()) {
            while ((nextLine = reader.readNext()) != null) {

//                rowValidator.validate(nextLine);
                if (isDeleteFirstLine && skip < 1) {
                    skip = 1;
                }
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
        } catch (CsvMalformedLineException | CsvException e) {
            throw new CsvException();

        } catch (IOException e) {
            throw new RuntimeException();

        } finally {
            System.out.println("Read file: " + file);
            System.out.println("Reading operations: " + i + ", list entries: " + resultList.size());
        }
        return i;
    }

    @Override
    public long readFile(File file, List<String[]> resultList) throws CsvException {
        return readerFileByLine(file, resultList, 0, 0);
    }
}
