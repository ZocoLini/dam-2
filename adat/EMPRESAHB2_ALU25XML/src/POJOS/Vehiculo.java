package POJOS;

import java.util.Date;


public class Vehiculo  implements java.io.Serializable {


     private String nss;   
     private String matricula;
     private String marca;
     private String modelo;
     private Date dataCompra;
     
    public Vehiculo() {
    }
	
    
   
    public String getNss() {
        return this.nss;
    }
    
    public void setNss(String nss) {
        this.nss = nss;
    }
 
    public String getMatricula() {
        return this.matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public String getMarca() {
        return this.marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return this.modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public Date getDataCompra() {
        return this.dataCompra;
    }
    
    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

}


