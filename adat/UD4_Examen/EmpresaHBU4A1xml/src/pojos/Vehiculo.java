package pojos;

import java.io.Serializable;
import java.sql.Date;

public class Vehiculo implements Serializable {
    private String nss;
    private String matricula;
    private String marca;
    private String modelo;
    private Date dataCompra;

    public Vehiculo() {}

    public Vehiculo(String nss, String matricula, String marca, String modelo, Date dataCompra) {
        this.nss = nss;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.dataCompra = dataCompra;
    }

    public String getNss() {
        return nss;
    }
    public void setNss(String nss) {
        this.nss = nss;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public Date getDataCompra() {
        return dataCompra;
    }
    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    /* Mapeo con el empleado */
    private Empregado propietario;

    public Empregado getPropietario() {
        return propietario;
    }
    public void setPropietario(Empregado propietario) {
        this.propietario = propietario;
    }


}