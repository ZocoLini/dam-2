/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASESDATOS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Alumno implements Serializable
{
    private int numero;
    private Nombre nombre;
    Date fechaNac;
    ArrayList<String> telefono;
    boolean borrado;

    public Alumno()
    {
    }


    public Alumno(Nombre nombre, Date fechaNac, ArrayList<String> telefono, boolean borrado)
    {

        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.telefono = telefono;
        this.borrado = borrado;
    }


    public int getNumero()
    {
        return numero;
    }

    public void setNumero(int numero)
    {
        this.numero = numero;
    }

    public Nombre getNombre()
    {
        return nombre;
    }

    public void setNombre(Nombre nombre)
    {
        this.nombre = nombre;
    }

    public Date getFechaNac()
    {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac)
    {
        this.fechaNac = fechaNac;
    }

    public ArrayList<String> getTelefono()
    {
        return telefono;
    }

    public void setTelefono(ArrayList<String> telefono)
    {
        this.telefono = telefono;
    }

    public boolean isBorrado()
    {
        return borrado;
    }

    public void setBorrado(boolean borrado)
    {
        this.borrado = borrado;
    }


    public long LongitudRegistro()
    {
        //Date en Internet dice que ocupa 12 bytes? . Vamos a suponer esto

        return (4 + nombre.getNombre().length() + nombre.getApellido1().length() + nombre.getApellido2().length() + 12 +
                (telefono.toString()).length() + 1);

    }
}
