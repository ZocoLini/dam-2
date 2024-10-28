package actores;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import parser.ParserHandlerState;
import parser.StateMachineParserHandler;

public class ActorParseHandlerState extends ParserHandlerState<Actor>
{
    private String actualElement;
    
    public ActorParseHandlerState(StateMachineParserHandler parserHandler, Actor element, Attributes attributes)
    {
        super(parserHandler, element, attributes);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equals("DataNacemento"))
        {
            FechaNacimiento f = new FechaNacimiento();
            element.setFechaNacimiento(f);
            parserHandler.addState(new FechaNacimientoParseHandlerState(parserHandler, f, attributes));
        }
        else
        {
            actualElement = qName;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equals("Actor")) 
        {
            parserHandler.popState();
        }
    }

    @Override
    public void text(String text) throws SAXException
    {
        switch (actualElement)
        {
            case "Nome" -> element.setNombre(text);
            case "Sexo" -> element.setSexo(text);
        }
    }

    @Override
    public void setAttributes(Attributes attributes)
    {
        element.setId(Integer.parseInt(attributes.getValue("id")));
    }
}
