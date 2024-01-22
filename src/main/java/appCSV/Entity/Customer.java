package appCSV.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table (name = "wb_customers")
public class Customer {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Integer id;

    @Column (name = "phone_number", nullable = false)
    private String phone_number;

    @Column (name = "wb_name", nullable = false)
    private String name;

    @Column (name = "wb_comment")
    private String comment;

    @Column (name = "wb_email")
    private String email;

    @Column (name = "wb_lat")
    private String wb_lat;

    @Column (name = "wb_lon")
    private String wb_lon;

    @Column (name = "wb_address", nullable = false)
    private String address;

    @Column (name = "wb_geohash")
    private String geohash;

    @Column (name = "old_id")
    @Deprecated
    private String old_id;

    @Column (name = "wb_seq_pn_count")
    @Deprecated
    private String wb_seq_pn_count;

    @Column (name = "wb_seq_pn_geo_count")
    @Deprecated
    private String wb_seq_pn_geo_count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getWb_lat() {
        return wb_lat;
    }

    public void setWb_lat(String wb_lat) {
        this.wb_lat = wb_lat;
    }

    public String getWb_lon() {
        return wb_lon;
    }

    public void setWb_lon(String wb_lon) {
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
        Customer customer = (Customer) o;
        return Objects.equals(getPhone_number(), customer.getPhone_number()) && Objects.equals(getName(), customer.getName()) && Objects.equals(getEmail(), customer.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhone_number(), getName(), getEmail());
    }
}
