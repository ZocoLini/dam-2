package EJ1_ARUD2;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class EJ1_ARUD2
{
    private static final File ACTORES_DATA = new File("Actores.xml");
    private static final File ACTORES_MODIFICATION = new File("modificaciones-actores.txt");

    public static void main(String[] args)
    {
        writeModifications(modifyDocument(getDocument()));
    }

    private static Document getDocument()
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        dbf.setIgnoringElementContentWhitespace(true);

        DocumentBuilder db;
        try
        {
            db = dbf.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            System.out.println("Configuracion Exception: " + e.getMessage());
            return null;
        }

        try
        {
            return db.parse(ACTORES_DATA);
        }
        catch (SAXException e)
        {
            System.out.println("SAX EXception: " + e.getMessage());
            return null;
        }
        catch (IOException e)
        {
            System.out.println("IOException: " + e.getMessage());
            return null;
        }
    }

    private static Document modifyDocument(Document document)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACTORES_MODIFICATION)))
        {
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] tokens = line.split(",");

                Element element = document.getElementById(tokens[0]);
                element.getElementsByTagName("DataNacemento").item(0).setTextContent(tokens[1]);
            }
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        document.getDocumentElement().appendChild(createActorNode(document, "A32", "Michael Caine", "14/03/1933",
                "home"));
        
        return document;
    }

    private static Node createActorNode(Document document, String id, String name, String date, String sexo)
    {
        Element actor = document.createElement("Actor");

        Attr attr = document.createAttribute("id");
        attr.setValue(id);
        actor.setAttributeNode(attr);
        actor.setIdAttributeNode(attr, true);
        
        Element nomeElement = document.createElement("Nome");
        nomeElement.setTextContent(name);
        
        Element sexoElement = document.createElement("Sexo");
        sexoElement.setTextContent(sexo);
        
        Element dateElement = document.createElement("DataNacemento");
        dateElement.setTextContent(date);
        dateElement.setAttribute("formato", "dd/mm/aaaa");
        
        actor.appendChild(nomeElement);
        actor.appendChild(sexoElement);
        actor.appendChild(dateElement);
        
        return actor;
    }
    
    private static void writeModifications(Document document)
    {
        TransformerFactory transFact = TransformerFactory.newInstance();

        try
        {
            Transformer transformer = transFact.newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "actores.dtd");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            DOMSource source = new DOMSource(document);
            StreamResult output = new StreamResult(ACTORES_DATA);

            transformer.transform(source, output);
        }
        catch (TransformerConfigurationException e)
        {
            throw new RuntimeException(e);
        }
        catch (TransformerException e)
        {
            throw new RuntimeException(e);
        }
    }
}
