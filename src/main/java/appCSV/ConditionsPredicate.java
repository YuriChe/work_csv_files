package appCSV;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ConditionsPredicate implements Predicate<String[]> {

    @Override
    public boolean test(String[] strings) {
        HashSet<String> setC = new HashSet<>();
//        перечисление улиц по которым идет поиск
//        setC.add("СВЕРДЛОВСКАЯ");
        setC.add("МАТВЕЕВСКАЯ");
        setC.add("ВЕЕРНАЯ");
        setC.add("КРЕМЕНЧУГСКАЯ");

        if (strings == null) return false;
        boolean match = false;
        if (
                /*(strings[2].toUpperCase().contains("АТАЕВ") || strings[2].toUpperCase().contains("ИВАЧЁВА"))
                &&*/
//       номер 11 цифр
                strings[1].matches("\\d{11}") &&
//       номер мобильный .9...
                strings[1].startsWith("9", 1) &&
//       в адресе есть Москва
                strings[7].toUpperCase().contains("МОСКВА")) {

            Set<String> setRowAddress = Arrays.stream(strings[7]
                    .toUpperCase().replace(',', ' ')
                    .split(" "))
                    .collect(Collectors.toSet());

            setC.retainAll(setRowAddress);

            if (!setC.isEmpty()) match = true;
        }
        return match;
    }
}
