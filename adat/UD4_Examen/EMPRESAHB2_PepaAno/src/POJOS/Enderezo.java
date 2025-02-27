/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

import javax.persistence.Column;
import javax.persistence.Embeddable;
//clase componente
@Embeddable
public class Enderezo {

    @Column(name = "Rua", length = 30)
    private String rua;

    @Column(name = "Numero_Calle")
    private Integer numeroCalle;

    @Column(name = "Piso", length = 4)
    private String piso;

    @Column(name = "CP", length = 5)
    private String cp;

    @Column(name = "Localidade", length = 25)
    private String localidade;

    @Column(name = "Provincia", length = 15)
    private String provincia;

    public Enderezo() {
    }

    public Enderezo(String rua, Integer numeroCalle, String piso, String cp, String localidade, String provincia) {
        this.rua = rua;
        this.numeroCalle = numeroCalle;
        this.piso = piso;
        this.cp = cp;
        this.localidade = localidade;
        this.provincia = provincia;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumeroCalle() {
        return numeroCalle;
    }

    public void setNumeroCalle(Integer numeroCalle) {
        this.numeroCalle = numeroCalle;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

}
