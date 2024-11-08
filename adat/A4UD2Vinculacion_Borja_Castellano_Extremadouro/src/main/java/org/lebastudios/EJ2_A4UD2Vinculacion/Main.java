package org.lebastudios.EJ2_A4UD2Vinculacion;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.lebastudios.EJ1_A4UD2Vinculacion.Empresa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class Main
{
    public static void main(String[] args)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(Modulo.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            System.out.println(unmarshaller.unmarshal(new FileInputStream("modulo.xml")).toString());
        }
        catch (JAXBException e)
        {
            throw new RuntimeException(e);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }

    }
}
