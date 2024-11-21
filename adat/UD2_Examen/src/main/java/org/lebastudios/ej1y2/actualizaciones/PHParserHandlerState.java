package org.lebastudios.ej1y2.actualizaciones;

import org.lebastudios.parser.ParserHandlerState;
import org.lebastudios.parser.StateMachineParserHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PHParserHandlerState extends ParserHandlerState<PH>
{
    public PHParserHandlerState(StateMachineParserHandler parserHandler, PH element,
            Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }

    @Override
    protected void setAttributes(Attributes attributes)
    {
        element.setTipo(attributes.getValue("tipo"));
        element.setPorcentaje(Float.parseFloat(attributes.getValue("porcentaje")));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equals("pH")) 
        {
            parserHandler.popState();
        }
    }
}
