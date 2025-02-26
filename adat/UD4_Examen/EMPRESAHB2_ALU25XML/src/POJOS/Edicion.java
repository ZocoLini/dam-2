package POJOS;

import java.util.Date;

public class Edicion implements java.io.Serializable {
    private EdicionId id;
    private Date data;
    private String lugar;
    private Curso curso;
    private Empregadofixo profesor;

    public Edicion() {
    }

    public EdicionId getId() {
        return this.id;
    }

    public void setId(EdicionId id) {
        this.id = id;
    }
  
    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLugar() {
        return this.lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Empregadofixo getProfesor() {
        return profesor;
    }

    public void setProfesor(Empregadofixo profesor) {
        this.profesor = profesor;
    }
}