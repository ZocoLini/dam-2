package EJ2_A1UD2;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class EJ2_A1UD2
{
    private static final File ACTORES = new File("Actores.xml");
    private static final File SALIDA = new File("output.txt");
    
    public static void main(String[] args)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);


        DocumentBuilder db;
        try
        {
            db = dbf.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            System.out.println("Configuracion no valida manin: " + e.getMessage());
            return;
        }

        Document document;
        try
        {
            document = db.parse(ACTORES);
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
        
        try (FileOutputStream writer = new FileOutputStream(SALIDA))
        {
            writer.write(("Actores\n").getBytes());
            for (int i = 0; i < document.getDocumentElement().getChildNodes().getLength(); i++)
            {
                writer.write(("---------------------------------------\n").getBytes());
                printActor(writer, document.getDocumentElement().getChildNodes().item(i));
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
    }
    
    private static void printActor(OutputStream writer, Node node)
    {
        if (!node.getNodeName().equals("Actor")) 
        {
            throw new IllegalArgumentException("No metiste un actor manin");
        }
        
        try
        {
            writer.write(("id: " + node.getAttributes().getNamedItem("id").getNodeValue() + "\n").getBytes());
            
            for (int i = 0; i < node.getChildNodes().getLength(); i++) 
            {
                Node childNode = node.getChildNodes().item(i);
                
                writer.write((childNode.getNodeName() + ":" + childNode.getTextContent() + "\n").getBytes());
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
