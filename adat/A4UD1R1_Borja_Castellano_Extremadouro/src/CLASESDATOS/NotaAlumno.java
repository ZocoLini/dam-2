/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CLASESDATOS;

/**
 * @author usuario
 */

import a4ud1_alumno.CustomObjectOutputStream;

import java.io.*;
import java.util.ArrayList;

public class NotaAlumno implements Serializable
{
    private static final File DATA = new File("NoatsAlumnos.dat");
    
    @Serial private static final long serialVersionUID = 42L;
    private int numero;
    private ArrayList<NotaModulo> notas;


    public NotaAlumno()
    {
    }

    public NotaAlumno(int numero, ArrayList<NotaModulo> notas)
    {
        this.numero = numero;
        this.notas = notas;
    }

    public int getNumero()
    {
        return numero;
    }

    public void setNumero(int numero)
    {
        this.numero = numero;
    }

    public ArrayList<NotaModulo> getNotas()
    {
        return notas;
    }

    public void setNotas(ArrayList<NotaModulo> notas)
    {
        this.notas = notas;
    }


    public static NotaAlumno getNotaAlumno(int numero)
    {
        if (!DATA.exists())
        {
            return null;
        }
        
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(DATA)))
        {
            while (true)
            {
                NotaAlumno notaAlumno = (NotaAlumno) input.readObject();
                if (notaAlumno.getNumero() == numero)
                {
                    return notaAlumno;
                }
            }
        }
        catch (EOFException eofException)
        {
            return null;
        }
        catch (IOException | ClassNotFoundException exception)
        {
            return null;
        }
    }
    
    public void guardar()
    {
        ObjectOutputStream outputStream = null;
        try
        {
            outputStream = DATA.exists()
                    ? new CustomObjectOutputStream(new FileOutputStream(DATA, true))
                    : new ObjectOutputStream(new FileOutputStream(DATA));
            
            outputStream.writeObject(this);
        }
        catch (Exception exception)
        {
            System.out.println("Error al guardar la nota del alumno.");
        }
        finally
        {
            if (outputStream != null)
            {
                try
                {
                    outputStream.close();
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static NotaAlumno generate(Alumno alumno)
    {
        System.out.println("Introduce * para salir a la hora de indicar el nombre del modulo.");
        boolean exit = false;

        ArrayList<NotaModulo> notas = new ArrayList<>();

        while (!exit)
        {
            NotaModulo notaModulo = NotaModulo.generate();

            if (notaModulo.isLast())
            {
                exit = true;
                continue;
            }

            notas.add(notaModulo);
        }

        return new NotaAlumno(alumno.getNumero(), notas);
    }

    public String preattyPrinting()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("MODULO\t\t\t\tNOTA\n");
        sb.append("--------------------------------------------------\n");
        double notaMedia = 0;
        
        for (var nota : notas)
        {
            sb.append(nota.preattyPrinting()).append("\n");
            notaMedia += nota.getNota();
        }
        
        notaMedia /= notas.size();
        
        sb.append("NOTA MEDIA\t\t\t").append(String.format("%.2f", notaMedia));
        return sb.toString();
    }
    
    @Override
    public String toString()
    {
        return "NotaAlumno{" +
                "numero=" + numero +
                ", notas=" + notas +
                '}';
    }
}
