package org.lebastudios.EJ5_A4UD2REPASOVinculacion;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main
{
    public static void main(String[] args)
    {
        Pesca pesca;
        
        try
        {
            JAXBContext context = JAXBContext.newInstance(Pesca.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            
            pesca = (Pesca) unmarshaller.unmarshal(new FileInputStream("Pesca.xml"));
        }
        catch (JAXBException e)
        {
            throw new RuntimeException(e);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        System.out.println(pesca.preattyPrinting());
    }
}
