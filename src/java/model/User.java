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
import javax.persistence.SequenceGenerator;

@Entity
@Table(name = "tb_user")
@SequenceGenerator(name = "seq_usr", sequenceName = "seq_usr_id")
public class User implements Serializable {
    private Long id;
    private String name;
    private String cpf;
    private Address address;

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usr")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(updatable=true, name="usr_name", nullable=false, length=255)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Column(updatable=true, name="usr_cpf", nullable=false, length=14)
    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="add_id", updatable=true)
    public Address getAddress() {
        return this.address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
}
