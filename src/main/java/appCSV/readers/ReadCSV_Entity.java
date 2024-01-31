package appCSV.readers;

import appCSV.entity.CustomerWB;

import java.util.ArrayList;
import java.util.List;

public class ReadCSV_Entity extends ReadCSV_ByLine {

    public List<CustomerWB> readerToCustomers(String file) {
        List<String[]> list = reader(file);
        List<CustomerWB> listCustomerWBS = new ArrayList<>();

        for (String[] array : list) {
            if (array == null || array[0] == null) continue;
            CustomerWB customerWB = new CustomerWB();

            if (array[0].matches("[0-9]+")) {
                customerWB.setId_wb(Integer.valueOf(array[0]));
            } else customerWB.setId_wb((int) (Math.random() * 10000));

            customerWB.setPhone_number(array[1]);
            customerWB.setName(array[2]);
            if (array[3].equals(array[1].substring(1))) {
                customerWB.setComment("");
            } else {
                customerWB.setComment(array[3]);
            }
            customerWB.setEmail(array[4]);
            customerWB.setWb_lat(array[5]);
            customerWB.setWb_lon(array[6]);
            String address = new String();
            int homeStr = array[7].toUpperCase().indexOf("ДОМ");
            if (homeStr == -1) {
                homeStr = array[7].toUpperCase().indexOf("Д.");
            }
            if (homeStr != -1) {
                address = array[7].substring(0, homeStr);
            }
            customerWB.setAddress(address);
            customerWB.setGeohash(array[8]);

            listCustomerWBS.add(customerWB);
        }
        return listCustomerWBS;
    }
}
