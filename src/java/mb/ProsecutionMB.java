package mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Judge;
import model.PartType;
import model.Phase;
import model.Prosecution;
import model.ProsecutionStatus;
import model.ProsecutionDTO;
import model.ProsecutionUser;
import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;
import validator.ProsecutionUserValidator;
import validator.ProsecutionValidator;

@Named(value = "prosecutionMB")
@SessionScoped
public class ProsecutionMB implements Serializable {
    @Inject
    private LoginMB loginMB;
    
    private boolean success;
    private boolean error;
    private String responseMessage;
    private boolean canInsertPhase;

    private Long idProsecution;
    private final Date date = new Date();
    private Long idPromovente;
    private Long idPromoventeLawyer;
    private Long idPromovido;
    private Long idPromovidoLawyer;
    private ProsecutionDTO prosecutionDTO;
    private int filter;
    private List<User> parts;
    private List<User> lawyers;
    
    public ProsecutionMB() {
    }
    
    @PostConstruct
    public void init() {
        setSuccess(false);
        setError(false);
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql = "select * from tb_user where user_type_id = 4";
        Query query = session.createSQLQuery(hql).addEntity(User.class);
        
        parts = query.list();
        
        hql = "select * from tb_user where user_type_id = 2";
        query = session.createSQLQuery(hql).addEntity(User.class);;
        
        lawyers = query.list();
        
        session.getTransaction().commit();
        session.close();
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

    public boolean isCanInsertPhase() {
        return canInsertPhase;
    }

    public void setCanInsertPhase(boolean canInsertPhase) {
        this.canInsertPhase = canInsertPhase;
    }
    
    public Long getIdProsecution() {
        return idProsecution;
    }

    public void setIdProsecution(Long idProsecution) {
        this.idProsecution = idProsecution;
    }
    
    public Date getDate() {
        return date;
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

    public ProsecutionDTO getProsecutionDTO() {
        return prosecutionDTO;
    }

    public void setProsecutionDTO(ProsecutionDTO prosecutionDTO) {
        this.prosecutionDTO = prosecutionDTO;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }

    public List<User> getParts() {
        return parts;
    }

    public void setParts(List<User> parts) {
        this.parts = parts;
    }

    public List<User> getLawyers() {
        return lawyers;
    }

    public void setLawyers(List<User> lawyers) {
        this.lawyers = lawyers;
    }
    
    public String insertProsecution() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        boolean valid = true;
        String response = "";
        
        setError(false);
        setSuccess(false);
        
        String hql = "select us.id, count(prs.id) as qtdPros from tb_user us left join tb_prosecution prs on prs.judge_id = us.id where us.user_type_id = 3 group by us.id LIMIT 1";

        Query query = session.createSQLQuery(hql).addEntity(Judge.class);
        
        Judge judge = (Judge)query.uniqueResult();
        
        long idJudge = judge.getId();
        
        ProsecutionValidator prosecutionValidator = new ProsecutionValidator(valid);
        response = prosecutionValidator.validateProsecution(idJudge);
        valid = prosecutionValidator.isValid();
        
        if (!valid) {
            setError(true);
            setResponseMessage(response);
            return response;
        }
        
        User newJudge = new User(idJudge);
        
        Prosecution prosecution = new Prosecution(date, newJudge);
        
        session.save(prosecution);
        
        setIdPromoventeLawyer(loginMB.getIdUser());
        
        ProsecutionUserValidator prosecutionUserValidator = new ProsecutionUserValidator(valid);
        response = prosecutionUserValidator.validateProsecutionUser(idPromovente, idPromoventeLawyer , idPromovido, idPromovidoLawyer);
        valid = prosecutionUserValidator.isValid();
        
        if (!valid) {
            setError(true);
            setResponseMessage(response);
            return response;
        }
            
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
        
        return "/listar.xhtml";
    }
    
    public List<ProsecutionDTO> indexProsecution() {
        /* filter: 
            0 -> todos | 
            1 -> aberto | 
            2 -> encerrados | 
            3 -> ganho/perdido como promovido
            4 -> ganho/perdido como promovente
        */
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql = 
                "select \n" +
                "   prs.id as idProsecution, \n" +
                "   prs.prosecution_date as date, \n" +
                "   TO_CHAR(prs.prosecution_date, 'dd/mm/yyyy hh:mi') as formattedDate, \n" +
                "   jdg.id as idJudge, \n" +
                "   jdg.user_name as judge, \n" +
                "   prs_sts.prosecution_status_name as status,\n" +
                "   prt.user_name as part,\n" +
                "   prt_type.part_type_name as category \n" +
                "from tb_prosecution prs \n" +
                "inner join tb_user jdg\n" +
                "   on jdg.id = prs.judge_id\n" +
                "inner join tb_prosecution_user prs_usr\n" +
                "   on prs_usr.prosecution_id = prs.id\n" +
                "inner join tb_prosecution_status prs_sts\n" +
                "   on prs_sts.id = prs_usr.prosecution_status_id\n" +
                "inner join tb_user prt\n" +
                "   on prt.id = prs_usr.part_id\n" +
                "inner join tb_part_type prt_type\n" +
                "   on prt_type.id = prs_usr.part_type_id\n" +
                "where\n" +
                "   (prs_usr.lawyer_id = :idUser or prs_usr.part_id = :idUser) and\n" +
                "   (\n" + 
                "       0 = :filter or \n" +
                "       (\n" +
                "           1 = :filter and  \n" +
                "           prs_sts.id = 1\n" +
                "	) or \n" +
                "	(\n" +
                "           2 = :filter and  \n" +
                "           prs_sts.id = 2\n" +
                "	) or \n" +
                "	(\n" +
                "           3 = :filter and\n" +
                "           prt_type.id = 2 and\n" +
                "           (\n" +
                "              prs_sts.id = 3 or \n" +
                "              prs_sts.id = 4\n" +
                "           )\n" +
                "	) or\n" +
                "	(\n" +
                "           4 = :filter and  \n" +
                "           prt_type.id = 1 and\n" +
                "           (\n" +
                "              prs_sts.id = 3 or \n" +
                "              prs_sts.id = 4\n" +
                "           )\n" +
                "       )\n" +
                "   )\n" +
                "   order by prs.id";

        Long idUser = loginMB.getIdUser();
        
        Query query = session.createSQLQuery(hql);
        query.setParameter("idUser", idUser);
        query.setParameter("filter", filter);

        List<ProsecutionDTO> prosecutions = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        return prosecutions;
    }
    
    public List<ProsecutionDTO> indexProsecutionJudge() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
                
        String hql = 
            "select \n" +
            "   prs.id as idProsecution, \n" +
            "   prs.prosecution_date as date, \n" +
            "   TO_CHAR(prs.prosecution_date, 'dd/mm/yyyy hh:mi') as formattedDate \n" +
            "from tb_prosecution prs \n" +
            "where\n" +
            "   prs.judge_id = :idUser\n" +
            "   order by prs.id";
        
        Long idUser = loginMB.getIdUser(); 
        
        Query query = session.createSQLQuery(hql);
        query.setParameter("idUser", idUser);
        
        List<ProsecutionDTO> prosecutions = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        return prosecutions;
    }
    
