package pojos;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/* Mapeo */
@Embeddable
public class Telefono {
    @Column(name="Numero", length = 9, nullable = false)
    private String numero;
    @Column(name="Informacion", length = 15)
    private String informacion;

    public Telefono() {
    }

    public Telefono(String numero) {
        this.numero = numero;
    }

    public Telefono(String numero, String informacion) {
        this.numero = numero;
        this.informacion = informacion;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getInformacion() {
        return this.informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    @Override public String toString() {
        return "Telefono{" +
                "numero='" + numero + '\'' +
                ", informacion='" + informacion + '\'' +
                '}';
    }

    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Telefono telefono = (Telefono) o;
        return Objects.equals(numero, telefono.numero);
    }

    @Override public int hashCode() {
        return Objects.hashCode(numero);
    }
}
