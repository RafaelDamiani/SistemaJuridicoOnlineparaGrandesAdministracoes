package validator;

public class AddressValidator {
    private boolean valid;
    
    public AddressValidator(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public String validateAddress(String zipCode, String street, Short number, String city, String state) {
        if (zipCode == null || zipCode.isEmpty()) {
            setValid(false);
            return "Preencha o CEP";
        }

        if (zipCode.length() < 8) {
            setValid(false);
            return "O CEP deve ter 8 caracteres";
        }
        
        if (street == null || street.isEmpty()) {
            setValid(false);
            return "Preencha a rua";
        }
        
        if (number == null) {
            setValid(false);
            return "Preencha o número";
        }
        
        if (city == null || city.isEmpty()) {
            setValid(false);
            return "Preencha a cidade";
        }
        
        if (state == null || state.isEmpty()) {
            setValid(false);
            return "Preencha o estado";
        }
        
        return "ok";
    }
}
