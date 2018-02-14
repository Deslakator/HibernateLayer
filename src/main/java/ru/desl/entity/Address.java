package ru.desl.entity;

import javax.persistence.*;

@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(
                name = "get_all_address",
                query = "select a from Address a"
        )
        ,
        @NamedQuery(
                name = "get_address_by_id",
                query = "select a from Address a where id = :id"
        )
})
@Entity
@Table(name = "empl_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(length = 20)
    private String postcode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (id != address.id) return false;
        if (!city.equals(address.city)) return false;
        return postcode != null ? postcode.equals(address.postcode) : address.postcode == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + city.hashCode();
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
