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
    
    public String validateProsecutionUser(Long idPromovente, Long idPromoventeLawyer, Long idPromovido, Long idPromovidoLawyer) {
        if (idPromovente == null || idPromovente == 0) {
            setValid(false);
            return "Preencha o promovente";
        }
        
        if (idPromoventeLawyer == null || idPromoventeLawyer == 0) {
            setValid(false);
            return "Preencha o advogado do promovente";
        }
        
        if (idPromovido == null || idPromovido == 0) {
            setValid(false);
            return "Preencha o promovido";
        }

        if (idPromovidoLawyer == null || idPromovidoLawyer == 0) {
            setValid(false);
            return "Preencha o advogado do promovido";
        }
        
        return "ok";
    }
}
