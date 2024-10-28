package com.example.aplicacion_elecciones.database.entities;

import androidx.annotation.NonNull;

public class CandidatoElecciones
{
    private final int id;
    private final String name;
    private final int idPartidoPolitico;
    private int cantidadVotosRecibidos;

    public CandidatoElecciones(int id, String name, int idPartidoPolitico, int cantidadVotosRecibidos)
    {
        this.id = id;
        this.name = name;
        this.idPartidoPolitico = idPartidoPolitico;
        this.cantidadVotosRecibidos = cantidadVotosRecibidos;
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

    public int getCantidadVotosRecibidos()
    {
        return cantidadVotosRecibidos;
    }

    public void votar()
    {
        cantidadVotosRecibidos++;
    }

    @NonNull
    @Override
    public String toString()
    {
        return name + " (" + PartidoPoliticoDAO.select(idPartidoPolitico).getSiglas() + ")";
    }
}
