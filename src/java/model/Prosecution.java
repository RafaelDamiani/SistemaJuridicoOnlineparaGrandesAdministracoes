package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_prosecution")
public class Prosecution implements Serializable {
    private Long id;
    private Date date;
    private User judge;
    
    public Prosecution() {
    }
    
    public Prosecution(Long id) {
        this.id = id;
    }
    
    public Prosecution(Date date, User judge) {
        this.date = date;
        this.judge = judge;
    }
    
    public Prosecution(Long id, Date date, User judge) {
        this.id = id;
        this.date = date;
        this.judge = judge;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(updatable=true, name="prosecution_date", nullable=false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    @ManyToOne
    @JoinColumn(name="judge_id")
    public User getJudge() {
        return this.judge;
    }
    
    public void setJudge(User judge) {
        this.judge = judge;
    }
}