    public ProsecutionDTO indexProsecutionById(Long idProsecution) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql = 
                "select \n" +
                "   prs.id as idProsecution, \n" +
                "   prs.prosecution_date as date, \n" +
                "   jdg.user_name as judge, \n" +
                "   prs_sts.prosecution_status_name as status,\n" +
                "   prt.user_name as part,\n" +
                "   prt_type.part_type_name as category\n" +
                "from tb_prosecution prs \n" +
                "inner join tb_user jdg\n" +
                "   on jdg.id = prs.judge_id\n" +
                "inner join tb_prosecution_user prs_usr\n" +
                "   on prs_usr.prosecution_id = prs.id\n" +
                "inner join tb_prosecution_status prs_sts\n" +
                "   on prs_sts.id = prs_usr.prosecution_status_id\n" +
                "inner join tb_user prt\n" +
                "   on prt.id = prs_usr.part_id\n" +
                "inner join tb_part_type prt_type\n" +
                "   on prt_type.id = prs_usr.part_type_id\n" +
                "where\n" +
                "prs.id = :idProsecution";
        
        Query query = session.createSQLQuery(hql);
        query.setParameter("idProsecution", idProsecution);
        
        ProsecutionDTO prosecution = (ProsecutionDTO) query.uniqueResult();
        
        session.getTransaction().commit();
        session.close();
        
