package appCSV.writers;

import java.util.List;
import java.util.stream.Collectors;

public class ReorganizeListImpl<T> implements ReorganizeList<T> {
    @Override
    public List<T> toUniqueList(List<T> list) {
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    // Проверяем, что каждый символ строки является цифрой
    public boolean isNumeric(String str) {

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
