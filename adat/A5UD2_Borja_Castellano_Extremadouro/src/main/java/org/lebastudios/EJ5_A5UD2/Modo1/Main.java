package org.lebastudios.EJ5_A5UD2.Modo1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.net.URI;

public class Main
{
    public static void main(String[] args)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        try
        {
            Mareas mareas = objectMapper.readValue(
                    URI.create("https://servizos.meteogalicia.gal/mgrss/predicion/mareas/jsonMareas.action").toURL(),
                    Mareas.class
            );

            System.out.println(mareas);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
