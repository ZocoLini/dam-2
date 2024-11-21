package org.lebastudios.examples.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.SneakyThrows;

import java.io.FileInputStream;

public class JAXBExample
{
    @SneakyThrows
    public static void main(String[] args)
    {
        JAXBContext context = JAXBContext.newInstance(ListaPersonas.class);
        // Leer desde fichero
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ListaPersonas personas = (ListaPersonas) unmarshaller.unmarshal(new FileInputStream("personas.xml"));
        // Escribir a un OutputStream
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(personas, System.out);
    }
}
