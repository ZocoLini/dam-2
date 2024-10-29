package EJ2_ARUD2;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import parser.StateMachineParserHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EJ2_ARUD2
{
    public static void main(String[] args)
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("FasesLunas.xml")))
        {
            XMLReader reader = factory.newSAXParser().getXMLReader();
            final var stateMachineParserHandler = new StateMachineParserHandler();
            final var lunasParser = new LunasParserHandlerState(stateMachineParserHandler, new ListaLunas(), null);
            stateMachineParserHandler.parse(reader, new FileInputStream("lunas.xml"), lunasParser);
            writer.write(lunasParser.get().toXMLFormat());
        }
        catch (SAXException | ParserConfigurationException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
