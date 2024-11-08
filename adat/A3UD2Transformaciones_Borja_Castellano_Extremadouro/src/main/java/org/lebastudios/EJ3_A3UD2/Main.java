package org.lebastudios.EJ3_A3UD2;

import org.lebastudios.parser.StateMachineParserHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        TransformerFactory tf = TransformerFactory.newInstance();
        try
        {
            StreamSource data = new StreamSource(Main.class.getResourceAsStream("/Alumnos.xml"));
            StreamSource xslt = new StreamSource(Main.class.getResourceAsStream("/Alumnos.xsl"));

            Transformer transformer = tf.newTransformer(xslt);


            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.newDocument();
            
            DOMResult output = new DOMResult(doc);
            
            transformer.transform(data, output);

            Element nuevoAlumno = doc.createElement("alumno");
            nuevoAlumno.setTextContent("Nuevo Alumno");

            doc.getDocumentElement().getLastChild().appendChild(nuevoAlumno);

            StateMachineParserHandler parser = new StateMachineParserHandler();
            List<Nota> notas = new ArrayList<>();
            ListaNotasParserHandlerState
                    listaNotasParserHandlerState = new ListaNotasParserHandlerState(parser, notas, null);
            parser.addState(listaNotasParserHandlerState);
            
            transformer.transform(new DOMSource(doc), new SAXResult(parser));
            
            System.out.println(notas);
        }
        catch (TransformerConfigurationException e)
        {
            throw new RuntimeException(e);
        }
        catch (TransformerException e)
        {
            throw new RuntimeException(e);
        }
        catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e);
        }
    }
}
