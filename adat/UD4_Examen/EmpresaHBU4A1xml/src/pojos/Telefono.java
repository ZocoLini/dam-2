/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.util.Objects;

/**
 *
 * @author ofernpast
 */
public class Telefono {
    private String numero;
    private String informacion;

    public Telefono() {
    }

    public Telefono(String numero) {
        this.numero = numero;
    }

    public Telefono(String informacion, String numero) {
        this.informacion = informacion;
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getInformacion() {
        return informacion;
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
