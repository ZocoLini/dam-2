package EJ1_A2UD2;

import Actores.ActoresParserHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class EJ1_A2UD2
{
    private static final File ACTORES_XML = new File("Actores.xml");
    
    public static void main(String[] args)
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);

        try
        {
            ActoresParserHandler.parseActores(factory.newSAXParser());
        }
        catch (SAXException e)
        {
            throw new RuntimeException(e);
        }
        catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e);
        }
    }
}
