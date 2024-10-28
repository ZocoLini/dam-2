package EJ3_A2UD2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import parser.ParserHandlerState;
import parser.StateMachineParserHandler;

import java.time.format.DateTimeFormatter;

public class ItemParseHandlerState extends ParserHandlerState<Item>
{
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;

    private String actualElement = "";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        actualElement = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equals("item")) parserHandler.popState();
    }

    @Override
    public void text(String text) throws SAXException
    {
        switch (actualElement)
        {
            case "title" -> element.setTitular(text);
            case "pubDate" -> element.setPubDate(text);
            case "dc:creator" -> element.setAutor(text);
            case "description" -> element.setDescripcion(text);
            case "category" -> element.getCategorias().add(text);
        }
        
        actualElement = "";
    }

    public ItemParseHandlerState(StateMachineParserHandler parserHandler, Item element, Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }
}
