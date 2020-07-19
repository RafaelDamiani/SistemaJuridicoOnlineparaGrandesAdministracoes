package mb;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Phase;
import model.PhaseStatus;
import model.PhaseType;
import model.Prosecution;
import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;
import validator.PhaseValidator;

@Named(value = "phaseMB")
@RequestScoped
public class PhaseMB {
    @Inject
    private ProsecutionMB prosecutionMB;
    
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
        
        Prosecution prosecution = new Prosecution(idProsecution);
        User lawyer = new User(idlawyer);
        PhaseType phaseType = new PhaseType(idPhaseType);
        PhaseStatus phaseStatus = new PhaseStatus(idPhaseStatus);
        
        Phase phase = new Phase(date, prosecution, lawyer, title, description, justification, phaseType, phaseStatus);
        
        session.save(phase);

        session.getTransaction().commit();
        session.close();
        
        return response;
    }
    
    public List<Phase> indexPhase() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql = 
                "select \n" +
                "	phs.id as idPhase,\n" +
                "	phs.phase_date,\n" +
                "	TO_CHAR(phs.phase_date, 'dd/mm/yyyy hh:mi') as formattedDate,\n" +
                "	phs.phase_title,\n" +
                "	phs.phase_description,\n" +
                "	phs_tpe.phase_type_name as type,\n" +
                "	phs_sts.phase_status_name as status,\n" +
                "	phs.phase_justification,\n" +
                "	lwr.user_name as lawyer\n" +
                "from tb_phase phs\n" +
                "inner join tb_phase_type phs_tpe\n" +
                "	on phs_tpe.id = phs.phase_type_id\n" +
                "inner join tb_phase_status phs_sts\n" +
                "	on phs_sts.id = phs.phase_status_id\n" +
                "inner join tb_user lwr\n" +
                "	on lwr.id= phs.lawyer_id\n" +
                "where phs.prosecution_id = :idProsecution\n" +
                "order by phs.id";
        
        Long idProsecution = prosecutionMB.getProsecutionDTO().getIdProsecution();
        
        Query query = session.createSQLQuery(hql);
        query.setParameter("idProsecution", idProsecution);
        
        List<Phase> phases = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        return phases;
    }
}
