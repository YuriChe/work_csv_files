package appCSV.readers;

import appCSV.Entity.Customer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV_Entity extends ReadCSV_ByLine{

    public List<Customer> readerToClass(String file) throws IOException {
        List<String[]> list = reader(file);
        List<Customer> listCustomer = new ArrayList<>();

        for (String[] array : list) {
            Customer customer = new Customer();
            customer.setId(Integer.valueOf(array[0]));
            customer.setPhone_number(array[1]);
            customer.setName(array[2]);
            customer.setComment(array[3]);
            customer.setEmail(array[4]);
            customer.setWb_lat(array[5]);
            customer.setWb_lon(array[6]);
            customer.setAddress(array[7]);
            customer.setGeohash(array[8]);

            listCustomer.add(customer);
        }

        return null;
    }
}
