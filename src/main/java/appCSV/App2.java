package appCSV;

import appCSV.config.Config;
import appCSV.names.NameInt;
import appCSV.readers.ReadCSV_ByLine;
import appCSV.search.SearchCSV;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;

import static java.util.stream.Collectors.*;

public class App2 {
    public static void main(String[] args) {

        final File dir = new File(Config.path);
        final File[] files = dir.listFiles();
        if (files == null) {
            System.out.println("Нет файлов с данными");
            return;
        }

        ReadCSV_ByLine readCSVByLine = new ReadCSV_ByLine();
        List<String[]> listRows;

        listRows = readCSVByLine.reader(String.valueOf(files[0]), 990, 1000);
        SearchCSV searchCSV = new SearchCSV();
        searchCSV.fileFields(String.valueOf(files[0]));
        System.out.println(listRows.getClass());
        listRows.forEach(str -> System.out.println(Arrays.toString(str)));

//        AtomicReference<Integer> i = new AtomicReference<>(0);
//        listRows.stream().flatMap(Arrays::stream).forEach(str -> System.out.print(i.getAndSet(i.get() + 1) + " " + str + "; "));

        List<NameInt> list2 = new ArrayList<>();
        try {
            list2 = listRows.stream()
                    .filter(str -> StringUtils.isNumeric(str[9]))
                    .map(x -> new NameInt(x[2].split(" ")[1], Integer.valueOf(x[0])))
                    .toList();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        Map<String, Set<Integer>> map3;
        map3 = list2.stream()
                .collect(groupingBy(NameInt::getName, mapping(NameInt::getValue, toSet())));
//                                Collectors.summingInt(NameInt::getValue)))

        map3.entrySet().forEach(System.out::println);

//        list2.stream()
//                .forEach(System.out::println);

//        listRows2.forEach(str -> System.out.println(Arrays.toString(str)));
//        list2.stream().collect(Collectors.groupingBy(1, Collectors.summarizingInt(2)))

    }

}
