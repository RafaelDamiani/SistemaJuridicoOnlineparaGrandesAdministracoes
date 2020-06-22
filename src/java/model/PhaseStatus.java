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
@Table(name = "tb_phase_status")
@SequenceGenerator(name = "seq_phase_status", sequenceName = "seq_phase_status_id")
public class PhaseStatus implements Serializable {
    private Integer id;
    private String name;
    private List<Phase> phases;
    
    public PhaseStatus() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_phase_status")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(updatable=true, name="phase_name", nullable=false, length=255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @OneToMany(mappedBy="phaseStatus", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public List<Phase> getPhases() {
        return this.phases;
    }
    
    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }
}
