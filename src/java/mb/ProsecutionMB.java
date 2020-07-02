package mb;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.hibernate.Session;
import util.HibernateUtil;
import validator.ProsecutionValidator;

@Named(value = "prosecutionMB")
@RequestScoped
public class ProsecutionMB {
    private final Date date = new Date();
    private Long idJudge;

    public Long getIdJudge() {
        return idJudge;
    }

    public void setIdJudge(Long idJudge) {
        this.idJudge = idJudge;
    }
    
    public ProsecutionMB() {
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
        
        return "Processo cadastrado com sucesso!";
    }
}
