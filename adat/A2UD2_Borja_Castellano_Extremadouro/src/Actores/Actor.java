package Actores;

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
        
    }
    
    void characteres(char[] ch, int start, int length) throws SAXException
    {
        
    }
    
    void setFechaNacimiento(FechaNacimiento fechaNacimiento)
    {
        this.fechaNacimiento = fechaNacimiento;
    }
}
