package EJ2_ARUD2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import parser.ParserHandlerState;
import parser.StateMachineParserHandler;

public class LunaParserHandlerState extends ParserHandlerState<Luna>
{
    private String actualElement = "";

    public LunaParserHandlerState(StateMachineParserHandler parserHandler, Luna element,
            Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        actualElement = qName;
    }

    @Override
    public void text(String text) throws SAXException
    {
        if (actualElement.equals("Luas:data"))
        {
            String[] tokens = text.split(" ");
            element.setDate(tokens[0]);
            element.setHora(tokens[1]);
        }
        else
        {
            if (actualElement.equals("Luas:estado"))
            {
                element.setEstado(text);
            }
        }

        actualElement = "";
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equals("item"))
        {
            parserHandler.popState();
        }
    }
}
