package EJ2_ARUD2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import parser.ParserHandlerState;
import parser.StateMachineParserHandler;

public class LunasParserHandlerState extends ParserHandlerState<ListaLunas>
{
    private String actualElement = "";
    
    public LunasParserHandlerState(StateMachineParserHandler parserHandler, ListaLunas element,
            Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        actualElement = qName;
        
        if (qName.equals("item"))
        {
            Luna luna = new Luna();
            element.getLunas().add(luna);
            parserHandler.addState(new LunaParserHandlerState(parserHandler, luna, attributes));
        }
    }

    @Override
    public void text(String text) throws SAXException
    {
        switch (actualElement)
        {
            case "description" -> element.setDescripcion(text);
            case "link" -> element.setServicio(text.trim());
        }
        
        actualElement = "";
    }
}
