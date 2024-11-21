package org.lebastudios.ej3;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main
{
    public static void main(String[] args)
    {
        JAXBContext context;
        try
        {
            // Hacemos el unmarshal del archivo XML al objeto Programa
            context = JAXBContext.newInstance(Programa.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Programa programa = (Programa) unmarshaller.unmarshal(
                    new FileInputStream("medicionesRios.xml")
            );
            
            // Imprimimos por consola la informacion de Programa
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
