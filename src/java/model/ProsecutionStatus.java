package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_prosecution_status")
public class ProsecutionStatus implements Serializable {
    private Integer id;
    private String name;
    
    public ProsecutionStatus() {
    }
    
    public ProsecutionStatus(Integer id) {
        this.id = id;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(updatable=true, name="prosecution_status_name", nullable=false, length=255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
