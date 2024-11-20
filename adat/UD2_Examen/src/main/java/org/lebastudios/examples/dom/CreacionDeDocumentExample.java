package org.lebastudios.examples.dom;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class CreacionDeDocumentExample
{
    //Especifica el lenguaje utilizado por el parser en el análisis
    private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    //especifica el espacio de nombres
    private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    
    public static void main(String[] args) throws ParserConfigurationException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Ignore comments: 
        factory.setIgnoringComments(true);
        // Ignore element content whitespace: 
        factory.setIgnoringElementContentWhitespace(true);
        // Validar contra el DTD:
        factory.setValidating(true);
        // Validar con xsd:
        factory.setNamespaceAware(true);
        factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

        // Validar con xsd:
        // <Tag xmlns:xsi=http://www.w3.org/2001/XMLSchema-instance
        //          xsi:noNamespaceSchemaLocation="esquema.xsd"
        
        DocumentBuilder builder = factory.newDocumentBuilder();
        // Error handler para el parser:
        builder.setErrorHandler(null);
        // Parsear
        builder.parse("file.xml");
        // New Document
        Document document = builder.newDocument();
        
        // DOMImplementation
        DOMImplementation dom = builder.getDOMImplementation();
        
        Document document = dom.createDocument(null, "root", null);
        document.setXmlStandalone(true);
        document.setXmlVersion("1.0");
    }
}
