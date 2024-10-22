package actores;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: Implementar unan forma recursiva de crear elementos y en el switch, poniendo el nombre del tag, 
//  se cree un objeto de la clase correspondiente y se añada a la lista de actores. Igual usar tantos 
//  handlers como tags haya. MAQUINA DE ESTADOS

public class ActoresParserHandler extends DefaultHandler
{
    private static final File ACTORES_XML = new File("Actores.xml");

    public static List<Actor> parseActores(XMLReader reader) throws SAXException
    {
        try
        {
            final var parserHandler = new ActoresParserHandler();
            reader.setContentHandler(parserHandler);
            reader.parse(new InputSource(new FileInputStream(ACTORES_XML)));
            
            return parserHandler.actores;
        }
        catch (IOException e)
        {
            System.out.println("IO Error al parsear el archivo " + ACTORES_XML.getName());
        }
        
        return new ArrayList<>();
    }
    
    private final ArrayList<Actor> actores = new ArrayList<>();
    private FechaNacimiento fechaNacimiento;
    private boolean settingNombre;
    private boolean settingSex;
    private ActoresParserHandler() {}

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        switch (qName) 
        {
            case "Actor":
                actores.add(new Actor(uri, localName, qName, attributes));
                fechaNacimiento = null;
                break;
            case "Nome":
                settingNombre = true;
                break;
            case "Sexo":
                settingSex = true;
                break;
            case "DataNacemento":
                final var date = new FechaNacimiento(uri, localName, qName, attributes);
                actores.getLast().setFechaNacimiento(date);
                fechaNacimiento = date;
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
            return;
        }
        
        if (settingNombre) 
        {
            actores.getLast().setNombre(String.valueOf(ch).substring(start, start + length));
            settingNombre = false;
            return;
        }
        
        if (settingSex) 
        {
            actores.getLast().setSexo(String.valueOf(ch).substring(start, start + length));
            settingSex = false;
            return;
        }
    }
}
