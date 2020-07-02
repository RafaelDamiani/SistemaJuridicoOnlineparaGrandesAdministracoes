package validator;

public class ProsecutionUserValidator {
    private boolean valid;
    
    public ProsecutionUserValidator(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String validateProsecutionUser(String promovente, String promovido) {
        if (promovente == null || promovente.isEmpty()) {
            setValid(false);
            return "Preencha o promovente";
        }
        
        if (promovido == null || promovido.isEmpty()) {
            setValid(false);
            return "Preencha o promovido";
        }
        
        return "ok";
    }
}
