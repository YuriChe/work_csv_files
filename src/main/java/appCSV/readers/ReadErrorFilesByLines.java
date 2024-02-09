package appCSV.readers;

import appCSV.writers.WriteToFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static appCSV.config.Config.debug;
import static appCSV.config.Config.isDeleteFirstLine;

public class ReadErrorFilesByLines implements ReadCSV<String[]> {
    public String errorCode; // строка выдачи в файл ошибок
    public AtomicLong counter;


    @Override
    public long readFile(File file, List<String[]> resultList) {

        errorCode = "{File=" + file + "}\n" +
                "{Errors: \n}";
        /**
         * чтение из файла построчно в список List<String>
         */
        List<String> listLines;
        int skip = 0;
        try (Stream<String> streamLines = Files.lines(file.toPath())) {

            counter = new AtomicLong(0);

            if (isDeleteFirstLine) {// пропуск первой строки, если нужно
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
            System.out.println("The first phase of reading from the file is complete. Rows=" + (listLines.size() + skip));
        }
// !!!!! внимание, тут чтение количества полей
        String[] fields = GetFileFields.fileFields(file);
        System.out.println(fields);
        resultList.addAll(listItemProcessing(listLines, 12));

        if (debug) {
            WriteToFile writeToFile = new WriteToFile();// запись ошибочных, исключенных строк
            errorCode = errorCode +
                    "{Item read count=" + counter + "}" +
                    "{List item count =" + resultList.size() + "}\n";

            writeToFile.writeDataToFile(resultList, errorCode);
        }

        return counter.get();
    }

    /**
     * Обработка списка строк из файла, преобразование substring внутри кавычек в отдельный элемент,
     * разбиение на элементы по разделителю ","
     *
     * @return список String[]
     */
    public List<String[]> listItemProcessing(List<String> listLines, int capacity) {
        List<String[]> listResult = new ArrayList<>();
        int countLines = 0;
        for (String stringLineOriginal : listLines) {
            countLines++;

            String[] arrStringElementsTemp = new String[capacity];

            splitLine(stringLineOriginal, arrStringElementsTemp);


            listResult.add(arrStringElementsTemp);
        }

        System.out.println("Вторая фаза завершена. Элементов =" + listResult.size());
        return listResult;
    }

    public void splitLine(String stringLine, String[] resArr) {

        ArrayList<String> splitLine = new ArrayList<>(12);

        Scanner scanner = new Scanner(stringLine);
        scanner.useDelimiter(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        while (scanner.hasNext()) {
            String el = scanner.next();
            el = el.replaceAll("^\"|\"$", "");
            splitLine.add(el);
        }
        splitLine.toArray(resArr);

        scanner.close();

    }
}





            /*if ((end = stringLine.toString().lastIndexOf("\"")) != -1) {
                if ((begin = stringLine.toString().substring(0, end - 1).lastIndexOf("\"")) != -1) {
                    if (begin != end) {
                        if (begin != 0) {
                            if (stringLine.charAt(begin - 1) != ',') {
                                errorCode = errorCode.concat("{Invalid quotes without comma. Error line=" + count +
                                        ", string=" + stringLineOriginal + "}\n");
                                splitLine = null;
                                return false;
                            }
                        }
                        if (end != stringLine.length() - 1) {
                            if (stringLine.charAt(end + 1) != ',') {
                                errorCode = errorCode.concat("{Invalid quotes without comma. Error line=" + count +
                                        ", string=" + stringLineOriginal + "}\n");
                                splitLine = null;
                                return false;
                            }
                        }*/
/**
 * запись в 8-й элемент адреса
 * <p>
 * удаление субстроки обрамленной кавычками
 * <p>
 * удаление субстроки обрамленной кавычками
 * <p>
 * удаление субстроки обрамленной кавычками
 * <p>
 * удаление субстроки обрамленной кавычками
 * <p>
 * удаление субстроки обрамленной кавычками
 * <p>
 * удаление субстроки обрамленной кавычками
 * <p>
 * удаление субстроки обрамленной кавычками
 *//*

                        resArr[7] = stringLine.substring(begin + 1, end);

                        *//**
 *  удаление субстроки обрамленной кавычками
 *//*
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
                        errorCode = errorCode.concat("{Empty quotes. Error line=" + count +
                                ", string=" + stringLineOriginal + "}\n");
                        splitLine = null;
                        return false;
                    }

                } else {
                    errorCode = errorCode.concat("{Unpaired quotation. Error line=" + count +
                            ", string=" + stringLineOriginal + "}\n");
                    splitLine = null;
                    return false;
                }
            }*/
        /*if (strArr.length < 11) {
            errorCode = errorCode.concat("{Quantity elements . Error line=" + count + ", string=" + stringLineOriginal + "}\n");
            return false;
        }*//*
            splitLine = null;
            return true;
        }
    }*/