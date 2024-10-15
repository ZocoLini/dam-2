package EJ3_A1UD2;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class EJ3_A1UD2
{
    private static final File EMPLEADOS = new File("Empleados.xml");

    public static void main(String[] args)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setValidating(true);
        dbf.setIgnoringElementContentWhitespace(true);

        // TODO: Por algun motivo no ignora los #text cuando usa un xsd

        DocumentBuilder db;
        try
        {
            dbf.setFeature("http://xml.org/sax/features/validation", true);
            dbf.setFeature("http://apache.org/xml/features/validation/schema", true);
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new ErrorHandler()
            {
                @Override
                public void warning(SAXParseException exception) throws SAXException
                {
                    System.out.println("Warning");
                }

                @Override
                public void error(SAXParseException exception) throws SAXException
                {
                    System.out.println("Error: " + exception.getMessage());
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException
                {
                    System.out.println("Fatal error");
                }
            });
        }
        catch (ParserConfigurationException e)
        {
            System.out.println("Configuracion no valida manin: " + e.getMessage());
            return;
        }

        Document document;
        try
        {
            document = db.parse(EMPLEADOS);
        }
        catch (SAXException e)
        {
            System.out.println("SAX Exception: " + e.getMessage());
            return;
        }
        catch (IOException e)
        {
            System.out.println("IOException: " + e.getMessage());
            return;
        }

        visualizarElementInfo(document.getDocumentElement(), 0);
        addIdSequentialy(document);
        modify(document);
        clonarNodo(document);
        exportarDocumento(document);
    }

    private static void visualizarElementInfo(Node element, int indent)
    {
        System.out.println(" ".repeat(indent * 4) + "Nombre elemento: " + element.getNodeName());
        System.out.println(" ".repeat(indent * 4) + "- Valor elemento: " + element.getNodeValue());

        var attributes = element.getAttributes();

        if (attributes != null)
        {
            for (int i = 0; i < attributes.getLength(); i++)
            {
                var attribute = attributes.item(i);
                System.out.println(" ".repeat(indent * 4 + 2) + "Nombre atributo: " + attribute.getNodeName());
                System.out.println(" ".repeat(indent * 4 + 2) + "- Valor atributo: " + attribute.getNodeValue());
            }
        }

        var children = element.getChildNodes();

        for (int i = 0; i < children.getLength(); i++)
        {
            visualizarElementInfo(children.item(i), indent + 1);
        }
    }

    private static void addIdSequentialy(Document document)
    {
        var root = document.getDocumentElement();

        int j = 0;

        for (int i = 0; i < root.getChildNodes().getLength(); i++)
        {
            var child = root.getChildNodes().item(i);
            if (!child.getNodeName().equals("Empleado")) continue;

            Attr attr = document.createAttribute("id");
            attr.setValue(String.valueOf(j));
            j++;
            child.getAttributes().setNamedItem(attr);
        }
    }

    private static void modify(Document document)
    {
        Element primerEmpleado = createEmpleado(document,
                "10",
                "Carmen Gil Villa",
                "Secretaria",
                "200 euros",
                "Del Mar, 4 36004 Pontevedra");
        Element segundoEmpleado = createEmpleado(document,
                "11",
                "Lucia Martín Martín",
                "Gerente",
                "200 euros",
                "Avda Vigo, 7 36911 Marin-Pontevedra-");
        Element tercerEmpleado = createEmpleado(document,
                "14",
                "Carolina Sánchez Jiménez",
                "Trabajador",
                "100 euros",
                "Oriente, 6 35200 Vigo Pontevedra");

        Element root = document.getDocumentElement();

        root.appendChild(primerEmpleado);
        root.insertBefore(segundoEmpleado, root.getFirstChild());

        root.replaceChild(tercerEmpleado, root.getChildNodes().item(1));
        root.removeChild(root.getChildNodes().item(1));
    }

    private static Element createEmpleado(Document document, String id, String name, String cargoContent,
            String aumentoContent, String direccionContent)
    {
        Element empleado = document.createElement("Empleado");
        empleado.setAttribute("id", id);

        Element nombre = document.createElement("Nombre");
        nombre.setTextContent(name);
        Element cargo = document.createElement("Cargo");
        cargo.setTextContent(cargoContent);
        Element direccion = document.createElement("Direccion");
        direccion.setTextContent(direccionContent);
        Element aumento = document.createElement("Aumento");
        aumento.setTextContent(aumentoContent);

        empleado.appendChild(nombre);
        empleado.appendChild(cargo);
        empleado.appendChild(direccion);
        empleado.appendChild(aumento);

        return empleado;
    }

    //  Clona el nodo 3 y sitúalo al final del documento. Cambia el valor del atributo por id=15 y el nombre
//  por Maria Rivas Rivas
    private static void clonarNodo(Document document)
    {
        Element root = document.getDocumentElement();
        Node clonedNode = root.getChildNodes().item(2).cloneNode(true);

        root.appendChild(clonedNode);
    }

    private static final File OUTPUT = new File("EmpleadoCambio.xml");

    private static void exportarDocumento(Document document)
    {
        TransformerFactory transFact = TransformerFactory.newInstance();

        try
        {
            Transformer transformer = transFact.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            DOMSource source = new DOMSource(document);
            StreamResult output = new StreamResult(OUTPUT);

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