        return prosecution;
    }
    
    public String editProsecution(Long idProsecution, Date date, String formattedDate, Long idJudge, String judge, String status, String part, String category) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        setCanInsertPhase(true);
        
        setIdProsecution(idProsecution);
        
        if (idJudge == null && loginMB.getIdUserType() == 3) {
            idJudge = loginMB.getIdUser();
            judge = loginMB.getName();
        }
        
        prosecutionDTO = new ProsecutionDTO(idProsecution, date, formattedDate, idJudge, judge, status, part, category);
        
        String hql = "select * from tb_prosecution_user where prosecution_id = :idProsecution and part_type_id = 1";
        Query query = session.createSQLQuery(hql).addEntity(ProsecutionUser.class);
        query.setParameter("idProsecution", idProsecution);
        
        ProsecutionUser promovente = (ProsecutionUser) query.uniqueResult();
        
        prosecutionDTO.setIdProsecutionUserPromovente(promovente.getId());
        prosecutionDTO.setIdPromovente(promovente.getPart().getId());
        prosecutionDTO.setPromovente(promovente.getPart().getName());
        prosecutionDTO.setIdPromoventeLawyer(promovente.getLawyer().getId());
        prosecutionDTO.setPromoventeLawyer(promovente.getLawyer().getName());
        
        hql = "select * from tb_prosecution_user where prosecution_id = :idProsecution and part_type_id = 2";
        query = session.createSQLQuery(hql).addEntity(ProsecutionUser.class);
        query.setParameter("idProsecution", idProsecution);
        
        ProsecutionUser promovido = (ProsecutionUser) query.uniqueResult();
        
        prosecutionDTO.setIdProsecutionUserPromovido(promovido.getId());
        prosecutionDTO.setIdPromovido(promovido.getPart().getId());
        prosecutionDTO.setPromovido(promovido.getPart().getName());
        prosecutionDTO.setIdPromovidoLawyer(promovido.getLawyer().getId());
        prosecutionDTO.setPromovidoLawyer(promovido.getLawyer().getName());
        
        hql = "select * from tb_phase where prosecution_id = :idProsecution order by id desc limit 1";
        query = session.createSQLQuery(hql).addEntity(Phase.class);
        query.setParameter("idProsecution", idProsecution);
        
        Phase phase = (Phase) query.uniqueResult();
        
        if (phase != null && (phase.getPhaseStatus() == null || phase.getPhaseStatus().getId() != 1))
            setCanInsertPhase(false);
        
        session.getTransaction().commit();
        session.close();
        
        return "/EditarProcesso.xhtml";
    }
    
    public String updateProsecution() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        boolean valid = true;
        String response = "";
        
        setError(false);
        setSuccess(false);
        
        setIdPromovente(prosecutionDTO.getIdPromovente());
        setIdPromoventeLawyer(prosecutionDTO.getIdPromoventeLawyer());
        setIdPromovido(prosecutionDTO.getIdPromovido());
        setIdPromovidoLawyer(prosecutionDTO.getIdPromovidoLawyer());
        
        ProsecutionUserValidator prosecutionUserValidator = new ProsecutionUserValidator(valid);
        response = prosecutionUserValidator.validateProsecutionUser(idPromovente, idPromoventeLawyer, idPromovido, idPromovidoLawyer);
        valid = prosecutionUserValidator.isValid();
        
        if (!valid) {
            setResponseMessage(response);
            setError(true);
            return response;
        }
        
        User partPromovente = new User(idPromovente);
        User promoventeLawyer = new User(idPromoventeLawyer);
        User partPromovido = new User(idPromovido);
        User promovidoLawyer = new User(idPromovidoLawyer);
        
        ProsecutionStatus prosecutionStatus = new ProsecutionStatus(1);
        PartType partPromoventeType = new PartType(1);
        PartType partPromovidoType = new PartType(2);
        
        User judge = new User(prosecutionDTO.getIdJudge());
        
        Prosecution prosecution = new Prosecution(prosecutionDTO.getIdProsecution(), prosecutionDTO.getDate(), judge);
        
        ProsecutionUser prosecutionUserPromovente = new ProsecutionUser(prosecution, partPromovente, promoventeLawyer, prosecutionStatus, partPromoventeType);
        prosecutionUserPromovente.setId(prosecutionDTO.getIdProsecutionUserPromovente());
        
        ProsecutionUser prosecutionUserPromovido = new ProsecutionUser(prosecution, partPromovido, promovidoLawyer, prosecutionStatus, partPromovidoType);
        prosecutionUserPromovido.setId(prosecutionDTO.getIdProsecutionUserPromovido());
        
        session.update(prosecutionUserPromovente);
        session.update(prosecutionUserPromovido);
        
        session.getTransaction().commit();
        session.close();
        
        response = "Processo atualizado com sucesso!";
        
        setResponseMessage(response);
        setSuccess(true);
        return response;
    }
}
