package org.lebastudios.EJ1_A4UD2Vinculacion;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main
{
    public static void main(String[] args)
    {
        Empleado empleado1 = new Empleado("76582884R", "Borja", 24);
        Empleado empleado2 = new Empleado("77476385W", "David", 20);
        
        Empresa empresa = new Empresa("A13241324", "LebaStudios", empleado1, empleado2);

        try
        {
            JAXBContext context = JAXBContext.newInstance(Empresa.class);
            Marshaller marshaller = context.createMarshaller();
            
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            marshaller.marshal(empresa, new FileOutputStream("empresa.xml"));
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
