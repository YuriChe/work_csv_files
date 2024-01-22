package appCSV.names;

import appCSV.config.Config;
import appCSV.readers.ReadCSV_ByLine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppNames {
    public static void main(String[] args) throws IOException {
        final File dir = new File(Config.path);
        final File[] files = dir.listFiles();
        if (files == null) {
            System.out.println("Нет файлов с данными");
            return;
        }

        Names names = new Names();
        ReadCSV_ByLine readCSVByLine = new ReadCSV_ByLine();
        List<String[]> fullListRows = new ArrayList<>();
        for (File file : files) {
            fullListRows.addAll(readCSVByLine.reader(String.valueOf(file)));
        }

        //        вывод имен где содержится "юр"
//        names.printExactlyNames(intMap, "юр");

//        формирует map имя, количество упоминаний
        Map<String, Long> resNames = names.countNames(fullListRows);

        Map<String, Integer> intMap = resNames.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().intValue()
                ));

//        сортирует map
        Map<String, Integer> sortedMap = intMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e2, // Если ключи совпадают, выберите одно из значений
                        LinkedHashMap::new  // Сохранить порядок вставки
                ));

        // Вывод отсортированной Map
        names.printSortNames(sortedMap, 1000, 8500);
        System.out.println("Всего уникальных имен " + sortedMap.size());
//        System.out.println("Всего строк прочитано " + readCSVByLine.countTotalRows);
    }
}
