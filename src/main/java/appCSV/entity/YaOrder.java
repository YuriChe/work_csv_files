package appCSV.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ya_customers", schema = "wb")
public class YaOrder {

    @Id
    @Column(name = "yandex_id")
    private int id;
    @Column(name = "phone_number")
    private String phone_number;
    @Column(name = "name")
    private String name;
    @Column(name = "created_at")
    private String created_at;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "house")
    private String house;

    public String getDoorcode() {
        return doorcode;
    }

    public void setDoorcode(String doorcode) {
        this.doorcode = doorcode;
    }

    @Column(name = "doorcode")
    private String doorcode;
    @Column(name = "region_id")
    private int region_id;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "longitude")
    private double longitude;
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

    public YaOrder() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getSum_orders() {
        return sum_orders;
    }

    public void setSum_orders(int sum_orders) {
        this.sum_orders = sum_orders;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public int getSeq_pn_count() {
        return seq_pn_count;
    }

    public void setSeq_pn_count(int seq_pn_count) {
        this.seq_pn_count = seq_pn_count;
    }

    public int getSeq_pn_geo_count() {
        return seq_pn_geo_count;
    }

    public void setSeq_pn_geo_count(int seq_pn_geo_count) {
        this.seq_pn_geo_count = seq_pn_geo_count;
    }
}
