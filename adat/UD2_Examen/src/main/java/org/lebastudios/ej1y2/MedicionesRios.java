package org.lebastudios.ej1y2;

import org.lebastudios.ej1y2.actualizaciones.ActualizacionesParserHandlerState;
import org.lebastudios.ej1y2.actualizaciones.Entrada;
import org.lebastudios.parser.StateMachineParserHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicionesRios
{
    Document medicionesRiosXML;

    public MedicionesRios(InputStream stream) throws IOException, SAXException
    {
        medicionesRiosXML = cargarDOMMedicionesRios(stream);
    }

    public Document cargarDOMMedicionesRios(InputStream stream) throws IOException, SAXException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setValidating(true);

        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            System.out.println("Error al crear el DocumentBuilder. Confiruacion erronea: " + e.getMessage());
            return null;
        }

        builder.setErrorHandler(new MedicionesRiosParserErrorHandler());
        return builder.parse(stream);
    }

    public boolean existeMedicion(String rioID, String fecha)
    {
        Node resultadoXPath = obtenerNodoConXpath(
                String.format("/Programa/Rios/Rio[@codigo='%s']/Medicion[@fecha = '%s']", rioID, fecha)
        );

        return resultadoXPath != null;
    }

    public void actualizarConArchivo(File actualizacionesXML) throws IOException
    {
        try
        {
            SAXParserFactory parser = SAXParserFactory.newDefaultInstance();
            SAXParser saxParser = parser.newSAXParser();

            XMLReader reader = saxParser.getXMLReader();

            // Validar con XSD
            reader.setFeature("http://xml.org/sax/features/namespaces", true);
            reader.setFeature("http://apache.org/xml/features/validation/schema", true);

            // Error Handler
            reader.setErrorHandler(new ActualizacionesParserErrorHandler());
            // Comenzar a parsear
            StateMachineParserHandler parserHandler = new StateMachineParserHandler();
            List<Entrada> entradas = new ArrayList<>();

            ActualizacionesParserHandlerState actualizacionesParserHandlerState =
                    new ActualizacionesParserHandlerState(parserHandler, entradas, null);

            parserHandler.parse(reader, new FileInputStream(actualizacionesXML), actualizacionesParserHandlerState);
            
            actualizarConEntradas(entradas);
        }
        catch (SAXNotRecognizedException | SAXNotSupportedException e)
        {
            System.out.println("No deberia suceder nunca ya que a features usadas son siempre validas");
        }
        catch (ParserConfigurationException e)
        {
            System.out.println("La configuraciuon del parser es siempre correcta");
        }
        catch (SAXException e)
        {
            System.out.println("Debe parsear sin ningun problema el archivo ya que esta validado con un XSD");
        }
    }
    
    // Recibiendo una lista de entradas obtenidas del archivo Actualizaciones.xml realizamos las operaciones 
    // indicadas para cada una
    public void actualizarConEntradas(List<Entrada> entradas)
    {
        entradas.forEach(entrada -> 
        {
            // Buscamos el rio indicado en la entrada actual
            Node rio = obtenerNodoConXpath(
                    String.format("//Rio[@codigo = '%s']", entrada.getRio().getCodigo())
            );
            
            // Si no existe tal rio lo añaimos a el junto a la entrada
            if (rio == null) 
            {
                addNuevaMedicionYRio(entrada);
                return;
            }
            
            // Si el rio existe llegamos aqui y lo que toca verificar es si para dicha fecha ya existe una medicion
            Node medicionAModificar = null;
            
            for (int i = 0; i < rio.getChildNodes().getLength(); i++) 
            {
                Node medicion = rio.getChildNodes().item(i);
                
                if (medicion.getAttributes().getNamedItem("fecha").getNodeValue()
                        .equals(entrada.getFechaFormatoMedicionesRios())) 
                {
                    medicionAModificar = medicion;
                    break;
                }
            }
            
            // Dependiendo de si existe o no la medicion la modificamos o la creamos respectivamente
            if (medicionAModificar != null) 
            {
                modificarRioModificandoMedicion(entrada, medicionAModificar);
            }
            else
            {
                modificarRioAnhadiendoMedicion(entrada, rio);
            }
        });
    }

    // Añadir el rio con la correspondiente medicion dada una entrada del archivo Actualizaciones.xml
    private void addNuevaMedicionYRio(Entrada entrada)
    {
        Element medicion = crearMedicion(entrada);
        
        Element rio = medicionesRiosXML.createElement("Rio");
        rio.setAttribute("codigo", entrada.getRio().getCodigo());
        rio.setAttribute("nombre", entrada.getRio().getNombre());
        
        rio.appendChild(medicion);
        
        medicionesRiosXML.getDocumentElement().getElementsByTagName("Rios").item(0).appendChild(rio);
        System.out.println("Se ha añadido un nuevo Río con codigo " + entrada.getRio().getCodigo());
    }
    
    // Añadimos una nueva medicion creada a partir de una entrada del archivo Actualizaciones.xml a un rio dado
    private void modificarRioAnhadiendoMedicion(Entrada entrada, Node rio)
    {
        Node medicion = crearMedicion(entrada);
        rio.appendChild(medicion);
        
        System.out.println("Se ha añadido una medicion al rio " + entrada.getRio().getCodigo() + " con fecha del " 
                + entrada.getFechaFormatoMedicionesRios());
    }
    
    // Modifica una medicion de un rio existente con los valores de una entrada del archivo Actualizaciones.xml
    private void modificarRioModificandoMedicion(Entrada entrada, Node medicion)
    {
        Element elementoMedicion = (Element) medicion;
        
        elementoMedicion.setAttribute("calidad", entrada.getDatos().getCalidad());
        
        for (int i = 0; i < elementoMedicion.getChildNodes().getLength(); i++) 
        {
            Node nodo = elementoMedicion.getChildNodes().item(i);
            
            switch (nodo.getNodeName())
            {
                case "pH":
                    Element ph = (Element) nodo;
                    ph.setAttribute("tipo", entrada.getDatos().getPh().getTipo());
                    ph.setTextContent(String.valueOf(entrada.getDatos().getPh().getPorcentaje()));
                    break;
                case "Oxigeno":
                    Element oxigeno = (Element) nodo;
                    oxigeno.setTextContent(String.valueOf(entrada.getDatos().getOxigeno()));
                    break;
                case "Temperatura":
                    Element temperatura = (Element) nodo;
                    temperatura.setTextContent(String.valueOf(entrada.getDatos().getTemperatura()));
                    break;
            }
        }
        
        System.out.println("Se ha modificado una medicion en el rio " + entrada.getRio().getCodigo() + " con fecha " +
                "del " + entrada.getFechaFormatoMedicionesRios());
    }
    
    // Crea un elemento medicion a partir de una entrada del archivo Actualizaciones.xml
    private Element crearMedicion(Entrada entrada)
    {
        Element medicion = medicionesRiosXML.createElement("Medicion");
        
        medicion.setAttribute("fecha", entrada.getFechaFormatoMedicionesRios());
        medicion.setAttribute("calidad", entrada.getDatos().getCalidad());
        
        Element ph = medicionesRiosXML.createElement("pH");
        ph.setAttribute("tipo", entrada.getDatos().getPh().getTipo());
        ph.setTextContent(String.valueOf(entrada.getDatos().getPh().getPorcentaje()));
        
        Element oxigeno = medicionesRiosXML.createElement("Oxigeno");
        oxigeno.setTextContent(String.valueOf(entrada.getDatos().getOxigeno()));
        
        Element temperatura = medicionesRiosXML.createElement("Temperatura");
        temperatura.setTextContent(String.valueOf(entrada.getDatos().getTemperatura()));
        
        medicion.appendChild(ph);
        medicion.appendChild(oxigeno);
        medicion.appendChild(temperatura);
        
        return medicion;
    }
    
    // Dado una expresion XPath devolvemos el nodo que se encuentre al evaluar
    private Node obtenerNodoConXpath(String xpathString)
    {
        XPath xpath = XPathFactory.newDefaultInstance().newXPath();

        XPathExpression expression;
        try
        {
            expression = xpath.compile(xpathString);
        }
        catch (XPathExpressionException e)
        {
            System.out.println("Error al compilar la expresion XPath");
            return null;
        }

        try
        {
            return  (Node) expression.evaluate(this.medicionesRiosXML, XPathConstants.NODE);
        }
        catch (XPathExpressionException e)
        {
            System.out.println("Error al evaluar la expresion XPath");
            return null;
        }
    }
    
    // Simplemente usamos un transformer para escribir a un fichero el DOM que este objeto tiene cargado en memoria
    public void escribirEnFichero(File file)
    {
        TransformerFactory factory = TransformerFactory.newDefaultInstance();
        factory.setAttribute("indent-number", 2);

        try
        {
            Transformer transformer = factory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            transformer.transform(new DOMSource(medicionesRiosXML), new StreamResult(file));
        }
        catch (TransformerConfigurationException e)
        {
            System.out.println("No deberia haber un error de configuracion del transformer.");
        }
        catch (TransformerException e)
        {
            System.out.println("No deberia suceder ningun error irrecuperable al hacer a transformacion");
        }
    }
    
    // Los diferentes error handlers que, en caso de necesitar realizar alguna operacion por errores en la validacion 
    // pueden ser facilmente modificados
    
    private static class ActualizacionesParserErrorHandler implements ErrorHandler
    {

        @Override
        public void warning(SAXParseException exception) throws SAXException
        {
            System.out.println("Warning: " + exception.getMessage());
            throw exception;
        }

        @Override
        public void error(SAXParseException exception) throws SAXException
        {
            System.out.println("Error: " + exception.getMessage());
            throw exception;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException
        {
            System.out.println("Fatal: " + exception.getMessage());
            throw exception;
        }
    }
    
    private static class MedicionesRiosParserErrorHandler implements ErrorHandler
    {

        @Override
        public void warning(SAXParseException exception) throws SAXException
        {
            System.out.println("Warning: " + exception.getMessage());
            throw exception;
        }

        @Override
        public void error(SAXParseException exception) throws SAXException
        {
            System.out.println("Error: " + exception.getMessage());
            throw exception;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException
        {
            System.out.println("Fatal: " + exception.getMessage());
            throw exception;
        }
    }
}
