package appCSV.readers;

import appCSV.entity.CustomerWBGeo;

import java.util.List;

public class EnrollEntityGeo {
    /**
     * создание списка из entity CustomerWB
     */
    public long enrollToCustomersGeo(List<String[]> listRows, List<CustomerWBGeo> listCustomerWBGeo) {

        for (String[] row : listRows) {
            if (row == null || row.length < 5) continue;

            CustomerWBGeo customerWBGeo = new CustomerWBGeo();

            if (row[0] != null && row[0].matches("[0-9]+")) {
                customerWBGeo.setId_wb(Integer.valueOf(row[0]));
            } else customerWBGeo.setId_wb(0);

            if (row[1] != null && row[1].length() > 7 && (row[1].charAt(1) == '9')) {
                customerWBGeo.setPhone_number(row[1]);
            } else customerWBGeo.setPhone_number("invalid");

            if (row[2] != null) {
                customerWBGeo.setName(row[2]);
            } else customerWBGeo.setName("");

            /*if (row[3] != null && row[3].length() > 6) {
                //проверка на идентичнность с основным номером
                if (row[3].equals(row[1].substring(1)) && row[1].charAt(1) != '4') {
                    customerWBGeo.setComment("");
                    //номера не равны или основной на 4
                } else if (row[3].charAt(0) == '9') {
                    //если 4 и запасной 9 - меняем местами
                    if (row[1].charAt(1) == '4') {
                        customerWBGeo.setPhone_number(row[3]);
                        customerWBGeo.setComment(row[1]);
                    } else customerWBGeo.setComment(row[3]);
                    //если запасной не 9
                } else if (customerWBGeo.getPhone_number().equals("invalid") || row[1].charAt(1) == '4') {
                    //номера без мобильного не нужно
                    continue;
                }
            } else if (customerWBGeo.getPhone_number().equals("invalid") || row[1].charAt(1) == '4') {
                        continue;
                    } else customerWBGeo.setComment("");
*/
            if (row[4] != null) {
                customerWBGeo.setEmail(row[4]);
            } else customerWBGeo.setEmail("");

            if (row[3] != null && row[3].length() > 6) {
                if (customerWBGeo.getPhone_number().equals("invalid")) {
                    if (row[3].charAt(0) == '9') {
                        customerWBGeo.setPhone_number("7" + row[3]);
                        customerWBGeo.setComment("");
                    } else continue;
                } else if (row[3].equals(row[1].substring(1))) {
                    customerWBGeo.setComment("");
                } else customerWBGeo.setComment(row[3]);

            } else customerWBGeo.setComment("");

            if (customerWBGeo.getPhone_number().equals("invalid")) continue;

            try {
                if (row[5] != null) {
                    double number = Double.parseDouble(row[5]);
                    if (!Double.isInfinite(number) && !Double.isNaN(number)) {
                        // Действия, если значение является числом типа double
                        customerWBGeo.setWb_lat(number);
                    } else {
                        // Действия, если значение не является числом типа double
                        customerWBGeo.setWb_lat(0);
                    }
                } else customerWBGeo.setWb_lat(0);
                // Теперь 'number' содержит значение типа double
                // Можно выполнить дополнительные проверки или операции с 'number'
                // Например, проверить, что число не является бесконечностью или NaN

            } catch (NumberFormatException e) {
                // Действия, если значение не может быть преобразовано в double
                customerWBGeo.setWb_lat(0);
            }

            try {
                if (row[6] != null) {
                    double number = Double.parseDouble(row[6]);
                    if (!Double.isInfinite(number) && !Double.isNaN(number)) {
                        customerWBGeo.setWb_lon(number);
                    } else {
                        customerWBGeo.setWb_lon(0);
                    }
                } else customerWBGeo.setWb_lon(0);
            } catch (NumberFormatException e) {
                customerWBGeo.setWb_lon(0);
            }

            String address;
            if (row[7] != null) {
                int homeStr = row[7].toUpperCase().indexOf(" КВ.");
                if (homeStr == -1) {
                    homeStr = row[7].toUpperCase().indexOf(" КВ");
                }
                if (homeStr != -1) {
                    address = row[7].substring(0, homeStr + 4);
                } else address = row[7];
            } else address = "";
//            address = row[7];
            customerWBGeo.setAddress(address);

            if (row[8] != null) {
                customerWBGeo.setGeohash(row[8]);
            } else customerWBGeo.setGeohash("");


            if (row[10] != null && row[10].matches("[0-9]+")) {
                customerWBGeo.setWb_seq_pn_count(Integer.valueOf(row[10]));
            } else customerWBGeo.setWb_seq_pn_count(0);

            if (row[11] != null && row[11].matches("[0-9]+")) {
                customerWBGeo.setWb_seq_pn_geo_count(Integer.valueOf(row[11]));
            } else customerWBGeo.setWb_seq_pn_geo_count(0);

            listCustomerWBGeo.add(customerWBGeo);//добавление элемента к списку
        }
        return listCustomerWBGeo.size();
    }
}