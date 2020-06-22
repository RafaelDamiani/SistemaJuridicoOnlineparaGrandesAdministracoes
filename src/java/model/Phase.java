package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_phase")
@SequenceGenerator(name = "seq_phase", sequenceName = "seq_phase_id")
public class Phase implements Serializable {
    private Long id;
    private Prosecution prosecution;
    private PhaseType phaseType;
    private PhaseStatus phaseStatus;
    
    public Phase(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_phase")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @ManyToOne
    @JoinColumn(name="prosecution_id")
    public Prosecution getProsecution() {
        return this.prosecution;
    }
    
    public void setProsecution(Prosecution prosecution) {
        this.prosecution = prosecution;
    }
    
    @ManyToOne
    @JoinColumn(name="phase_type_id")
    public PhaseType getPhaseType() {
        return this.phaseType;
    }
    
    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }
    
    @ManyToOne
    @JoinColumn(name="phase_status_id")
    public PhaseStatus getPhaseStatus() {
        return this.phaseStatus;
    }
    
    public void setPhaseStatus(PhaseStatus phaseStatus) {
        this.phaseStatus = phaseStatus;
    }
}
