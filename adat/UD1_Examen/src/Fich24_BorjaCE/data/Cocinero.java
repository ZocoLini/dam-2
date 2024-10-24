package Fich24_BorjaCE.data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Cocinero implements Serializable
{
    @Serial private static final long serialVersionUID = 100L;

    private final int codigo; // Debe ser generado
    private final String apodo; // Unico
    private final String nombreCompleto;
    private final LocalDate fechaNacimiento;
    private final List<String> recetas;

    public Cocinero(int codigo, String apodo, String nombreCompleto, LocalDate fechaNacimiento, List<String> recetas)
    {
        this.codigo = codigo;
        this.apodo = apodo;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.recetas = recetas;
    }
    
    public int getCodigo()
    {
        return codigo;
    }

    public int getEdad()
    {
        return fechaNacimiento.until(LocalDate.now()).getYears();
    }

    public String getApodo()
    {
        return apodo;
    }

    public String getNombreCompleto()
    {
        return nombreCompleto;
    }

    // Devuelve un string con un formato de impresion de datos estetico visualmente
    public String preattyString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------\n")
          .append("Cocinero codigo ").append(codigo).append("\n")
          .append("--------------------------------------\n")
                .append("Nombre: ").append(nombreCompleto).append("  Apodo: ").append(apodo).append("\n")
                .append("Fecha de nacimiento: ").append(fechaNacimiento).append("\n")
                .append("Recetas: [");
        
        recetas.forEach(receta -> sb.append(receta).append(","));
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]\n");
        sb.append("--------------------------------------");
        
        return sb.toString();
    }
}
