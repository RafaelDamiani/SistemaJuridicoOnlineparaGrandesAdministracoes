package mb;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.hibernate.Session;
import util.HibernateUtil;
import validator.ProsecutionUserValidator;
import validator.ProsecutionValidator;

@Named(value = "prosecutionMB")
@RequestScoped
public class ProsecutionMB {
    private final Date date = new Date();
    private Long idJudge;
    private Long idPromovente;
    private Long idPromoventeLawyer;
    private Long idPromovido;
    private Long idPromovidoLawyer;
    
    public ProsecutionMB() {
    }
    
    public Long getIdJudge() {
        return idJudge;
    }

    public void setIdJudge(Long idJudge) {
        this.idJudge = idJudge;
    }

    public Long getIdPromovente() {
        return idPromovente;
    }

    public void setIdPromovente(Long idPromovente) {
        this.idPromovente = idPromovente;
    }
    
    public Long getIdPromoventeLawyer() {
        return idPromoventeLawyer;
    }

    public void setIdPromoventeLawyer(Long idPromoventeLawyer) {
        this.idPromoventeLawyer = idPromoventeLawyer;
    }

    public Long getIdPromovido() {
        return idPromovido;
    }

    public void setIdPromovido(Long idPromovido) {
        this.idPromovido = idPromovido;
    }
    
    public Long getIdPromovidoLawyer() {
        return idPromovidoLawyer;
    }

    public void setIdPromovidoLawyer(Long idPromovidoLawyer) {
        this.idPromovidoLawyer = idPromovidoLawyer;
    }
    
    public String insertProsecution() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        boolean valid = true;
        String response = "";
        
        ProsecutionValidator prosecutionValidator = new ProsecutionValidator(valid);
        response = prosecutionValidator.validateProsecution(idJudge);
        valid = prosecutionValidator.isValid();
        
        if (!valid)
            return response;
        
        ProsecutionUserValidator prosecutionUserValidator = new ProsecutionUserValidator(valid);
        response = prosecutionUserValidator.validateProsecutionUser(idPromovente, idPromoventeLawyer , idPromovido, idPromovidoLawyer);
        valid = prosecutionUserValidator.isValid();
        
        if (!valid)
            return response;
        
        return "Processo cadastrado com sucesso!";
    }
}
