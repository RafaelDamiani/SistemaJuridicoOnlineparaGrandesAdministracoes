package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_phase_type")
@SequenceGenerator(name = "seq_phase_type", sequenceName = "seq_phase_type_id")
public class PhaseType implements Serializable {
    private Integer id;
    private String name;
    
    public PhaseType() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_phase_type")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(updatable=true, name="phase_type_name", nullable=false, length=255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
