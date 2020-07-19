package mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import model.Judge;
import model.PartType;
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
    
    private final Date date = new Date();
    private Long idPromovente;
    private Long idPromoventeLawyer;
    private Long idPromovido;
    private Long idPromovidoLawyer;
    private ProsecutionDTO prosecutionDTO;
    private int filter;
    private DataModel<ProsecutionDTO> prosecutionList;
    
    public ProsecutionMB() {
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
        
        Prosecution prosecution = new Prosecution(date, newJudge);
        
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
                "   prs_usr.lawyer_id = :idUser or prs.judge_id = :idUser or prs_usr.part_id = :idUser and\n" +
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
        
        prosecutionList = new ListDataModel<>(prosecutions);
        
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
        
        prosecutionDTO = new ProsecutionDTO(idProsecution, date, formattedDate, idJudge, judge, status, part, category);
        
        String hql = "select * from tb_prosecution_user where prosecution_id = :idProsecution and part_type_id = 1";
        Query query = session.createSQLQuery(hql).addEntity(ProsecutionUser.class);
        query.setParameter("idProsecution", idProsecution);
        
        ProsecutionUser promovente = (ProsecutionUser) query.uniqueResult();
        
        prosecutionDTO.setIdPromovente(promovente.getPart().getId());
        prosecutionDTO.setPromovente(promovente.getPart().getName());
        prosecutionDTO.setIdPromoventeLawyer(promovente.getLawyer().getId());
        prosecutionDTO.setPromoventeLawyer(promovente.getLawyer().getName());
        
        hql = "select * from tb_prosecution_user where prosecution_id = :idProsecution and part_type_id = 2";
        query = session.createSQLQuery(hql).addEntity(ProsecutionUser.class);
        query.setParameter("idProsecution", idProsecution);
        
        ProsecutionUser promovido = (ProsecutionUser) query.uniqueResult();
        
        prosecutionDTO.setIdPromovido(promovido.getPart().getId());
        prosecutionDTO.setPromovido(promovido.getPart().getName());
        prosecutionDTO.setIdPromovidoLawyer(promovido.getLawyer().getId());
        prosecutionDTO.setPromovidoLawyer(promovido.getLawyer().getName());
        
        session.getTransaction().commit();
        session.close();
        
        return "/EditarProcesso.xhtml";
    }
    
    public String updateProsecution() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        boolean valid = true;
        String response = "";
        
        ProsecutionUserValidator prosecutionUserValidator = new ProsecutionUserValidator(valid);
        response = prosecutionUserValidator.validateProsecutionUser(idPromovente, idPromoventeLawyer , idPromovido, idPromovidoLawyer);
        valid = prosecutionUserValidator.isValid();
        
        if (!valid)
            return response;
        
        User partPromovente = new User(prosecutionDTO.getIdPromovente());
        User promoventeLawyer = new User(prosecutionDTO.getIdPromoventeLawyer());
        User partPromovido = new User(prosecutionDTO.getIdPromovido());
        User promovidoLawyer = new User(prosecutionDTO.getIdPromovidoLawyer());
        
        ProsecutionStatus prosecutionStatus = new ProsecutionStatus(1);
        PartType partPromoventeType = new PartType(1);
        PartType partPromovidoType = new PartType(2);
        
        User judge = new User(prosecutionDTO.getIdJudge());
        
        Prosecution prosecution = new Prosecution(prosecutionDTO.getIdProsecution(), prosecutionDTO.getDate(), judge);
        
        ProsecutionUser prosecutionUserPromovente = new ProsecutionUser(prosecution, partPromovente, promoventeLawyer, prosecutionStatus, partPromoventeType);
        ProsecutionUser prosecutionUserPromovido = new ProsecutionUser(prosecution, partPromovido, promovidoLawyer, prosecutionStatus, partPromovidoType);
        
        session.update(prosecutionUserPromovente);
        session.update(prosecutionUserPromovido);
        
        session.getTransaction().commit();
        session.close();
        
        return "Processo atualizado com sucesso!";
    }
}
