package org.lebastudios.EJ3_A3UD2;

import org.lebastudios.parser.ParserHandlerState;
import org.lebastudios.parser.StateMachineParserHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class NotaParserHandlerState extends ParserHandlerState<Nota>
{
    private String elementActual = "";
    
    public NotaParserHandlerState(StateMachineParserHandler parserHandler, Nota element,
            Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        elementActual = qName;
    }

    @Override
    public void text(String text) throws SAXException
    {
        if (elementActual.equals("alumno"))
        {
            element.alumnos.add(text);
        }
        
        elementActual = "";
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equals("nota"))
        {
            parserHandler.popState();
        }
    }

    @Override
    protected void setAttributes(Attributes attributes)
    {
        element.valor = attributes.getValue("valor");
    }
}
