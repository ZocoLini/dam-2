package POJOS;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EdicionId  implements java.io.Serializable {
    @Column(name = "Codigo",nullable = false)
    private long codigo;

    @Column(name = "Numero",nullable = false)
    private long numero;

    public EdicionId() {
    }

    public EdicionId(long codigo) {
        this.codigo = codigo;
    }

    public EdicionId(long codigo, long numero) {
       this.codigo = codigo;
       this.numero = numero;
    }
   
    public long getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
    public long getNumero() {
        return this.numero;
    }
    
    public void setNumero(long numero) {
        this.numero = numero;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EdicionId other = (EdicionId) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        if (this.numero != other.numero) {
            return false;
        }
        return true;
    }


 


}


