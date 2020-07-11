package validator;

public class PhaseValidator {
    private boolean valid;
    
    public PhaseValidator(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String validatePhase(Long idProsecution, Long idLawyer, String title, String description, String justification, Integer idPhaseType, Integer idPhaseStatus) {
        if (idProsecution == null || idProsecution == 0) {
            setValid(false);
            return "A fase não possui nenhum processo atrelado à ela. Entre em contato com o Adminstrador.";
        }
        
        if (idLawyer == null || idLawyer == 0) {
            setValid(false);
            return "A fase não possui nenhum advogado atrelado à ela. Entre em contato com o Adminstrador.";
        }

        if (title == null || title.isEmpty()) {
            setValid(false);
            return "Preencha o título";
        }
        
        if (title.length() < 5) {
            setValid(false);
            return "O título deve ter no mínimo 5 caracteres";
        }
        
        if (description == null || description.isEmpty()) {
            setValid(false);
            return "Preencha a descrição";
        }
        
        if (description.length() < 25) {
            setValid(false);
            return "A descrição deve ter no mínimo 25 caracteres";
        }
        
        return "ok";
    }
}
