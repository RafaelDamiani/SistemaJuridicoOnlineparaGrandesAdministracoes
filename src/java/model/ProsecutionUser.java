package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_prosecution_user")
public class ProsecutionUser {
    private Long id;
    private Prosecution prosecution;
    private User part;
    private User lawyer;
    private ProsecutionStatus prosecutionStatus;
    private PartType partType;
    
    public ProsecutionUser() {
    }
    
    public ProsecutionUser(Prosecution prosecution, User part, User lawyer, ProsecutionStatus prosecutionStatus, PartType partType) {
        this.prosecution = prosecution;
        this.part = part;
        this.lawyer = lawyer;
        this.prosecutionStatus = prosecutionStatus;
        this.partType = partType;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
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
    @JoinColumn(name="part_id")
    public User getPart() {
        return this.part;
    }
    
    public void setPart(User part) {
        this.part = part;
    }
    
    @ManyToOne
    @JoinColumn(name="lawyer_id")
    public User getLawyer() {
        return this.lawyer;
    }
    
    public void setLawyer(User lawyer) {
        this.lawyer = lawyer;
    }
    
    @ManyToOne
    @JoinColumn(name="prosecution_status_id")
    public ProsecutionStatus getProsecutionStatus() {
        return this.prosecutionStatus;
    }
    
    public void setProsecutionStatus(ProsecutionStatus prosecutionStatus) {
        this.prosecutionStatus = prosecutionStatus;
    }
    
    @ManyToOne
    @JoinColumn(name="part_type_id")
    public PartType getPartType() {
        return this.partType;
    }
    
    public void setPartType(PartType partType) {
        this.partType = partType;
    }
}
