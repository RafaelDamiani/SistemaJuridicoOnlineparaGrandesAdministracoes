package mb;

import java.security.NoSuchAlgorithmException;
import model.User;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.*;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.hibernate.*;
import util.HibernateUtil;

@Named(value = "userMB")
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

    public String Deslogar() throws NoSuchAlgorithmException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("#{userMB}");

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        session.invalidate();

        return "./index.xhtml";
    }
}
