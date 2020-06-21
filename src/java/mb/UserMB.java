package mb;

import model.User;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.*;
import javax.faces.bean.ManagedBean;
import org.hibernate.*;
import util.HibernateUtil;

@ManagedBean(name = "userMB")
@RequestScoped
public class UserMB {
    private User user;
    private List<User> userList;
    
    private String name;
    
    public UserMB() {
    }
    
    @PostConstruct
    public void init() {
        this.user = new User();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String salvar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        user.setName(name);
        
        session.save(user);
        session.getTransaction().commit();
        session.beginTransaction();
        
        Query query = session.createQuery("from User");
        this.userList = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        return "salvar";
    }
    
    public String listar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Query query = session.createQuery("from User");
        this.userList = query.list();
        
        session.getTransaction().commit();
        
        session.close();
        
        return "listar";
    }
}
