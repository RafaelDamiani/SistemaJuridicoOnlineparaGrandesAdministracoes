package model;

import java.util.Date;

public class ProsecutionDTO {
    private Long idProsecution;
    private Date date;
    private String formattedDate;
    private Long idJudge;
    private String judge;
    private String status;
    private String part;
    private String category;
    
    private Long idPromovente;
    private String promovente;
    private Long idPromovido;
    private String promovido;
    private Long idPromoventeLawyer;
    private String promoventeLawyer;
    private Long idPromovidoLawyer;
    private String promovidoLawyer;
    
    public ProsecutionDTO() {
    }
    
    public ProsecutionDTO(Long idProsecution, Date date, String formattedDate, Long idJudge, String judge, String status, String part, String category) {
        this.idProsecution = idProsecution;
        this.date = date;
        this.formattedDate = formattedDate;
        this.idJudge = idJudge;
        this.judge = judge;
        this.status = status;
        this.part = part;
        this.category = category;
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

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
    
    public Long getIdJudge() {
        return idJudge;
    }

    public void setIdJudge(Long idJudge) {
        this.idJudge = idJudge;
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

    public Long getIdPromovente() {
        return idPromovente;
    }

    public void setIdPromovente(Long idPromovente) {
        this.idPromovente = idPromovente;
    }

    public String getPromovente() {
        return promovente;
    }

    public void setPromovente(String promovente) {
        this.promovente = promovente;
    }

    public Long getIdPromovido() {
        return idPromovido;
    }

    public void setIdPromovido(Long idPromovido) {
        this.idPromovido = idPromovido;
    }

    public String getPromovido() {
        return promovido;
    }

    public void setPromovido(String promovido) {
        this.promovido = promovido;
    }

    public Long getIdPromoventeLawyer() {
        return idPromoventeLawyer;
    }

    public void setIdPromoventeLawyer(Long idPromoventeLawyer) {
        this.idPromoventeLawyer = idPromoventeLawyer;
    }

    public String getPromoventeLawyer() {
        return promoventeLawyer;
    }

    public void setPromoventeLawyer(String promoventeLawyer) {
        this.promoventeLawyer = promoventeLawyer;
    }

    public Long getIdPromovidoLawyer() {
        return idPromovidoLawyer;
    }

    public void setIdPromovidoLawyer(Long idPromovidoLawyer) {
        this.idPromovidoLawyer = idPromovidoLawyer;
    }

    public String getPromovidoLawyer() {
        return promovidoLawyer;
    }

    public void setPromovidoLawyer(String promovidoLawyer) {
        this.promovidoLawyer = promovidoLawyer;
    }
    
    
}
