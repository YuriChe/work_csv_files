package appCSV.names;

import java.util.*;
import java.util.stream.Collectors;

public class GetNamesImpl implements GetNames {
    @Override  //        создает Set имен всех из БД
    public Set<String> namesSet(List<String[]> list) {

        return list.parallelStream()
                .map(strings -> strings[2].split(" "))
                .filter(strArray -> strArray.length > 1)
                .map(str -> str[1])
                .collect(Collectors.toSet());
    }

    @Override //        создает List имен всех из БД
    public List<String> namesList(List<String[]> list) {

        List<String> listEnd = list.parallelStream()
                .map(strings -> {
                    if (strings[2] != null) {
                        return strings[2].split(" ");
                    } else return new String[0];
                })
                .filter(strArray -> strArray.length > 1)
                .map(str -> str[1])
                .collect(Collectors.toList());
        return listEnd;
    }

    @Override // создание списка уникальных имеющихся имен
    public Set<String> listUniqueNames(List<String[]> list) {

        Set<String> setUniqueNames = namesSet(list);
        System.out.println("Total unique names:" + setUniqueNames.size());
        return setUniqueNames;
    }

    @Override//    выводит map с именами и их количеством
    public HashMap<String, Long> countNames(List<String[]> list) {
        List<String> namesList = namesList(list);
        long startTime = System.currentTimeMillis();

        HashMap<String, Long> countNames = (HashMap<String, Long>) namesList.parallelStream().
                collect(Collectors.groupingBy(
                        String::toString,
                        Collectors.counting()
                ));

        long endTime = System.currentTimeMillis();
        System.out.println("ELAPSED TIME " + (endTime - startTime));
        return countNames;
    }

    /**
     * @param map      map с именами и их количеством упоминаний
     * @param quantity сколько элементов
     * @param start    если 0, то количество с конца
     */
    public void printSortNames(Map<String, Long> map, int quantity, int start) {
        // Вывод Map с именами и количеством
        if (start == 0) {
            start = map.size() - quantity;
        }
        map.entrySet()
                .stream()
                .skip(start)
                .limit(quantity)
                .forEach(x -> System.out.println(x.getKey() + "\t: " + x.getValue()));
        System.out.println("\nTotal elements=" + map.size());
    }

    public void printExactlyNames(Map<String, Integer> map, String text) {
        // вывод имен содержащих строку text
        map.entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(text))
                .forEach(x -> System.out.println(x.getKey() + ": " + x.getValue()));
    }

    public LinkedHashMap<String, Long> sortMapNames(Map<String, Long> map) {
        LinkedHashMap<String, Long> sortedMap = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e2, // Если ключи совпадают, выберите одно из значений
                        LinkedHashMap::new  // Сохранить порядок вставки
                ));
        return sortedMap;
    }

}

