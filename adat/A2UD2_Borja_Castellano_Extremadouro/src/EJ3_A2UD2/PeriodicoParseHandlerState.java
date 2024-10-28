package EJ3_A2UD2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import parser.ParserHandlerState;
import parser.StateMachineParserHandler;

public class PeriodicoParseHandlerState extends ParserHandlerState<Periodico>
{
    private String actualElement;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equals("item"))
        {
            final var item = new Item();
            element.getItems().add(item);
            final var itemParseHandler = new ItemParseHandlerState(parserHandler, item, attributes);
            parserHandler.addState(itemParseHandler);
            return;
        }
        
        actualElement = qName;
    }

    @Override
    public void text(String text) throws SAXException
    {
        switch (actualElement)
        {
            case "title" -> element.setTitular(text);
            case "description" -> element.setDescripcion(text);
        }
        
        actualElement = "";
    }

    public PeriodicoParseHandlerState(StateMachineParserHandler parserHandler, Periodico element, Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }
}
