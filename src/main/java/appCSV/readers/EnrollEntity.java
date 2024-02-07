package appCSV.readers;

import appCSV.entity.CustomerWB;
import com.opencsv.exceptions.CsvException;

import java.util.List;

public class EnrollEntity {

    public long enrollToCustomers(List<String[]> listRows, List<CustomerWB> listCustomerWB) {

    /**
     *  создание списка из entity CustomerWB
     */
        for (String[] row : listRows) {
            if (row == null || row[0] == null) continue;

            CustomerWB customerWB = new CustomerWB();

            if (row[0].matches("[0-9]+")) {
                customerWB.setId_wb(Integer.valueOf(row[0]));
            } else customerWB.setId_wb((int) (Math.random() * 10000));

            customerWB.setPhone_number(row[1]);

            customerWB.setName(row[2]);
            if (row[1] != null && row[1].length() > 2) {
                if (row[3].equals(row[1].substring(1))) {
                    customerWB.setComment("");
                } else {
                    customerWB.setComment(row[3]);
                }
            } else customerWB.setComment("");

            customerWB.setEmail(row[4]);
            customerWB.setWb_lat(row[5]);
            customerWB.setWb_lon(row[6]);

            String address = new String();

            if (row[7] != null) {
                int homeStr = row[7].toUpperCase().indexOf("ДОМ");
                if (homeStr == -1) {
                    homeStr = row[7].toUpperCase().indexOf("Д.");
                }
                if (homeStr != -1) {
                    address = row[7].substring(0, homeStr);
                }
            } else address = "";
            customerWB.setAddress(address);

            customerWB.setGeohash(row[8]);

            listCustomerWB.add(customerWB);//добавление элемента к списку
        }
        return listCustomerWB.size();
    }
}
