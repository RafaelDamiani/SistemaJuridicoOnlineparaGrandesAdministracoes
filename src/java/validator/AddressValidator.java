package validator;

import model.Address;

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
    
    private String street;
    private Short number;
    private String city;
    private String state;
    
    public String validateAddress(Address address) {
        if (address.getZipCode() == null || address.getZipCode().isEmpty()) {
            setValid(false);
            return "Preencha o CEP";
        }
        
        if (address.getStreet() == null || address.getStreet().isEmpty()) {
            setValid(false);
            return "Preencha a rua";
        }
        
        if (address.getNumber() == null) {
            setValid(false);
            return "Preencha o número";
        }
        
        if (address.getCity() == null || address.getCity().isEmpty()) {
            setValid(false);
            return "Preencha a cidade";
        }
        
        if (address.getState() == null || address.getState().isEmpty()) {
            setValid(false);
            return "Preencha o estado";
        }
        
        return "ok";
    }
}
