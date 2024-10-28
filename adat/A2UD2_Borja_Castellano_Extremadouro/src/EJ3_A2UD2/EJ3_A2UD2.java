package EJ3_A2UD2;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import parser.StateMachineParserHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;

public class EJ3_A2UD2
{
    public static void main(String[] args)
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try
        {
            XMLReader reader = factory.newSAXParser().getXMLReader();

            final var stateMachineParserHandler = new StateMachineParserHandler();
            final var periodicoParser = new PeriodicoParseHandlerState(stateMachineParserHandler, new Periodico(), null);

            stateMachineParserHandler.parse(reader, new FileInputStream("noticias.xml"), periodicoParser);

            System.out.println(periodicoParser.get().preattyPrinting());
        }
        catch (SAXException | ParserConfigurationException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
