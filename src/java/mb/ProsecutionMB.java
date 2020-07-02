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
    private String promovente;
    private String promovido;
    
    public ProsecutionMB() {
    }
    
    public Long getIdJudge() {
        return idJudge;
    }

    public void setIdJudge(Long idJudge) {
        this.idJudge = idJudge;
    }

    public String getPromovente() {
        return promovente;
    }

    public void setPromovente(String promovente) {
        this.promovente = promovente;
    }

    public String getPromovido() {
        return promovido;
    }

    public void setPromovido(String promovido) {
        this.promovido = promovido;
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
        response = prosecutionUserValidator.validateProsecutionUser(promovente, promovido);
        valid = prosecutionUserValidator.isValid();
        
        if (!valid)
            return response;
        
        return "Processo cadastrado com sucesso!";
    }
}
