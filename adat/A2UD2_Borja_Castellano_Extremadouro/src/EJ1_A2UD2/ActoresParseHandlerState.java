package EJ1_A2UD2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import parser.ParserHandlerState;
import parser.StateMachineParserHandler;

import java.util.List;

public class ActoresParseHandlerState extends ParserHandlerState<List<Actor>>
{
    public ActoresParseHandlerState(StateMachineParserHandler parserHandler, List<Actor> actores, Attributes attributes)
    {
        super(parserHandler, actores, attributes);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equals("Actor")) 
        {
            Actor newActor = new Actor();
            element.add(newActor);
            final var actorParseHandlerState = new ActorParseHandlerState(parserHandler, newActor, attributes);
            parserHandler.addState(actorParseHandlerState);
        }
    }
}
