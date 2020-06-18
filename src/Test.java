import org.hibernate.Session;
import model.User;
import util.HibernateUtil;

public class Test {
    public static void main(String[] args) {
        User user = new User();
        user.setName("Paulo");
        Session session = HibernateUtil.getSessionFactory().
                openSession();

        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        System.out.println(user.getName() + " Inserido com sucesso.");
    }
}
