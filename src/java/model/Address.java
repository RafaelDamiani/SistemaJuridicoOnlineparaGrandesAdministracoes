package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;


@Entity
@Table(name = "tb_address")
@SequenceGenerator(name = "seq_add", sequenceName = "seq_add_id")
public class Address {
    private Long id;
    private String zipCode;
    private String street;
    private Integer number;
    private String city;
    private String state;
    private User user;
    
    public Address() {
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_add")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(updatable=true, name="add_zipCode", nullable=false, length=10)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    @Column(updatable=true, name="add_street", nullable=false, length=255)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    @Column(updatable=true, name="add_number", nullable=false, length=8)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    
    @Column(updatable=true, name="add_city", nullable=false, length=29)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    @Column(updatable=true, name="add_state", nullable=false, length=2)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    @OneToOne(mappedBy = "address")
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
