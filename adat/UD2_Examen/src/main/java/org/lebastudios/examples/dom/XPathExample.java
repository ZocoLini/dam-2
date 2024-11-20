package org.lebastudios.examples.dom;

import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class XPathExample
{
    public static void main(String[] args)
    {
        XPathFactory factory = XPathFactory.newDefaultInstance();

        XPath xpath = factory.newXPath();
        
        // Compile an XPath expression
        XPathExpression expression = xpath.compile("/bookstore/book[price>35]/title/text()");
        
        // QNames Examples: 
        expression.evaluate(null, QName);
    }
}
