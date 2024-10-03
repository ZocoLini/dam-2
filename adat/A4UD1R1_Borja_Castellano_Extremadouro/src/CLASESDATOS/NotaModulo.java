/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASESDATOS;

import java.io.Serializable;

/**
 * @author usuario
 */
public class NotaModulo implements Serializable
{
    private String asignatura;
    private double nota;

    public NotaModulo() {}

    public NotaModulo(String asignatura, Double nota)
    {
        this.asignatura = asignatura;
        this.nota = nota;
    }

    public String getAsignatura()
    {
        return asignatura;
    }

    public void setAsignatura(String asignatura)
    {
        this.asignatura = asignatura;
    }

    public Double getNota()
    {
        return nota;
    }

    public void setNota(Double nota)
    {
        this.nota = nota;
    }


    @Override
    public String toString()
    {
        return "NotaModulo{" +
                "asignatura='" + asignatura + '\'' +
                ", nota=" + nota +
                '}';
    }
}