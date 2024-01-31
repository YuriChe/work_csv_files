package appCSV.names;

import appCSV.search.SearchCSVImpl;

import java.util.*;
import java.util.stream.Collectors;

public class Names extends SearchCSVImpl implements INames {
    @Override
    public Set<String> namesSet(List<String[]> list) {
//        создает Set имен всех из БД
        return list.parallelStream()
                .map(strings -> strings[2].split(" "))
                .filter(strArray -> strArray.length > 1)
                .map(str -> str[1])
                .collect(Collectors.toSet());
    }

    @Override
    public List<String> namesList(List<String[]> list) {
//        создает List имен всех из БД
         List<String> listEnd = list.parallelStream()
                .map(strings -> strings[2].split(" "))
                .filter(strArray -> strArray.length > 1)
                .map(str -> str[1])
                .collect(Collectors.toList());
        return listEnd;
    }

    @Override
    public Set<String> listUniqueNames(List<String[]> list) {
        // создание списка уникальных имеющихся имен
        Set<String> setUniqueNames = namesSet(list);
        System.out.println("Всего уникальных имен: " + setUniqueNames.size());
        return setUniqueNames;
    }

    @Override
//    выводит map с именами и их количеством
    public Map<String, Long> countNames(List<String[]> list) {
        List<String> namesList = namesList(list);
        long startTime = System.currentTimeMillis();

        Map<String, Long> countNames = namesList.parallelStream().
                collect(Collectors.groupingBy(
                        String::toString,
                        Collectors.counting()
                ));

        long endTime = System.currentTimeMillis();
        System.out.println("ELAPSED TIME " + (endTime - startTime));
        return countNames;
    }

    public void printSortNames(Map<String, Integer> map, int quantity, int start) {
        // Вывод Map с именами и количеством
        map.entrySet()
                .stream()
                .skip(start)
                .limit(quantity)
                .forEach(x -> System.out.println(x.getKey() + "\t: " + x.getValue()));
    }

    public void printExactlyNames(Map<String, Integer> map, String text) {
        // вывод имен содержащих строку text
        map.entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(text))
                .forEach(x -> System.out.println(x.getKey() + ": " + x.getValue()));
    }
}
