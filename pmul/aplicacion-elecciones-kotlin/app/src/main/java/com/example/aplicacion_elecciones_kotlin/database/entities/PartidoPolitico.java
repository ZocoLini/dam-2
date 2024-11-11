package com.example.aplicacion_elecciones_kotlin.database.entities;

import android.graphics.Color;

public class PartidoPolitico
{
    private final int id;
    private final String nombre;
    private final int colorRed;
    private final int colorGreen;
    private final int colorBlue;
    private final String siglas;

    public PartidoPolitico(int id, String nombre, int colorRed, int colorGreen, int colorBlue, String siglas)
    {
        this.id = id;
        this.nombre = nombre;
        this.colorRed = colorRed;
        this.colorGreen = colorGreen;
        this.colorBlue = colorBlue;
        this.siglas = siglas;
    }

    public int getId()
    {
        return id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public int getColorRed()
    {
        return colorRed;
    }

    public int getColorGreen()
    {
        return colorGreen;
    }

    public int getColorBlue()
    {
        return colorBlue;
    }

    public int getColor()
    {
        return Color.rgb(colorRed, colorGreen, colorBlue);
    }

    public String getSiglas()
    {
        return siglas;
    }
}
