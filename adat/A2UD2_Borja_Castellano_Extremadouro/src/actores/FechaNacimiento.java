package actores;

import org.xml.sax.Attributes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class FechaNacimiento
{
    private final DateFormat format;
    private String fecha;
    
    FechaNacimiento(String uri, String localName, String qName, Attributes attributes)
    {
        if (!qName.equals("DataNacemento")) throw new IllegalArgumentException();
        
        this.format = new SimpleDateFormat(attributes.getValue("formato"));
    }
    
    void characteres(char[] ch, int start, int length)
    {
        fecha = String.valueOf(ch).substring(start, start + length);
    }

    @Override
    public String toString()
    {
        return fecha;
    }
}
