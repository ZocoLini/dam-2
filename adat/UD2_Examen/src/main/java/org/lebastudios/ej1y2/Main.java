package org.lebastudios.ej1y2;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main
{
    private static final String MEDIOCIONES_RIOS_FILE_PATH = "medicionesRios.xml";
    private static final String ACTUALIZACIONES_RIOS_FILE_PATH = "Actualizaciones.xml";
    private static final String FICHERO_SALIDA_MODIFICACIONES = "medicionesRiosOUT.xml";
    
    public static void main(String[] args)
    {
        // EJERCICIO 1 A: Cargamos el DOM en memoria. El XML cargado esta siendo validado con un DTD.
        MedicionesRios medicionesRios;
        try
        {
            medicionesRios =  new MedicionesRios(new FileInputStream(MEDIOCIONES_RIOS_FILE_PATH));
        }
        catch (IOException e)
        {
            System.out.println("Error de entrada salida relacionada con la ruta " + MEDIOCIONES_RIOS_FILE_PATH);
            return;        
        }
        catch (SAXException e)
        {
            System.out.println("Error al intentar crear el DOM: " + e.getMessage());
            return;
        }

        // EJERCICIO 1 B: Usando el DOM cargado, verificar si, dado un codigo de rio y una fecha, existe la medicion correspondiente.
        System.out.println(medicionesRios.existeMedicion("R001", "15-ene-2023") ? "Primera medicion existe" : "Primera medicion no existe");
        System.out.println(medicionesRios.existeMedicion("R001", "14-ene-2023") ? "Segunda medicion existe" : "Segunda medicion no existe");

        // EJERCICIO 2: Realizar unas modificaciones en el DOM dado un archivo XML que las define y persistir el resultado 
        // en un archivo XML
        try
        {
            medicionesRios.actualizarConArchivo(new File(ACTUALIZACIONES_RIOS_FILE_PATH));
        }
        catch (IOException e)
        {
            System.out.println("Error de entrada salida relacionada con la ruta " + ACTUALIZACIONES_RIOS_FILE_PATH);
            return;
        }
        
        medicionesRios.escribirEnFichero(new File(FICHERO_SALIDA_MODIFICACIONES));
    }
}
