package model;

import java.util.Date;

public class ProsecutionTable {
    private Long idProsecution;
    private Date date;
    private String judge;
    private String status;
    private String part;
    private String category;
    
    public ProsecutionTable() {
    }
    
    public Long getIdProsecution() {
        return idProsecution;
    }

    public void setId(Long idProsecution) {
        this.idProsecution = idProsecution;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getStatus() {
        return status;
    }

    public void setProsecutionStatus(String status) {
        this.status = status;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
