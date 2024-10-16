package EJ4_A1UD2;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class EJ4_A1UD2
{
    private static final File DATA = new File("Hoteles.dat");

    public static void main(String[] args)
    {
        appMenu();
    }

    private static void appMenu()
    {
        boolean exit = false;

        while (!exit)
        {
            switch (showMenu())
            {
                case 1:
                    saveHotel();
                    break;
                case 2:
                    convertirDOMToXML();
                    break;
                default:
                    exit = true;
                    break;
            }
        }
    }

    private static int showMenu()
    {
        System.out.println("----  Menu  ----");
        System.out.println(" 1.- Guardar un nuevo hotel");
        System.out.println(" 2.- Convertir a XML");

        try
        {
            return new Scanner(System.in).nextInt();
        }
        catch (Exception ignore)
        {
            return -1;
        }
    }

    private static void saveHotel()
    {
        try (RandomAccessFile raf = new RandomAccessFile(DATA, "rw"))
        {
            Hotel.generate().save(raf);
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

    private static final File OUTPUT = new File("Hoteles.xml");

    private static void convertirDOMToXML()
    {
        TransformerFactory transFact = TransformerFactory.newInstance();

        try
        {
            Transformer transformer = transFact.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            DOMSource source = new DOMSource(convertirDATaDOM());
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

    private static Document convertirDATaDOM()
    {
        DOMImplementation implementation;
        try
        {
            implementation = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().getDOMImplementation();
        }
        catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e);
        }

        Document document = implementation.createDocument(null, "Hoteles", null);
        
        document.setXmlVersion("1.0");
        document.setXmlStandalone(false);
        
        ArrayList<Hotel> hoteles = new ArrayList<>();

        try (RandomAccessFile raf = new RandomAccessFile(DATA, "r"))
        {
            Hotel hotel = Hotel.read(raf);
            hoteles.add(hotel);
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }

        for (var hotel : hoteles)
        {
            document.getDocumentElement().appendChild(hotel.intoNode(document));
        }
        
        return document;
    }
}
