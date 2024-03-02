/*

package appCSV.readers;

import appCSV.entity.YaOrder;
import jakarta.persistence.Column;

import java.util.List;

public class EnrollYaOrders {

    public boolean EnrollYaOrder(List<String[]> listRows, List<YaOrder> yaOrderList) {

        for (String[] row : listRows) {
            if (row == null) continue;
            YaOrder yaOrder = new YaOrder();

            if (row[0] != null && row[0].matches("[0-9]+")) {
                yaOrder.setId(Integer.parseInt(row[0]));
            } else yaOrder.setId((int) (Math.random()*10000 + 9523131));

            if (row[1] != null && row[1].length() > 6 && (row[1].charAt(1) == '9' || row[1].charAt(1) == '4')) {
                yaOrder.setPhone_number(row[1]);
            } else yaOrder.setPhone_number("invalid");

            if (row[2] != null) {
                yaOrder.setName(row[2]);
            } else yaOrder.setName("");

            if (row[3] != null) {
                yaOrder.setCreated_at(row[3]);
            } else yaOrder.setCreated_at("");



            if (row[6] != null) {
                yaOrder.setCity(row[6]);
            } else yaOrder.setCity("");

            if (row[4] != null) {
                yaOrder.setStreet(row[4]);
            } else yaOrder.setStreet("");

            if (row[7] != null) {
                yaOrder.setHouse(row[7]);
            } else yaOrder.setHouse("");

            if (row[13] != null && row[13].matches("[0-9]+")) {
                yaOrder.setRegion_id(Integer.parseInt(row[13]));
            } else yaOrder.setRegion_id(0);

            if (row[15] != null) {
                yaOrder.setLatitude(Double.parseDouble(row[15]));
            } else yaOrder.setLatitude(0);

            if (row[16] != null) {
                yaOrder.setLongitude(Double.parseDouble(row[16]));
            } else yaOrder.setLongitude(0);



        }



        @Column(name = "address_city")
        private String city;
        @Column(name = "address_street")
        private String street;
        @Column(name = "address_house")
        private String house;
        @Column(name = "region_id")
        private int region_id;
        @Column(name = "latitude")
        private int latitude;
        @Column(name = "longitude")
        private int longitude;
        @Column(name = "sum_orders")
        private int sum_orders;
        @Column(name = "user_agent")
        private String user_agent;
        @Column(name = "geohash")
        private String geohash;
        @Column(name = "seq_pn_count")
        private int seq_pn_count;
        @Column(name = "seq_pn_geo_count")
        private int seq_pn_geo_count;


    }
}

*/
