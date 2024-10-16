package EJ1_A1UD2;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class EJ1_A1UD2
{
    private static final File ACTORES = new File("Actores.xml");
    
    public static void main(String[] args)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        dbf.setNamespaceAware(true);
        dbf.setIgnoringComments(false);
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

        System.out.println("Codificacion: " + document.getXmlEncoding());
        System.out.println("DTD asociado: " + document.getDoctype());
        Node raiz = document.getDocumentElement();
        System.out.println("El elemento raíz se llama " + raiz.getNodeName());
        System.out.println("El valor del elemento raíz es " + raiz.getNodeValue());
        System.out.println("El número de hijos del elemento raíz es " + raiz.getChildNodes().getLength());

        System.out.println("Texto de Raíz: " + raiz.getTextContent());

        System.out.println("Texto de los hijos: ");
        for (int i = 0; i < raiz.getChildNodes().getLength(); i++) 
        {
            Node node = raiz.getChildNodes().item(i);
            System.out.println("    Contenido del hijo: " + node.getTextContent());
        }

        Node nodo=raiz.getFirstChild().getNextSibling();
        System.out.println(
                "Nobre nodo:" + nodo.getNodeName() 
                + " Valor del nodo:" + nodo.getNodeValue()
        );

        System.out.println("Nombre nodo: " + nodo.getNodeName() + " Valor del nodo: " + nodo.getNodeValue());
        System.out.println("<id>" + nodo.getAttributes().getNamedItem("id"));
        for (int i = 0; i < nodo.getChildNodes().getLength(); i++) 
        {
            Node actualNode = nodo.getChildNodes().item(i);
            System.out.println(
                    "<" + actualNode.getNodeName() + ">: " 
                    + actualNode.getTextContent()
            );

        }
        
        Node otroNodo = nodo.getNextSibling();
        System.out.println("Data nodo: " + otroNodo.getNodeName());
        
        Node otroNodo2 = nodo.getPreviousSibling();
        System.out.println("Data nodo: " + otroNodo2.getNodeName());
        
        exploreHijos(raiz);
    }
    
    private static void exploreHijos(Node node)
    {
        NodeList children = node.getChildNodes();
        
        mostrarInfoNode(node);
        
        for (int i = 0; i < children.getLength(); i++) 
        {
            exploreHijos(children.item(i));
        } 
    }
    
    private static void mostrarInfoNode(Node nodo)
    {
        System.out.println("nombre del nodo: " + nodo.getNodeName() 
                + " Valor del nodo: " + nodo.getNodeValue());
        
        if (nodo.getAttributes() == null) return;
        
        for (int i = 0; i < nodo.getAttributes().getLength(); i++)
        {
            Node attribute = nodo.getAttributes().item(i);
            System.out.println("<" + attribute.getNodeName() + ">: " + attribute.getNodeValue());
        }
    }
}
