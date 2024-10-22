package EJ1_A2UD2;

import actores.Actor;
import actores.ActoresParserHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

public class EJ1_A2UD2
{
    public static void main(String[] args)
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try
        {
            XMLReader reader = factory.newSAXParser().getXMLReader();
            reader.setFeature("http://xml.org/sax/features/validation", true);
            List<Actor> actores = ActoresParserHandler.parseActores(reader);
            
            actores.forEach(actor -> System.out.println(actor.preattyPrinting()));
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
