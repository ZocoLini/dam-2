package actores;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class Actor
{
    private int id;
    private String nombre;
    private String sexo;
    private FechaNacimiento fechaNacimiento;

    void setFechaNacimiento(FechaNacimiento fechaNacimiento)
    {
        this.fechaNacimiento = fechaNacimiento;
    }

    void setSexo(String sexo)
    {
        this.sexo = sexo;
    }

    void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String preattyPrinting()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("id:").append(id).append("\n")
                .append("Nome:").append(nombre).append("\n")
                .append("Sexo:").append(sexo).append("\n")
                .append("DataNacemento:").append(fechaNacimiento).append("\n");
        
        return sb.toString();
    }
}
