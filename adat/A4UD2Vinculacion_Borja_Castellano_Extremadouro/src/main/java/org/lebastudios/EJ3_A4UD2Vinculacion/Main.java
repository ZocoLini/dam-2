package org.lebastudios.EJ3_A4UD2Vinculacion;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main
{
    public static void main(String[] args)
    {
        Marshaller marshaller;
        try
        {
            JAXBContext context = JAXBContext.newInstance(ListaPersonas.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        }
        catch (JAXBException e)
        {
            throw new RuntimeException(e);
        }
        
        ListaPersonas listaPersonas = new ListaPersonas();

        try
        {
            marshaller.marshal(listaPersonas, new FileOutputStream("personas.xml"));
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
