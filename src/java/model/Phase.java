package model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
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
    private Date date;
    private String title;
    private String description;
    private Prosecution prosecution;
    private PhaseType phaseType;
    private PhaseStatus phaseStatus;
    // private String file; <-- TODO pdf file
    //private List<Document> document;  <-- TODO
    
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
    
    @Column(updatable=true, name="phase_date", nullable=false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(updatable=true, name="phase_title", nullable=false, length=255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(updatable=true, name="phase_description", nullable=false, length=5000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
