package org.lebastudios.ej1y2.actualizaciones;

import org.lebastudios.parser.ParserHandlerState;
import org.lebastudios.parser.StateMachineParserHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RioParserHandlerState extends ParserHandlerState<Rio>
{
    public RioParserHandlerState(StateMachineParserHandler parserHandler, Rio element,
            Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equals("Rio")) 
        {
            parserHandler.popState();
        }
    }

    @Override
    public void text(String text) throws SAXException
    {
        element.setNombre(text);
    }

    @Override
    protected void setAttributes(Attributes attributes)
    {
        element.setCodigo(attributes.getValue("codigo"));
    }
}
