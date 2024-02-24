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
        resultList.addAll(listItemProcessing(listLines, fields.length));

        if (debug) {
            WriteToFile writeToFile = new WriteToFile();// запись ошибочных, исключенных строк
            errorCode = errorCode +
                    "{Item read count=" + counter + "}" +
                    "{List item count =" + resultList.size() + "}\n";

            writeToFile.writeDataToFile(resultList, errorCode, 1);
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
        if (debug) {
            System.out.println("Phase two is complete. Elements=" + listResult.size());
        }
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