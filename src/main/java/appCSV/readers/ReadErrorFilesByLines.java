package appCSV.readers;

import appCSV.writers.WriteToFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ReadErrorFilesByLines extends ReadCSV_ByLine {
    public String errorCode; // строка выдачи в файл ошибок
    public AtomicInteger counter;

    @Override
    public List<String[]> reader(String file) {

        errorCode = "{File=" + file + "}\n" +
                "{Errors: \n}";
        /**
         * чтение из файла построчно в список List<String>, кроме первой строки
         */
        AtomicInteger counter;
        List<String> listLines;
        try (Stream<String> streamLines = Files.lines(Path.of(file))) {
            counter = new AtomicInteger(0);
            listLines = streamLines
                    .peek(line -> counter.incrementAndGet())
                    .skip(1)
                    .toList();
        } catch (IOException ex) {
            throw new RuntimeException("Error reading file: " + file, ex);
        }

        System.out.println("Первая фаза чтения из файла завершена. Элементов =" + listLines.size());

        List<String[]> listResult = listItemProcessing(listLines);

            /*listResult.parallelStream()
                    .filter(e -> e.length < 11)
                    .forEach(e -> System.out.println(Arrays.toString(e)));*/

            /*while ((line = bufferedReader.readLine()) != null) {
                countLines++;
                if (countLines % 10000 == 0) System.out.println("Прочитано " + counter * 100 / 100000 + "%");
                elLine = line.split(",");
                if (elLine.length != lenghtArr) {
                    errorCode = errorCode.concat("{SizeArray=" + elLine.length +
                            ", previous=" + lenghtArr
                            + ", Error line=" + countLines
                            + ", string=" + line + "\n");
                    continue;
                }
                lenghtArr = elLine.length;
                for (int i = 0; i < elLine.length; i++) {
                    str = new StringBuilder(elLine[i]);
                    if (str.isEmpty() || line.isEmpty() || str.length() == 0) {
                        errorCode = errorCode.concat("{Empty. Error line=" + counter + ", string=" + line + "\n");
                        continue;
                    }
//  ищет двойные кавычки с боков и если есть, то удаляет
                    int x, y;
                    if (str.indexOf("\"") != -1) {
                        x = 0;
                        y = 0;
                        if ((x = str.indexOf("\"")) == 0 && (y = str.lastIndexOf("\"")) == str.length() - 1 && x != y) {
                            System.out.println(str + "\n" + "ЭТО ГДЕ ОШИБКА");
                            str.deleteCharAt(x);
                            str.deleteCharAt(y - 1);
                        } else {
                            errorCode = errorCode.concat("{Quotes. Error line=" + counter + ", string=" + line + "\n");
                            continue;
                        }
                    }
                    listResult.add(elLine);
                }
            }*/

        WriteToFile writeToFile = new WriteToFile();
        errorCode = errorCode +
                "{Item read count=" + counter + "}" +
                "{List item count =" + listResult.size() + "}\n";

        writeToFile.writeDataToFile(listResult, errorCode);
        return listResult;
    }

    /**
     * Обработка списка, преобразование substring внутри кавычек в отдельный элемент,
     * разбиение на элементы по разделителю ","
     *
     * @return список из String[]
     */
    public List<String[]> listItemProcessing(List<String> listLines) {
        List<String[]> listResult = new ArrayList<>(100000);
        outloop:
        for (String stringLineOriginal : listLines) {
            StringBuilder stringLine = new StringBuilder(stringLineOriginal);
            int begin, end;
            String strAddress = "";
            String strWithoutQuotes = "";
            while ((begin = stringLine.indexOf("\"")) != -1) {
                end = stringLine.toString().lastIndexOf("\"");
                if (begin != end) {
                    if (begin != 0) {
                        if (stringLine.charAt(begin - 1) != ',') {
                            errorCode = errorCode.concat("{Invalid quotes without comma. Error line=" + counter + ", string=" + stringLine + "}\n");
                            break;
                        }
                    }
                    if (end != stringLine.length() - 1) {
                        if (stringLine.charAt(end + 1) != ',') {
                            errorCode = errorCode.concat("{Invalid quotes without comma. Error line=" + counter + ", string=" + stringLine + "}\n");
                            break;
                        }
                    }
                    strAddress = stringLine.substring(begin + 1, end);
                    stringLine.delete(begin, end + 1);

                    strWithoutQuotes = new String(stringLine);
                    strWithoutQuotes = strWithoutQuotes.replace(",,", ",");
                } else {
                    errorCode = errorCode.concat("{Empty quotes. Error line=" + counter + ", string=" + stringLine + "}\n");
                    break;
                }

            }
            String[] strArr = strWithoutQuotes.split(",");
            if (strArr.length < 11) {
                errorCode = errorCode.concat("{Quantity elements . Error line=" + counter + ", string=" + stringLine + "}\n");
                continue;
            }
            String[] strArrRes = new String[12];
            for (int i = 0; i < 12; i++) {
                if (i < 7) {
                    strArrRes[i] = strArr[i];
                }
                if (i == 7) {
                    strArrRes[7] = strAddress;
                }
                if (i > 7) {
                    strArrRes[i] = strArr[i - 1];
                }
            }
            listResult.add(strArrRes);
        }
        System.out.println("Вторая фаза завершена. Элементов =" + listResult.size());
        return listResult;
    }
}