package POJOS;

import java.util.Date;


public class Empregadotemporal extends Empregado  implements java.io.Serializable {

     private Date dataInicio;
     private Date dataFin;
     private Double costeHora;
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


