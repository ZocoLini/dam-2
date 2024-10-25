package com.example.aplicacion_elecciones.database.entities;

public class CandidatoElecciones
{
    private final int id;
    private final String name;
    private final int idPartidoPolitico;

    public CandidatoElecciones(int id, String name, int idPartidoPolitico)
    {
        this.id = id;
        this.name = name;
        this.idPartidoPolitico = idPartidoPolitico;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getIdPartidoPolitico()
    {
        return idPartidoPolitico;
    }
}
