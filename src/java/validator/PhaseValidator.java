package validator;

public class PhaseValidator {
    private boolean valid;
    private boolean inserting;
    
    public PhaseValidator(boolean valid, boolean inserting) {
        this.valid = valid;
        this.inserting = inserting;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isInserting() {
        return inserting;
    }

    public void setInserting(boolean inserting) {
        this.inserting = inserting;
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
        
        if (idPhaseType == null || idPhaseType == 0) {
            setValid(false);
            return "Defina o tipo da fase";
        }
        
        if (inserting)
            return "Fase cadastrada com sucesso!";
        
        if (idPhaseStatus == null || idPhaseStatus == 0) {
            setValid(false);
            return "Escolha o status da fase";
        }
        
        if (idPhaseStatus == 2){
            if (justification == null || justification.isEmpty()) {
                setValid(false);
                return "Preencha a justificativa";
            }
            if (justification.length() < 25) {
                setValid(false);
                return "A justificativa deve ter no mínimo 25 caracteres";
            }
        }
        
        return "Fase atualizada com sucesso!";
    }
}
