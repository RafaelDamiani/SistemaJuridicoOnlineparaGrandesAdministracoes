package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Judge {
    private Long id;
    private Long qtdPros;
    
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getQtdPros() {
        return qtdPros;
    }

    public void setQtdPros(Long qtdPros) {
        this.qtdPros = qtdPros;
    }
}
