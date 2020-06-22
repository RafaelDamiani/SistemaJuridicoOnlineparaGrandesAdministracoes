package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_prosecution_status")
@SequenceGenerator(name = "seq_prosecution_status", sequenceName = "seq_prosecution_status_id")
public class ProsecutionStatus implements Serializable {
    private Integer id;
    private String name;
    private List<ProsecutionUser> prosecutionUsers;
    
    public ProsecutionStatus() {
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prosecution_status")
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
    
    @OneToMany(mappedBy="prosecutionStatus", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public List<ProsecutionUser> getProsecutionUsers() {
        return this.prosecutionUsers;
    }
    
    public void setProsecutionUsers(List<ProsecutionUser> prosecutionUsers) {
        this.prosecutionUsers = prosecutionUsers;
    }
}
