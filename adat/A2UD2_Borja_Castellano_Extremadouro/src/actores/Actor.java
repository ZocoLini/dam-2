package actores;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class Actor
{
    private int id;
    private String nombre;
    private String sexo;
    private FechaNacimiento fechaNacimiento;

    Actor(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (!qName.equals("Actor")) throw new IllegalArgumentException();

        final var idValue = attributes.getValue("id");
        
        if (idValue != null) 
        {
            id = Integer.parseInt(idValue);
        }
    }

    void characteres(char[] ch, int start, int length) throws SAXException
    {

    }

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
