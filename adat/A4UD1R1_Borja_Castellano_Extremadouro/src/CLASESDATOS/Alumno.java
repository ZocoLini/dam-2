/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASESDATOS;

import a4ud1_alumno.A4UD1_Alumnos;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
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

    public static Alumno generate() throws ParseException
    {
        System.out.println("Introduzca el nombre del alumno: ");
        String nombre = new Scanner(System.in).nextLine();
        System.out.println("Introduzca el primer apellido del alumno: ");
        String apellido1 = new Scanner(System.in).nextLine();
        System.out.println("Introduzca el segundo apellido del alumno: ");
        String apellido2 = new Scanner(System.in).nextLine();
        System.out.println("Introduzca la fecha de nacimiento del alumno (dd/MM/yyyy): ");
        String fechaNac = new Scanner(System.in).nextLine();
        System.out.println("Introduzca los numeros asociados al alumno (* para salir): ");

        Set<String> telefonos = new HashSet<>();

        boolean exit = false;

        while (!exit)
        {
            String telefono = new Scanner(System.in).nextLine();

            if (telefono.equals("*"))
            {
                exit = true;
                continue;
            }

            if (telefonos.contains(telefono))
            {
                System.out.println("El número de teléfono ya existe.");
                continue;
            }

            telefonos.add(telefono);
        }

        Nombre nombreAlumno = new Nombre(nombre, apellido1, apellido2);
        
        return new Alumno(nombreAlumno, A4UD1_Alumnos.formato.parse(fechaNac), telefonos, false);
    }

    public static int getNumeroRegistros()
    {
        return (int) Math.ceilDiv(DATA.length(), LONGITUD_RESGISTRO);
    }

    public String preattyPrinting()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ALUMNO NUMERO: ").append(numero).append("\n");
        sb.append(nombre.preattyPrinting()).append("\n");
        sb.append("FECHA NACIMIENTO: ").append(A4UD1_Alumnos.formato.format(fechaNac));
        int edad = -1;
        sb.append(" ").append("EDAD: ").append(edad).append("\n");
        sb.append("TELEFONO(S): " );
        telefonos.forEach(tel -> sb.append(tel).append(" "));
        sb.append("\n\n");
        
        NotaAlumno notas = NotaAlumno.getNotaAlumno(numero);
        
        if (notas == null) return sb.toString(); 
        
        sb.append(notas.preattyPrinting());
        return sb.toString();
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
