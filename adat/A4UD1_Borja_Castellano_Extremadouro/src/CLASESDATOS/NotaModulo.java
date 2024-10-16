/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASESDATOS;

import java.io.Serializable;
import java.util.Scanner;

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

    public boolean isLast()
    {
        return asignatura.equals("*");
    }
    
    public static NotaModulo generate()
    {
        System.out.println("Introduce el nombre del módulo: ");
        String modulo = new Scanner(System.in).nextLine();
        
        if (modulo.equals("*")) return new NotaModulo(modulo, 0.0);
        
        System.out.println("Introduce la nota del módulo: ");
        double nota = new Scanner(System.in).nextDouble();
        
        return new NotaModulo(modulo, nota);
    }

    public String preattyPrinting()
    {
        return String.format("%s\t\t\t\t%.2f", asignatura, nota);
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