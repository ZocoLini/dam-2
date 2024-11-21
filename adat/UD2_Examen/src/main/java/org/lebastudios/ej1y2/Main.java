package org.lebastudios.ej1y2;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        catch (SAXException e)
        {
            throw new RuntimeException(e);
        }

        System.out.println(medicionesRios.existeMedicion("R001", "15-ene-2023") ? "Primera medicion existe" : "Primera medicion no existe");
        System.out.println(medicionesRios.existeMedicion("R001", "14-ene-2023") ? "Segunda medicion existe" : "Segunda medicion no existe");

        try
        {
            medicionesRios.actualizarConArchivo(new File(ACTUALIZACIONES_RIOS_FILE_PATH));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        
        medicionesRios.escribirEnFichero(new File(FICHERO_SALIDA_MODIFICACIONES));
    }
}
