package appCSV.readers;

import appCSV.writers.WriteToFile;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static appCSV.config.Config.debug;
import static appCSV.config.Config.isDeleteFirstLine;

public class ReadErrorFilesByLines implements ReadCSV<String[]> {
    public String errorCode; // строка выдачи в файл ошибок
    public AtomicLong counter;


    @Override
    public long readFile(File file, List<String[]> resultList) throws CsvException {

        errorCode = "{File=" + file + "}\n" +
                "{Errors: \n}";
        /**
         * чтение из файла построчно в список List<String>, кроме первой строки
         */
        List<String> listLines;
        try (Stream<String> streamLines = Files.lines(file.toPath())) {
            counter = new AtomicLong(0);
            int skip = 0;
            if (isDeleteFirstLine) {
                skip = 1;
            }
            listLines = streamLines
                    .peek(line -> counter.incrementAndGet())
                    .skip(skip)
                    .toList();
        } catch (IOException ex) {
            throw new RuntimeException("Error reading file: " + file, ex);
        }
        if (debug) {
            System.out.println("Первая фаза чтения из файла завершена. Элементов =" + listLines.size());
        }
        resultList.addAll(listItemProcessing(listLines));


        WriteToFile writeToFile = new WriteToFile();
        errorCode = errorCode +
                "{Item read count=" + counter + "}" +
                "{List item count =" + resultList.size() + "}\n";

        writeToFile.writeDataToFile(resultList, errorCode);
        return counter.get();
    }

    /**
     * Обработка списка, преобразование substring внутри кавычек в отдельный элемент,
     * разбиение на элементы по разделителю ","
     *
     * @return список из String[]
     */
    public LinkedList<String[]> listItemProcessing(List<String> listLines) {
        LinkedList<String[]> listResult = new LinkedList<>();
        int countLines = 0;
        for (String stringLineOriginal : listLines) {
            countLines++;
            StringBuilder stringLine = new StringBuilder(stringLineOriginal);

            String[] arrStringElementsTemp = new String[12];

            if (!isValidQuotesLine(stringLine, arrStringElementsTemp, countLines, stringLineOriginal)) {
                continue;
            }

            String[] strArr = stringLine.toString().split(",");

            for (int i = 0; i < 12; i++) {
                if (i < 7) {
                    if (i < strArr.length) {
                        arrStringElementsTemp[i] = strArr[i];
                    } else {
                        arrStringElementsTemp[i] = "";
                    }
                }
                if (i == 7) {
                    continue;
                }
                if (i > 7) {
                    if (i < strArr.length) {
                        arrStringElementsTemp[i] = strArr[i - 1];
                    } else {
                        arrStringElementsTemp[i] = "";
                    }
                }
            }
            listResult.add(arrStringElementsTemp);
        }
        System.out.println("Вторая фаза завершена. Элементов =" + listResult.size());
        return listResult;
    }

    public boolean isValidQuotesLine(StringBuilder stringLine, String[] resArr, int count, String stringLineOriginal) {
        int begin, end;
        int index = 0;
        while ((index = stringLine.indexOf("\"\"", index)) != -1) {
            stringLine.replace(index, index + 2, "^");
            index++;
        }
        if ((end = stringLine.toString().lastIndexOf("\"")) != -1) {
            if ((begin = stringLine.toString().substring(0, end - 1).lastIndexOf("\"")) != -1) {
                if (begin != end) {
                    if (begin != 0) {
                        if (stringLine.charAt(begin - 1) != ',') {
                            errorCode = errorCode.concat("{Invalid quotes without comma. Error line=" + count + ", string=" + stringLineOriginal + "}\n");
                            return false;
                        }
                    }
                    if (end != stringLine.length() - 1) {
                        if (stringLine.charAt(end + 1) != ',') {
                            errorCode = errorCode.concat("{Invalid quotes without comma. Error line=" + count + ", string=" + stringLineOriginal + "}\n");
                            return false;
                        }
                    }
                    /**
                     * запись в 8-й элемент адреса
                     */

                    resArr[7] = stringLine.substring(begin + 1, end);

                    /**
                     *  удаление субстроки обрамленной кавычками
                     */
                    if (begin == 0) {
                        if (end >= stringLine.length() - 1) {
                            stringLine.delete(begin, end);
                        } else {
                            stringLine.delete(begin, end + 1);
                        }
                    } else {
                        stringLine.delete(begin - 1, end + 1);
                    }
                    while ((index = stringLine.indexOf("\"", index)) != -1) {
                        stringLine.replace(index, index + 1, "");
                        index++;
                    }

                } else {
                    errorCode = errorCode.concat("{Empty quotes. Error line=" + count + ", string=" + stringLineOriginal + "}\n");
                    return false;
                }

            } else {
                errorCode = errorCode.concat("{Unpaired quotation. Error line=" + count + ", string=" + stringLineOriginal + "}\n");
                return false;
            }
        }
        /*if (strArr.length < 11) {
            errorCode = errorCode.concat("{Quantity elements . Error line=" + count + ", string=" + stringLineOriginal + "}\n");
            return false;
        }*/
        return true;
    }
}