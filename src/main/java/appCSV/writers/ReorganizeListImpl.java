package appCSV.writers;

import appCSV.entity.CustomerWB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ReorganizeListImpl<T> implements ReorganizeList<T> {
    @Override
    public List<T> toUniqueList(List<T> list) {
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String[]> toArrStringForFile(List<T> list) {

        if (list == null && !list.stream().allMatch(e -> e instanceof CustomerWB)) {

            return null;
        }

        List<String[]> listResult = new ArrayList<>();
        String[] firstRow = new String[7];
        firstRow[0] = "id_wb";
        firstRow[1] = "phone_number";
        firstRow[2] = "phone_number2";
        firstRow[3] = "name";
        firstRow[4] = "address";
        firstRow[5] = "email";
        firstRow[6] = "geo_hash";
        listResult.add(firstRow);

        for (T el : list) {
            CustomerWB entity = (CustomerWB) el;
            String[] strArr = new String[7];
            strArr[0] = String.valueOf(entity.getId_wb());
            strArr[1] = entity.getPhone_number();
            strArr[2] = entity.getComment();
            strArr[3] = entity.getName();
            strArr[4] = entity.getAddress();
            strArr[5] = entity.getEmail();
            strArr[6] = entity.getGeohash();
            listResult.add(strArr);
        }
        return listResult;
    }

    public static List<String> trimSplitStreets(String originalStreets) {
        return new ArrayList<>(List.of(originalStreets.split(",")));
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
