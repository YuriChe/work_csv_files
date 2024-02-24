package appCSV.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "wb_customers_geo", schema = "wb")
public class CustomerWBGeo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "wb_id")
    private int id_wb;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "comment")
    private String comment;

    @Column(name = "email")
    private String email;

    @Column(name = "wb_lat")
    private double wb_lat;

    @Column(name = "wb_lon")
    private double wb_lon;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "geohash")
    private String geohash;

    /*@Column (name = "old_id")
    @Deprecated
    private String old_id;*/

    public int getWb_seq_pn_count() {
        return wb_seq_pn_count;
    }

    public void setWb_seq_pn_count(int wb_seq_pn_count) {
        this.wb_seq_pn_count = wb_seq_pn_count;
    }

    public int getWb_seq_pn_geo_count() {
        return wb_seq_pn_geo_count;
    }

    public void setWb_seq_pn_geo_count(int wb_seq_pn_geo_count) {
        this.wb_seq_pn_geo_count = wb_seq_pn_geo_count;
    }

    @Column (name = "wb_seq_pn_count")
    private int wb_seq_pn_count;

    @Column (name = "wb_seq_pn_geo_count")
    private int wb_seq_pn_geo_count;

    public CustomerWBGeo() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id_wb=" + id_wb +
                ", phone_number='" + phone_number + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", address='" + address + '\'' +
                ", wb_lat='" + wb_lat + '\'' +
                ", wb_lon='" + wb_lon + '\'' +
                ", email='" + email + '\'' +
                ", geohash='" + geohash + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_wb() {
        return id_wb;
    }

    public void setId_wb(int id_wb) {
        this.id_wb = id_wb;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getWb_lat() {
        return wb_lat;
    }

    public void setWb_lat(double wb_lat) {
        this.wb_lat = wb_lat;
    }

    public double getWb_lon() {
        return wb_lon;
    }

    public void setWb_lon(double wb_lon) {
        this.wb_lon = wb_lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerWBGeo that = (CustomerWBGeo) o;
        return Objects.equals(getPhone_number(), that.getPhone_number()) && Objects.equals(getName(), that.getName()) && Objects.equals(getGeohash(), that.getGeohash());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhone_number(), getName(), getGeohash());
    }
}
