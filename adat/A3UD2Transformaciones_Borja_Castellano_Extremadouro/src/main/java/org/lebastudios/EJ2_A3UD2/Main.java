package org.lebastudios.EJ2_A3UD2;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileWriter;
import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try
        {
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(Main.class.getResourceAsStream("/Hardware.xsl")));
            transformer.transform(new StreamSource(Main.class.getResourceAsStream("/Hardware.xml")), new StreamResult(new FileWriter("Hardware.xml")));
        }
        catch (TransformerConfigurationException e)
        {
            throw new RuntimeException(e);
        }
        catch (TransformerException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
