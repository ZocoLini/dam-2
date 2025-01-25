package org.lebastudios.EJ3_A7UD3;

import com.google.gson.GsonBuilder;
import org.lebastudios.EJ1_A7UD3.Empleados;
import org.lebastudios.EJ1_A7UD3.LocalDateTypeAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Empleados empleados = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create()
                .fromJson(Files.readString(Path.of("insercionEmpleados.json")),
                        Empleados.class);
    }
}
