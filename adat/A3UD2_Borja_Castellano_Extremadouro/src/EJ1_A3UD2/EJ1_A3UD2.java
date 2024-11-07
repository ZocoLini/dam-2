package EJ1_A3UD2;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class EJ1_A3UD2
{
    //Especifica el lenguaje utilizado por el parser en el análisis
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    //especifica el espacio de nombres
    static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

    public static void main(String[] args)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setIgnoringComments(true);
        dbf.setValidating(true);
        dbf.setNamespaceAware(true);
        dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

        DocumentBuilder db;
        try
        {
            db = dbf.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            System.out.println("Configuracion Exception: " + e.getMessage());
            return;
        }

        Document document;
        try
        {
            document = db.parse(new File("BibliotecaInformatica.xml"));
        }
        catch (SAXException e)
        {
            System.out.println("SAX EXception: " + e.getMessage());
            return;
        }
        catch (IOException e)
        {
            System.out.println("IOException: " + e.getMessage());
            return;
        }

        // Apartado a
        {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            try
            {
                // Expresión XPath para buscar todos los libros
                XPathExpression expr = xPath.compile("//seccion");
                NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

                // Imprimir los títulos de los libros
                for (int i = 0; i < nodeList.getLength(); i++)
                {
                    printSeccion(nodeList.item(i));
                }
            }
            catch (XPathExpressionException e)
            {
                System.out.println("XPathExpressionException: " + e.getMessage());
            }
        }

        // Apartado b
        {
            List<SeccionCuentaLibros> seccionCuentaLibros = new ArrayList<>();
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            try
            {
                NodeList secciones = (NodeList) xPath.compile("//seccion").evaluate(document, XPathConstants.NODESET);

                for (int i = 0; i < secciones.getLength(); i++)
                {
                    Node seccion = secciones.item(i);
                    if (!Objects.equals(seccion.getNodeName(), "seccion")) throw new IllegalArgumentException();

                    NodeList libros = seccion.getFirstChild().getChildNodes();
                    seccionCuentaLibros.add(new SeccionCuentaLibros(seccion.getAttributes().getNamedItem("nombre").getNodeValue(), libros.getLength()));
                }
            }
            catch (XPathExpressionException e)
            {
                System.out.println("XPathExpressionException: " + e.getMessage());
            }
            
            seccionCuentaLibros.stream()
                    .sorted(Comparator.comparingInt((SeccionCuentaLibros o) -> o.cantidadLibros).reversed().thenComparing(o -> o.nombre))
                    .forEach(seccion -> System.out.println(seccion + "--------------------------------------"));
        }
    }

    public static void printSeccion(Node seccion)
    {
        if (seccion == null)
        {
            System.out.println("Seccion no existente");
            return;
        }
        if (!Objects.equals(seccion.getNodeName(), "seccion")) throw new IllegalArgumentException();

        NodeList libros = seccion.getFirstChild().getChildNodes();

        for (int i = 0; i < libros.getLength(); i++)
        {
            printLibro(libros.item(i));
        }

    }

    private static void printLibro(Node libro)
    {
        if (libro == null)
        {
            System.out.println("Libro no existente");
            return;
        }
        if (!Objects.equals(libro.getNodeName(), "libro")) throw new IllegalArgumentException();

        System.out.println("ID: " + libro.getAttributes().getNamedItem("ID").getNodeValue());
        System.out.println("ISBN: " + libro.getAttributes().getNamedItem("isbn").getNodeValue());
        System.out.println("Titulo: " + libro.getAttributes().getNamedItem("titulo").getNodeValue());
        System.out.println(
                "Numero de paginas: " + libro.getAttributes().getNamedItem("numero_de_paginas").getNodeValue());

        System.out.print("Autores: [");
        for (int i = 0; i < libro.getFirstChild().getChildNodes().getLength(); i++)
        {
            System.out.print(libro.getFirstChild().getChildNodes().item(i).getTextContent());
        }
        System.out.println("]");

        System.out.println("Fecha de Edicion: " + libro.getChildNodes().item(1).getTextContent());
        System.out.println("Editorial: " + libro.getChildNodes().item(2).getTextContent());
        System.out.println("Precio: " + libro.getChildNodes().item(3).getTextContent());

        Node copias = libro.getChildNodes().item(4);
        System.out.println("Copias: ");
        for (int i = 0; i < copias.getChildNodes().getLength(); i++)
        {
            printCopia(copias.getChildNodes().item(i));
        }

        System.out.println();

        System.out.println("---------------------------------------------------------");
    }

    private static void printCopia(Node copia)
    {
        if (!Objects.equals(copia.getNodeName(), "copia")) throw new IllegalArgumentException();

        System.out.print("{Numero de copia:" + copia.getAttributes().getNamedItem("numero").getNodeValue()
                + ", Estado: " + copia.getAttributes().getNamedItem("estado").getNodeValue() + "}");
    }

    private record SeccionCuentaLibros(String nombre, int cantidadLibros)
    {
        @Override
        public String toString()
        {
            return "Seccion: " + nombre + "\nNumero de libros: " + cantidadLibros + "\n";
        }
    }
}
