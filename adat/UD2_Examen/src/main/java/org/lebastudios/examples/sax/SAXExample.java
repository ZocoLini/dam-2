package org.lebastudios.examples.sax;

import org.lebastudios.parser.StateMachineParserHandler;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;

public class SAXExample
{
    public static void main(String[] args)
    {
        SAXParserFactory parser = SAXParserFactory.newDefaultInstance();

        SAXParser saxParser = parser.newSAXParser();

        XMLReader reader = saxParser.getXMLReader();
        // Validación con DTD
        reader.setFeature("http://xml.org/sax/features/validation", true);
        // Validar con XSD
        reader.setFeature("http://apache.org/xml/features/validation/schema", true);    
        // Error Handler
        reader.setErrorHandler(null);
        // Comenzar a parsear
        StateMachineParserHandler parserHandler = new StateMachineParserHandler();
        parserHandler.parse(reader, new FileInputStream("input.xml"), null);
    }
}
