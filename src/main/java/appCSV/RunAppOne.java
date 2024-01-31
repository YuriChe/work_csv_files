package appCSV;

import appCSV.config.Config;
import appCSV.readers.ReadCSV_ByLine;
import appCSV.search.SearchCSV;
import appCSV.search.SearchCSVImpl;

import java.io.File;
import java.util.*;

public class RunAppOne {

    public static void main(String[] args) {

        final File dir = new File(Config.path);
        final File[] files = dir.listFiles();
        if (files == null) {
            System.out.println("Нет файлов с данными");
            return;
        }

        long startTime = System.currentTimeMillis();
        SearchCSV<String[]> search = new SearchCSVImpl();
        ReadCSV_ByLine readCSVByLine = new ReadCSV_ByLine();
//        List<String[]> finalResultByOnes = new CopyOnWriteArrayList<>(); //3026 - 362
        List<String[]> finalResultByOnes = new ArrayList<>(); //2928 - 362
//        Set<String[]> finalResultByOnes = new HashSet<>(); //2970 - 362
        List<String[]> tempList;

        for (File file1 : files) {
            String file = String.valueOf(file1);
//        чтение из файла в список
            tempList = readCSVByLine.reader(file, 1, 0);

// !!! НЕОБХОДИМО ПРОВЕРИТЬ класс ConditionPredicate где прописаны условия отбора/поиска
//            names.namesList(tempList);

            for (String[] str : search.selectSet(tempList)) {
                finalResultByOnes.add(Arrays.copyOf(str, str.length));
            }
            tempList.clear();
        }

        long endTime = System.currentTimeMillis();
        finalResultByOnes.stream().limit(10).forEach(x -> System.out.println(Arrays.toString(x)));
        System.out.println("TOTAL RECORDS " + finalResultByOnes.size());
        System.out.println("Elapsed time: " + (endTime - startTime));

//        Iterator<String[]> strI = resultSetAll.iterator();
//        while (strI.hasNext()) {
//            System.out.println(Arrays.toString(strI.next()));
//        }
//        System.out.println("TOTAL RECORDS " + resultSetAll.size());
    }
}

