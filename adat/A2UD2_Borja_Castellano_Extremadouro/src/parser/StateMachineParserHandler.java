package parser;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;

public class StateMachineParserHandler extends DefaultHandler
{
    private final Stack<ParserHandlerState<?>> parseHandlers = new Stack<>();

    public void addState(ParserHandlerState<?> newState)
    {
        parseHandlers.push(newState);
    }
    
    public void popState()
    {
        parseHandlers.pop();
    }
    
    public void parse(XMLReader reader, File file, ParserHandlerState<?> initialState) throws SAXException
    {
        try
        {
            parseHandlers.push(initialState);
            reader.setContentHandler(this);
            reader.parse(new InputSource(new FileInputStream(file)));
        }
        catch (IOException e)
        {
            System.out.println("IO Error al parsear el archivo " + file.getName());
        }
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        parseHandlers.peek().startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        parseHandlers.peek().endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        parseHandlers.peek().text(String.valueOf(ch, start, length));
    }
}
