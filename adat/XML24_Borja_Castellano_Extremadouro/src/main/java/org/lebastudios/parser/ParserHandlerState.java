package org.lebastudios.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class ParserHandlerState<T>
{
    protected StateMachineParserHandler parserHandler;
    protected T element;
    
    public ParserHandlerState(StateMachineParserHandler parserHandler, T element, Attributes attributes)
    {
        this.parserHandler = parserHandler;
        this.element = element;
        
        setAttributes(attributes);
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {}

    public void endElement(String uri, String localName, String qName) throws SAXException {}

    public void text(String text) throws SAXException {}

    public final T get()
    {
        return element;
    }
    
    protected void setAttributes(Attributes attributes) {}
}
