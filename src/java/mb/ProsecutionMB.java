package mb;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import model.Judge;
import model.PartType;
import model.Prosecution;
import model.ProsecutionStatus;
import model.ProsecutionUser;
import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;
import validator.ProsecutionUserValidator;
import validator.ProsecutionValidator;

@Named(value = "prosecutionMB")
@RequestScoped
public class ProsecutionMB {
    private final Date date = new Date();
    private Long idPromovente;
    private Long idPromoventeLawyer;
    private Long idPromovido;
    private Long idPromovidoLawyer;
    
    public ProsecutionMB() {
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
        
        String hql = "select us.id, count(prs.id) as qtdPros from tb_user us left join tb_prosecution prs on prs.judge_id = us.id where us.user_type_id = 3 group by us.id LIMIT 1";

        Query query = session.createSQLQuery(hql).addEntity(Judge.class);
        
        Judge judge = (Judge)query.uniqueResult();
        
        long idJudge = judge.getId();
        
        ProsecutionValidator prosecutionValidator = new ProsecutionValidator(valid);
        response = prosecutionValidator.validateProsecution(idJudge);
        valid = prosecutionValidator.isValid();
        
        if (!valid)
            return response;
        
        User newJudge = new User(idJudge);
        
        Prosecution prosecution = new Prosecution(date,newJudge);
        
        session.save(prosecution);
        
        ProsecutionUserValidator prosecutionUserValidator = new ProsecutionUserValidator(valid);
        response = prosecutionUserValidator.validateProsecutionUser(idPromovente, idPromoventeLawyer , idPromovido, idPromovidoLawyer);
        valid = prosecutionUserValidator.isValid();
        
        if (!valid)
            return response;
        
        User partPromovente = new User(idPromovente);
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
        
        session.getTransaction().commit();
        session.close();
        
        return "Processo cadastrado com sucesso!";
    }
}
