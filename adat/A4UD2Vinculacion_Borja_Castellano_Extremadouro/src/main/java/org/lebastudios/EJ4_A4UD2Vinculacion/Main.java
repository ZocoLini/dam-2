package org.lebastudios.EJ4_A4UD2Vinculacion;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Direccion direccion1 = new Direccion("Cabadiña", 5, 36690);
        Direccion direccion2 = new Direccion("Cabadiña", 7, 36690);
        
        Hotel hotel1 = new Hotel();
        hotel1.setTelefonos(List.of("1231241", "235546345643"));
        hotel1.setCodigoHotel("1");
        hotel1.setDireccion(direccion1);
        hotel1.setNombre("El Eden");
        hotel1.setFechaInauguracion("10/06/2000");
        
        Hotel hotel2 = new Hotel();
        hotel2.setTelefonos(List.of("3456731", "34561231"));
        hotel2.setCodigoHotel("2");
        hotel2.setDireccion(direccion2);
        hotel2.setNombre("El quisco");
        hotel2.setFechaInauguracion("11/11/2004");
        
        Hoteles hoteles = new Hoteles();
        hoteles.setHoteles(List.of(hotel1, hotel2));
        hoteles.setNombre("SOL CONFORT");
        hoteles.setCif("AB23723787");
        
        try
        {
            JAXBContext context = JAXBContext.newInstance(Hoteles.class);
            Marshaller marshaller = context.createMarshaller();
            
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            marshaller.marshal(hoteles, new FileOutputStream("hoteles.xml"));
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
