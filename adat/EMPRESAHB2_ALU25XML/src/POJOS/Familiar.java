package POJOS;

import java.util.Date;

public class Familiar  implements java.io.Serializable {

     private String nss;
     private String nome;
     private String apelido1;
     private String apelido2;
     private Date dataNacimento;
     private String parentesco;
     private char sexo;

    public Familiar() {
    }

    public Familiar(String nss, String nome, String apelido1, String apelido2, Date dataNacimento, String parentesco, char sexo) {
        this.nss = nss;
        this.nome = nome;
        this.apelido1 = apelido1;
        this.apelido2 = apelido2;
        this.dataNacimento = dataNacimento;
        this.parentesco = parentesco;
        this.sexo = sexo;
    }

	
   
       public String getNss() {
        return this.nss;
    }
    
    public void setNss(String nss) {
        this.nss = nss;
    }
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getApelido1() {
        return this.apelido1;
    }
    
    public void setApelido1(String apelido1) {
        this.apelido1 = apelido1;
    }
    public String getApelido2() {
        return this.apelido2;
    }
    
    public void setApelido2(String apelido2) {
        this.apelido2 = apelido2;
    }
    public Date getDataNacimento() {
        return this.dataNacimento;
    }
    
    public void setDataNacimento(Date dataNacimento) {
        this.dataNacimento = dataNacimento;
    }
    public String getParentesco() {
        return this.parentesco;
    }
    
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
    public char getSexo() {
        return this.sexo;
    }
    
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }




}


