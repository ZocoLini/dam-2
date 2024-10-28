package actores;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import parser.ParserHandlerState;
import parser.StateMachineParserHandler;

public class FechaNacimientoParseHandlerState extends ParserHandlerState<FechaNacimiento>
{
    public FechaNacimientoParseHandlerState(StateMachineParserHandler parserHandler, FechaNacimiento elemet, Attributes atributos)
    {
        super(parserHandler, elemet, atributos);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equals("DataNacemento")) 
        {
            parserHandler.popState();
        }
    }

    @Override
    public void text(String text) throws SAXException
    {
        element.setFecha(text);
    }
    
    @Override
    public void setAttributes(Attributes attributes)
    {
        element.setFormat(attributes.getValue("formato"));
    }
}
