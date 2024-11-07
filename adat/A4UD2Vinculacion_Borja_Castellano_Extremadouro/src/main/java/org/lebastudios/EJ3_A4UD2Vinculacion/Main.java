package org.lebastudios.EJ3_A4UD2Vinculacion;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

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

        Estudiante estudiante = new Estudiante();
        estudiante.setEdad(24);
        estudiante.setNombre("Borja Castellano");
        estudiante.getInfoContacto().setTelefonos(List.of("12341234", "684531345", "8412331254"));
        estudiante.setCarrera("Matemáticas");
        estudiante.setUniversidad("Santiago de Compostela");
        
        Trabajador trabajador = new Trabajador();
        trabajador.setEdad(33);
        trabajador.setNombre("Santiago");
        trabajador.getInfoContacto().setTelefonos(List.of("123456789"));
        trabajador.setSueldo(1200.0f);
        trabajador.setCargo("Robotico");
        trabajador.setCompanhia("IES Chan do Monte");
        
        listaPersonas.setPersonas(List.of(estudiante, trabajador));
        
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
