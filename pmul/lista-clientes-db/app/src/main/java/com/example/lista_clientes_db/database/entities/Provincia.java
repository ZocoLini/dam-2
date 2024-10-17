package com.example.lista_clientes_db.database.entities;

public class Provincia
{
    private final int id;
    private final String nombre;

    public Provincia(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId()
    {
        return id;
    }

    public String getNombre()
    {
        return nombre;
    }
}
