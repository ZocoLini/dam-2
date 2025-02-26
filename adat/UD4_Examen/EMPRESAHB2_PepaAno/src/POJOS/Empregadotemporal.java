package POJOS;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "EMPREGADOTEMPORAL", schema = "dbo", catalog = "EMPRESAHB2_25")
@PrimaryKeyJoinColumn(name = "NSS") // Se enlaza con la clave primaria de EMPREGADO
public class Empregadotemporal extends Empregado implements java.io.Serializable {

    @Column(name = "DataInicio")
    private Date dataInicio;

    @Column(name = "DataFin")
    private Date dataFin;

    @Column(name = "CosteHora")
    private Double costeHora;

    @Column(name = "NumHoras")
    private Double numHoras;

    public Empregadotemporal() {
    }

    public Empregadotemporal(Date dataInicio, Date dataFin, Double costeHora, Double numHoras, String nss, String nome, String apelido1) {
        super(nss, nome, apelido1);
        this.dataInicio = dataInicio;
        this.dataFin = dataFin;
        this.costeHora = costeHora;
        this.numHoras = numHoras;
    }

    public Date getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFin() {
        return this.dataFin;
    }

    public void setDataFin(Date dataFin) {
        this.dataFin = dataFin;
    }

    public Double getCosteHora() {
        return this.costeHora;
    }

    public void setCosteHora(Double costeHora) {
        this.costeHora = costeHora;
    }

    public Double getNumHoras() {
        return this.numHoras;
    }

    public void setNumHoras(Double numHoras) {
        this.numHoras = numHoras;
    }

}
