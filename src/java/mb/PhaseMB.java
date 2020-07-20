package mb;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
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
@SessionScoped
public class PhaseMB implements Serializable {

    @Inject
    private ProsecutionMB prosecutionMB;
    @Inject
    private LoginMB loginMB;

    private boolean success;
    private boolean error;
    private String responseMessage;

    private final Date date = new Date();
    private String title;
    private String description;
    private String justification;
    private Long idProsecution;
    private Long idlawyer;
    private Integer idPhaseType;
    private Integer idPhaseStatus;
    private Phase phase;

    public PhaseMB() {
    }

    @PostConstruct
    public void init() {
        setSuccess(false);
        setError(false);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
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

        setError(false);
        setSuccess(false);

        setIdProsecution(prosecutionMB.getIdProsecution());
        setIdlawyer(loginMB.getIdUser());

        PhaseValidator phaseValidator = new PhaseValidator(valid, true);
        response = phaseValidator.validatePhase(idProsecution, idlawyer, title, description, null, idPhaseType, null);
        valid = phaseValidator.isValid();

        if (!valid) {
            setError(true);
            setResponseMessage(response);
            return response;
        }

        Prosecution prosecution = new Prosecution(idProsecution);
        User lawyer = new User(idlawyer);
        PhaseType phaseType = new PhaseType(idPhaseType);

        Phase phase = new Phase(date, prosecution, lawyer, title, description, phaseType);

        session.save(phase);

        session.getTransaction().commit();
        session.close();

        return response;
    }

    public String updatePhase() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        boolean valid = true;
        String response = "";

        setError(false);
        setSuccess(false);

        PhaseValidator phaseValidator = new PhaseValidator(valid, false);
        response = phaseValidator.validatePhase(idProsecution, idlawyer, title, description, justification, idPhaseType, idPhaseStatus);
        valid = phaseValidator.isValid();

        if (!valid) {
            setError(true);
            setResponseMessage(response);
            return response;
        }

        Prosecution prosecution = new Prosecution(idProsecution);
        User lawyer = new User(idlawyer);
        PhaseType phaseType = new PhaseType(idPhaseType);
        PhaseStatus phaseStatus = new PhaseStatus(idPhaseStatus);

        Phase phase = new Phase(date, prosecution, lawyer, title, description, justification, phaseType, phaseStatus);

        session.update(phase);

        session.getTransaction().commit();
        session.close();

        return response;
    }

    public List<Phase> indexPhase() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String hql
                = "select \n"
                + "	phs.id as idPhase,\n"
                + "	phs.phase_date,\n"
                + "	TO_CHAR(phs.phase_date, 'dd/mm/yyyy hh:mi') as formattedDate,\n"
                + "	phs.phase_title,\n"
                + "	phs.phase_description,\n"
                + "	phs_tpe.phase_type_name as type,\n"
                + "	phs_sts.phase_status_name as status,\n"
                + "	phs.phase_justification,\n"
                + "	lwr.user_name as lawyer\n"
                + "from tb_phase phs\n"
                + "inner join tb_phase_type phs_tpe\n"
                + "	on phs_tpe.id = phs.phase_type_id\n"
                + "left join tb_phase_status phs_sts\n"
                + "	on phs_sts.id = phs.phase_status_id\n"
                + "inner join tb_user lwr\n"
                + "	on lwr.id= phs.lawyer_id\n"
                + "where phs.prosecution_id = :idProsecution\n"
                + "order by phs.id";

        Long idProsecution = prosecutionMB.getIdProsecution();

        Query query = session.createSQLQuery(hql);
        query.setParameter("idProsecution", idProsecution);

        List<Phase> phases = query.list();

        session.getTransaction().commit();
        session.close();

        return phases;
    }

    public String editPhase(Long idPhase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();        
        
        String hql = "select * from tb_phase where id = :idPhase";
        
        Query query = session.createSQLQuery(hql).addEntity(Phase.class);
        query.setParameter("idPhase", idPhase);
        
        Phase phase = (Phase)query.uniqueResult();
        
        setTitle(phase.getTitle());
        setIdPhaseType(phase.getPhaseType().getId());
        setDescription(phase.getDescription());
        
        session.getTransaction().commit();
        session.close();

        return "/CadastroFase.xhtml";
    }

    public String Deslogar() throws NoSuchAlgorithmException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("#{PhaseMB}");

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        session.invalidate();

        return "./index.xhtml";
    }

}
