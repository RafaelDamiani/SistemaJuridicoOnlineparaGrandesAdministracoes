package model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
@Table(name = "tb_address")
public class Address implements Serializable {
    private Long id;
    private String zipCode;
    private String street;
    private Short number;
    private String city;
    private String state;
    private User user;
    
    public Address() {
    }
    
    public Address(String zipCode, String street, Short number, String city, String state, User user) {
        this.zipCode = zipCode;
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.user = user;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(updatable=true, name="address_zip_code", nullable=false, length=8)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    @Column(updatable=true, name="address_street", nullable=false, length=255)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    @Column(updatable=true, name="address_number", nullable=false, length=8)
    public Short getNumber() {
        return number;
    }

    public void setNumber(Short number) {
        this.number = number;
    }
    
    @Column(updatable=true, name="address_city", nullable=false, length=29)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    @Column(updatable=true, name="address_state", nullable=false, length=2)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id", updatable=true)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
