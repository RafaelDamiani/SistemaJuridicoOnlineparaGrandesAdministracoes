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
@Table(name = "tb_prosecution")
@SequenceGenerator(name = "seq_prosecution", sequenceName = "seq_prosecution_id")
public class Prosecution implements Serializable {
    private Long id;
    private Date date;
    private User judge;
    
    public Prosecution() {
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prosecution")
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
