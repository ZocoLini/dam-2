package org.lebastudios.ej3;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main
{
    private static final String MEDICIONES_RIOS_SIN_DTD_FILE_PATH = "medicionesRiosSinDTD.xml";
    
    public static void main(String[] args)
    {
        // EJERCICIO 3. Usando JAXB para cargar en memoria a los objetos correspondientes y despues mostrando 
        // la informacion por consola
        
        JAXBContext context;
        try
        {
            // Hacemos el unmarshal del archivo XML al objeto Programa
            context = JAXBContext.newInstance(Programa.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Programa programa = (Programa) unmarshaller.unmarshal(
                    new FileInputStream(MEDICIONES_RIOS_SIN_DTD_FILE_PATH)
            );
            
            // Imprimimos por consola la informacion de Programa
            System.out.println(programa.getPreattyPrinting());
        }
        catch (JAXBException e)
        {
            System.out.println("Algun error inesperado ocurrio mientras se hacia el unmarchal");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No se ha encontrado el archivo XML que se uso en el unmarshaller");
        }

    }
}
