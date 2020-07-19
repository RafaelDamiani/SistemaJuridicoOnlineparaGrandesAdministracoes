/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.text.ParseException;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;
import util.HibernateUtil;
import model.Phase;
import model.PhaseStatus;
import org.hibernate.Query;

/**
 * REST Web Service
 *
 * @author rafae
 */
@Path("phase")
public class PhaseWS {
    private final Date date = new Date();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PhaseWS
     */
    public PhaseWS() {
    }
    
    @POST
    @Consumes({ 
        MediaType.APPLICATION_JSON, 
        MediaType.APPLICATION_FORM_URLENCODED
    })
    public void postPhase(@FormParam("prosecution") Long prosecution) throws ParseException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String hql = "select top 1 * from tb_phase where prosecution_id = :prosecution_id and phase_status_id = 3 order by id desc";
        
        Query query = session.createSQLQuery(hql).addEntity(Phase.class);
        query.setParameter("prosecution_id", prosecution);
        
        Phase phase = (Phase)query.uniqueResult();
        
        PhaseStatus phaseStatus = new PhaseStatus(4);
        
        Phase newPhase = new Phase(date, phase.getProsecution(), phase.getLawyer(), phase.getTitle(), phase.getDescription(), phase.getJustification(), phase.getPhaseType(), phaseStatus);
        
        session.save(newPhase);

        session.getTransaction().commit();
        session.close();
    }
}
