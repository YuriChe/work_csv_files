package appCSV.readers;

import appCSV.entity.YaOrder;

import java.util.List;

public class EnrollYaOrders {

    public long enrollYaOrder(List<String[]> listRows, List<YaOrder> yaOrderList) {

        for (String[] row : listRows) {
            if (row == null || row.length < 23) continue;

            YaOrder yaOrder = new YaOrder();

            if (row[0] != null && row[0].matches("[0-9]+")) {
                yaOrder.setId(Integer.parseInt(row[0]));
            } else yaOrder.setId((int) (Math.random() * 10000 + 9523131));

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

            if (row[7] != null) {
                yaOrder.setStreet(row[7]);
            } else yaOrder.setStreet("");

            if (row[8] != null) {
                yaOrder.setHouse(row[8]);
            } else yaOrder.setHouse("");

            if (row[13] != null) {
                yaOrder.setDoorcode(row[13]);
            } else yaOrder.setDoorcode("");

            if (row[14] != null && row[14].matches("[0-9]+")) {
                yaOrder.setRegion_id(Integer.parseInt(row[14]));
            } else yaOrder.setRegion_id(-1);

            if (row[15] != null) {
                yaOrder.setLatitude(Double.parseDouble(row[15]));
            } else yaOrder.setLatitude(-1);

            if (row[16] != null) {
                yaOrder.setLongitude(Double.parseDouble(row[16]));
            } else yaOrder.setLongitude(-1);

            if (row[18] != null && row[18].matches("[0-9]+")) {
                yaOrder.setSum_orders(Integer.parseInt(row[18]));
            } else yaOrder.setSum_orders(-1);

            if (row[19] != null && row[19].length() < 250) {
                yaOrder.setUser_agent(row[19]);
            } else yaOrder.setUser_agent("");

            if (row[20] != null) {
                yaOrder.setGeohash(row[20]);
            } else yaOrder.setGeohash("");

            if (row[21] != null && row[21].matches("[0-9]+")) {
                yaOrder.setSeq_pn_count(Integer.parseInt(row[21]));
            } else yaOrder.setSeq_pn_count(-1);

            if (row[22] != null && row[22].matches("[0-9]+")) {
                yaOrder.setSeq_pn_geo_count(Integer.parseInt(row[22]));
            } else yaOrder.setSeq_pn_geo_count(-1);

            yaOrderList.add(yaOrder);
        }
        return yaOrderList.size();
    }
}

