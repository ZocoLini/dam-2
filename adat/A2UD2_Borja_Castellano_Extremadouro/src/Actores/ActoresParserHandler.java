package Actores;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// TODO: Implementar unan forma recursiva de crear elementos y en el switch, poniendo el nombre del tag, 
//  se cree un objeto de la clase correspondiente y se añada a la lista de actores. Igual usar tantos 
//  handlers como tags haya.

public class ActoresParserHandler extends DefaultHandler
{
    private static final File ACTORES_XML = new File("Actores.xml");

    public static void parseActores(SAXParser parser) throws SAXException
    {
        try
        {
            parser.parse(ACTORES_XML, new ActoresParserHandler());
        }
        catch (IOException e)
        {
            System.out.println("IO Error al parsear el archivo " + ACTORES_XML.getName());
        }
    }
    
    private ArrayList<Actor> actores = new ArrayList<>();
    private FechaNacimiento fechaNacimiento;
    private ActoresParserHandler() {}

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        switch (qName) 
        {
            case "Actor":
                actores.add(new Actor(uri, localName, qName, attributes));
                break;
            case "DataNacimiento":
                fechaNacimiento = new FechaNacimiento(uri, localName, qName, attributes);
                break;
            default:
        }
    }

    
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        if (fechaNacimiento != null) 
        {
            fechaNacimiento.characteres(ch, start, length);
        }
        else
        {
            actores.getLast().characteres(ch, start, length);
        }
    }
}
