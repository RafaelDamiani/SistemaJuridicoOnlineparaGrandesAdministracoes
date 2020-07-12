package validator;

public class UserTypeValidator {
    private boolean valid;
    
    public UserTypeValidator(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String validateId(Integer id) {
        if (id == null || id == 0) {
            setValid(false);
            return "Preencha o tipo do usuário";
        }
        
        if (id == 1) {
            setValid(false);
            return "Não é permitido cadastrar um Admin";
        }
        
        return "ok";
    }
}
