package EJ1_A2UD2;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import parser.StateMachineParserHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class EJ1_A2UD2
{
    public static void main(String[] args)
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try
        {
            XMLReader reader = factory.newSAXParser().getXMLReader();
            reader.setFeature("http://xml.org/sax/features/validation", true);

            final var stateMachineParserHandler = new StateMachineParserHandler();
            final var actoresParser = new ActoresParseHandlerState(stateMachineParserHandler, new ArrayList<>(), null);

            stateMachineParserHandler.parse(reader, new FileInputStream("Actores.xml"), actoresParser);
            
            actoresParser.get().forEach(actor -> System.out.println(actor.preattyPrinting()));
        }
        catch (SAXException | ParserConfigurationException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
