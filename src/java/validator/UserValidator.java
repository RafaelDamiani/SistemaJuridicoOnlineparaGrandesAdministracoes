package validator;

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
    
    public String validateUser(String email, String password, String name, String cpf) { 
        if (email == null || email.isEmpty()) {
            setValid(false);
            return "Preencha o e-mail";
        }
        
        if (!email.contains("@")) {
            setValid(false);
            return "O e-mail está em um formato incorreto";
        }
        
        if (password == null || password.isEmpty()) {
            setValid(false);
            return "Preencha a senha";
        }
        
        if (password.length() < 6) {
            setValid(false);
            return "A senha deve ter no mínimo 6 caracteres";
        }
        
        if (name == null || name.isEmpty()) {
            setValid(false);
            return "Preencha o nome";
        }
        
        if (cpf == null || cpf.isEmpty()) {
            setValid(false);
            return "Preencha o CPF";
        }
        
        if (cpf.length() < 14 || cpf.length() > 14) {
            setValid(false);
            return "O CPF deve ter 11 caracteres";
        }
        
        
        return "ok";
    }
}
