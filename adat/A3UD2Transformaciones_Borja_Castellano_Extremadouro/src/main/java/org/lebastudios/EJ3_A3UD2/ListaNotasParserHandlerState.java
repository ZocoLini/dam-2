package org.lebastudios.EJ3_A3UD2;

import org.lebastudios.parser.ParserHandlerState;
import org.lebastudios.parser.StateMachineParserHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.List;

public class ListaNotasParserHandlerState extends ParserHandlerState<List<Nota>>
{
    public ListaNotasParserHandlerState(StateMachineParserHandler parserHandler, List<Nota> element,
            Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equals("nota"))
        {
            Nota nota = new Nota();
            element.add(nota);
            parserHandler.addState(new NotaParserHandlerState(parserHandler, nota, attributes));
        }
    }
}
