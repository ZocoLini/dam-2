package org.lebastudios.examples.dom;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.util.JAXBSource;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class TransformExample
{
    public static void main(String[] args)
    {
        // Parametro de newInstance es la hoja XSLT
        TransformerFactory factory = TransformerFactory.newDefaultInstance();
        // Indentación de 2 espacios
        factory.setAttribute("indent-number", 2);
        
        // Transformar el documento XML con la hoja XSLT
        Transformer transformer = factory.newTransformer(new StreamSource("hoja.xsl"));
        
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "hola.dtd");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        
        // Transformar el documento XML de entrada en el documento XML de salida
        // Source es el documento XML de entrada y Result es el documento XML de salida
        Source source1 = new StreamSource("documento.xml");
        Source source2 = new DOMSource(null);
        Source source3 = new SAXSource(null);
        Source source4 = new JAXBSource((JAXBContext) null, null);

        Result result1 = new StreamResult("resultado.xml");
        Result result2 = new DOMResult(null);
        Result result3 = new SAXResult(null);
        Result result4 = new JAXBSource((JAXBContext) null, null);
        
        transformer.transform(new StreamSource("documento.xml"), new StreamResult("resultado.xml"));
    }
}
