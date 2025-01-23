package org.lebastudios.entities;

import java.lang.String;
import java.lang.Character;
import java.util.Date;
import java.lang.Float;


public class Empregado {
    private String nome;
    private String apelido1;
    private String apelido2;
    private String nss;
    private Float salario;
    private Date dataNacemento;
    private Character sexo;

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
    public String getNss() {
return this.nss;
}
    public void setNss(String nss) {
this.nss = nss;
}
    public Float getSalario() {
return this.salario;
}
    public void setSalario(Float salario) {
this.salario = salario;
}
    public Date getDataNacemento() {
return this.dataNacemento;
}
    public void setDataNacemento(Date dataNacemento) {
this.dataNacemento = dataNacemento;
}
    public Character getSexo() {
return this.sexo;
}
    public void setSexo(Character sexo) {
this.sexo = sexo;
}

}