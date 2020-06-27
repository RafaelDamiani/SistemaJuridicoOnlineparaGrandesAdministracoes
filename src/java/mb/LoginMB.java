package mb;

import java.security.NoSuchAlgorithmException;
import javax.enterprise.context.*;
import javax.inject.Named;
import model.User;
import org.hibernate.*;
import util.HibernateUtil;
import util.PasswordUtil;
import validator.LoginValidator;

@Named(value = "loginMB")
@RequestScoped
public class LoginMB {
    private String email;
    private String password;
    
    public LoginMB() {
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
    
    public String logar() throws NoSuchAlgorithmException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        boolean valid = true;
        String response = "";
        
        User user = null;
                
        LoginValidator loginValidator = new LoginValidator(valid);
        
        response = loginValidator.validateLogin(email, password, user);
        
        valid = loginValidator.isValid();
        
        if (!valid)
            return response;
        
        String encryptedPassword = new PasswordUtil().encryptPassword(password);
        
        String hql = "select * from tb_user where user_email = :email and user_password = :password";
        
        Query query = session.createSQLQuery(hql).addEntity(User.class);
        query.setParameter("email", email);
        query.setParameter("password", encryptedPassword);
        
        user = (User)query.uniqueResult();
        
        session.getTransaction().commit();
        session.close();
        
        response = loginValidator.validateLogin(email, password, user);
        
        return response;
    }
}
