package pojos;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "VEHICULO",
        schema = "dbo",
        catalog = "EmpresaHB"
)
public class Vehiculo {
    @Id @Column(name = "NSS", nullable = false, length = 15)
    private String nss;

    @MapsId @OneToOne()
    @JoinColumn(name = "NSS")
    private Empregado empregado;

    @Column(name = "Matricula", nullable = false, length = 8)
    private String matricula;

    @Column(name = "Marca", nullable = false, length = 30)
    String marca;

    @Column(name = "Modelo", nullable = false, length = 30)
    private String modelo;

    @Column(name = "Data_compra", nullable = false)
    private Date dataCompra;

    public String getNss() {
        return nss;
    }
    public void setNss(String nss) {
        this.nss = nss;
    }

    public Empregado getEmpregado() {
        return empregado;
    }
    public void setEmpregado(Empregado empregado) {
        this.empregado = empregado;
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

}