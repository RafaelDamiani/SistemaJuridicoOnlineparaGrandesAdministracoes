package mb;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.hibernate.Session;
import util.HibernateUtil;
import validator.PhaseValidator;

@Named(value = "phaseMB")
@RequestScoped
public class PhaseMB {
    private final Date date = new Date();
    private String title;
    private String description;
    private String justification;
    private Long idProsecution;
    private Long idlawyer;
    private Integer idPhaseType;
    private Integer idPhaseStatus;
    
    public PhaseMB() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Long geIdProsecution() {
        return idProsecution;
    }

    public void setIdProsecution(Long idProsecution) {
        this.idProsecution = idProsecution;
    }
    
    public Long getIdlawyer() {
        return idlawyer;
    }

    public void setIdlawyer(Long idlawyer) {
        this.idlawyer = idlawyer;
    }

    public Integer getIdPhaseType() {
        return idPhaseType;
    }

    public void setIdPhaseType(Integer idPhaseType) {
        this.idPhaseType = idPhaseType;
    }

    public Integer getIdPhaseStatus() {
        return idPhaseStatus;
    }

    public void setIdPhaseStatus(Integer idPhaseStatus) {
        this.idPhaseStatus = idPhaseStatus;
    }
    
    public String insertPhase() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        boolean valid = true;
        String response = "";
        
        PhaseValidator phaseValidator = new PhaseValidator(valid);
        response = phaseValidator.validatePhase(idProsecution, idlawyer, title, description, justification, idPhaseType, idPhaseStatus);
        valid = phaseValidator.isValid();
        
        if (!valid)
            return response;
        
        /*User partPromovente = new User(idPromovente);
        User promoventeLawyer = new User(idPromoventeLawyer);
        User partPromovido = new User(idPromovido);
        User promovidoLawyer = new User(idPromovidoLawyer);
        
        ProsecutionStatus prosecutionStatus = new ProsecutionStatus(1);
        PartType partPromoventeType = new PartType(1);
        PartType partPromovidoType = new PartType(2);
        
        ProsecutionUser prosecutionUserPromovente = new ProsecutionUser(prosecution, partPromovente, promoventeLawyer, prosecutionStatus, partPromoventeType);
        ProsecutionUser prosecutionUserPromovido = new ProsecutionUser(prosecution, partPromovido, promovidoLawyer, prosecutionStatus, partPromovidoType);
        
        session.save(prosecutionUserPromovente);
        session.save(prosecutionUserPromovido);
        */
        session.getTransaction().commit();
        session.close();
        
        return "Fase cadastrada com sucesso!";
    }
}
