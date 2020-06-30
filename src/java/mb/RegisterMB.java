package mb;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import model.Address;
import model.User;
import model.UserType;
import org.hibernate.Session;
import util.HibernateUtil;
import util.PasswordUtil;
import validator.AddressValidator;
import validator.UserTypeValidator;
import validator.UserValidator;

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
    
    public String registerUser() throws NoSuchAlgorithmException {
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
        
        UserValidator userValidator = new UserValidator(valid);
        response = userValidator.validateUser(email, password, name, cpf);
        valid = userValidator.isValid();
        
        if (!valid)
            return response;
        
        String encryptedPassword = new PasswordUtil().encryptPassword(password);
        
        User user = new User(email, encryptedPassword, name, cpf, userType);
        
        AddressValidator addressValidator = new AddressValidator(valid);
        response = addressValidator.validateAddress(zipCode, street, number, city, state);
        valid = addressValidator.isValid();
        
        if (!valid)
            return response;
        
        Address address = new Address(zipCode, street, number, city, state, user);
        try {
            session.save(user);    
        }
        catch(RuntimeException rex) {
            if (rex.getClass().getCanonicalName().contains("ConstraintViolationException"))
                return "Já existe um usuário cadastrado com este e-mail";
            return "Ocorreu um erro ao salvar o usuário. Entre em contato com o Administrador";
        }
        
        session.save(address);
        
        session.getTransaction().commit();
        session.close();
        
        return "Cadastrado com sucesso!";
    }
}
