package mb;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import model.UserType;
import org.hibernate.Session;
import util.HibernateUtil;
import validator.UserTypeValidator;

@Named(value = "registerMB")
@RequestScoped
public class RegisterMB {
    private String email;
    private String password;
    private String name;
    private String cpf;
    private Integer idUserType;
    
    private String zipCode;
    private String street;
    private Short number;
    private String city;
    private String state;
    
    public RegisterMB() {
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(Integer idUserType) {
        this.idUserType = idUserType;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Short getNumber() {
        return number;
    }

    public void setNumber(Short number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String registeruser() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        boolean valid = true;
        String response = "";
        
        UserTypeValidator userTypeValidator = new UserTypeValidator(valid);
        response = userTypeValidator.validateId(idUserType);
        
        valid = userTypeValidator.isValid();
        
        if (!valid)
            return response;

        UserType userType = new UserType();
        userType.setId(idUserType);
        
        // UserType
        
        // User
        
        // Address
        
        session.getTransaction().commit();
        session.close();
        
        return "Cadastrado com sucesso!";
    }
}
