package validator;

import model.User;

public class UserValidator {
    private boolean valid;
    
    public UserValidator(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            setValid(false);
            return "Preencha o e-mail";
        }
        
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            setValid(false);
            return "Preencha a senha";
        }
        
        if (user.getName() == null || user.getName().isEmpty()) {
            setValid(false);
            return "Preencha o nome";
        }
        
        if (user.getCpf() == null || user.getCpf().isEmpty()) {
            setValid(false);
            return "Preencha o CPF";
        }
        
        return "ok";
    }
}
