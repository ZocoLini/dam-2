/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASESDATOS;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.*;

public class Alumno implements Serializable
{
    private static final File DATA = new File("alumnos.dat");
    public static final int LONGITUD_RESGISTRO = 150;
    
    private int numero;
    private Nombre nombre;
    private Date fechaNac;
    private Set<String> telefonos;
    private boolean borrado;

    public Alumno()
    {
    }

    public Alumno(Nombre nombre, Date fechaNac, Set<String> telefono, boolean borrado, int numero)
    {
        this.numero = numero;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.telefonos = telefono;
        this.borrado = borrado;
    }

    public Alumno(Nombre nombre, Date fechaNac, Set<String> telefono, boolean borrado)
    {
        this(nombre, fechaNac, telefono, borrado, getNumeroRegistros() + 1);
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

    public Set<String> getTelefonos()
    {
        return telefonos;
    }

    public void setTelefonos(Set<String> telefonos)
    {
        this.telefonos = telefonos;
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
                (telefonos.toString()).length() + 1);

    }
    
    public boolean guardar()
    {
        try (RandomAccessFile raf = new RandomAccessFile(DATA, "rw"))
        {
            raf.seek((long) (numero - 1) * LONGITUD_RESGISTRO);
            raf.writeUTF(String.format("%d %s %s %s %d ", numero, nombre.getNombre(), nombre.getApellido1(),
                    nombre.getApellido2(), fechaNac.getTime()));
            
            StringBuilder stringBuilder = new StringBuilder();
            
            for (var telefono : telefonos)
            {
                stringBuilder.append(telefono).append(" ");
            }

            raf.writeUTF(stringBuilder.toString());
            
            raf.writeUTF(String.format("%b\n", borrado));
        }
        catch (Exception exception)
        {
            return false;
        }
        
        return true;
    }
    
    public static Alumno getAlumno(int numero)
    {
        if (numero < 1 || numero > getNumeroRegistros()) 
        {
            System.out.println("El número de alumno no es válido.");
            return null;
        }
        
        try (RandomAccessFile raf = new RandomAccessFile(DATA, "r"))
        {
            raf.seek((long) (numero - 1) * LONGITUD_RESGISTRO);
            String registro = raf.readUTF();
            String[] campos = registro.split(" ");
            
            int num = Integer.parseInt(campos[0]);
            
            if (num != numero)
            {
                System.out.println("Error en la lectura del fichero. La posición no coincide con el número de alumno.");
                return null;
            }
            
            Nombre nombre = new Nombre(campos[1], campos[2], campos[3]);
            Date fechaNac = new Date(Long.parseLong(campos[4]));

            Set<String> telefonos = new HashSet<>(Arrays.stream(raf.readUTF().split(" ")).toList());
            
            boolean borrado = Boolean.parseBoolean(raf.readUTF().trim());
            
            return new Alumno(nombre, fechaNac, telefonos, borrado, num);
            
        }
        catch (Exception exception)
        {
            return null;
        }
    }
    
    public static int getNumeroRegistros()
    {
        return (int) Math.ceilDiv(DATA.length(), LONGITUD_RESGISTRO);
    }

    @Override
    public String toString()
    {
        return "Alumno{" +
                "numero=" + numero +
                ", nombre=" + nombre +
                ", fechaNac=" + fechaNac +
                ", telefonos=" + telefonos +
                ", borrado=" + borrado +
                '}';
    }
}
