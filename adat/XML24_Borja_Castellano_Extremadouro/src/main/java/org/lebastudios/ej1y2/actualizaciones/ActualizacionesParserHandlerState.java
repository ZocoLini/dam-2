package org.lebastudios.ej1y2.actualizaciones;

import org.lebastudios.parser.ParserHandlerState;
import org.lebastudios.parser.StateMachineParserHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.List;

public class ActualizacionesParserHandlerState extends ParserHandlerState<List<Entrada>>
{
    public ActualizacionesParserHandlerState(StateMachineParserHandler parserHandler, List<Entrada> element,
            Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equals("Entrada")) 
        {
            Entrada entrada = new Entrada();
            element.add(entrada);
            parserHandler.addState(new EntradaParserHandlerState(parserHandler, entrada, attributes));
        }
    }
}
