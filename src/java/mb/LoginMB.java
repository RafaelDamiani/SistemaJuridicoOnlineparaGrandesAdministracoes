package mb;

import model.User;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.*;
import javax.inject.Named;
import org.hibernate.*;
import util.HibernateUtil;

@Named(value = "loginMB")
@RequestScoped
public class Login {
    private String email;
    private String password;
    
    public Login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String logar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        String hql = "select * from user where email = :email and password = :password";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        
        
        
        query.setParameter("password", password);        
                
        session.getTransaction().commit();
        session.close();
        
        return "salvar";
    }
}
