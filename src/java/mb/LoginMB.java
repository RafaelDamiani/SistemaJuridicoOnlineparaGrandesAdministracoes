package mb;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.enterprise.context.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import model.User;
import org.hibernate.*;
import util.HibernateUtil;
import util.PasswordUtil;
import validator.LoginValidator;

@Named(value = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {

    private String email;
    private String password;
    private String name;
    private Long idUser;
    private Integer idUserType;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Integer getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(Integer idUserType) {
        this.idUserType = idUserType;
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

        if (!valid) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(response));
            return response;
        }

        String encryptedPassword = new PasswordUtil().encryptPassword(password);

        String hql = "select * from tb_user where user_email = :email and user_password = :password";

        Query query = session.createSQLQuery(hql).addEntity(User.class);
        query.setParameter("email", email);
        query.setParameter("password", encryptedPassword);

        user = (User) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        response = loginValidator.validateLogin(email, password, user);

        if (response.equals("E-mail ou senha incorretos")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(response));
        }

        setIdUser(user.getId());
        setIdUserType(user.getUserType().getId());
        setName(user.getName());

        return response;
    }

    public String Deslogar() throws NoSuchAlgorithmException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("#{LoginMB}");

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        session.invalidate();

        return "";
    }
}
