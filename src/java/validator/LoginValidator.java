package validator;

import model.User;

public class LoginValidator {
    private boolean valid;

    public LoginValidator(boolean valid) {
        this.valid = valid;
    }
    
    public boolean getValid() {
        return this.valid;
    }
    
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String validateLogin(String email, String password, User user) {
        if (email == null || email.isEmpty()) {
            setValid(false);
            return "Preencha o e-mail";
        }
            
        if (password == null || password.isEmpty()) {
            setValid(false);
            return "Preencha a senha";
        }
            
        if (user == null)
            return "E-mail ou senha incorretos";
        
        return "Logando";
    }
}
