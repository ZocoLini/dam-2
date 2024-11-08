package org.lebastudios.EJ3_A3UD2;

import java.util.ArrayList;
import java.util.List;

public class Nota
{
    public String valor;
    public List<String> alumnos = new ArrayList<>();

    @Override
    public String toString()
    {
        return "Nota{" +
                "valor='" + valor + '\'' +
                ", alumnos=" + alumnos +
                '}';
    }
}
