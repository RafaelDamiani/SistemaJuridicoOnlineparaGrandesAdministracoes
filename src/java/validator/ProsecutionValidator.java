package validator;

public class ProsecutionValidator {
    private boolean valid;
    
    public ProsecutionValidator(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String validateProsecution(Long idJudge) {
        if (idJudge == null || idJudge == 0) {
            setValid(false);
            return "Preencha o juiz";
        }
        
        return "ok";
    }
}
