package com.example.aplicacion_elecciones.database.entities;

public class Usuario
{
    private final int id;
    private final String nif;
    private final String password;
    private final boolean votacionesRealizadas;

    public Usuario(int id, String nif, String password, boolean votacionesRealizadas)
    {
        this.id = id;
        this.nif = nif;
        this.password = password;
        this.votacionesRealizadas = votacionesRealizadas;
    }

    public int getId()
    {
        return id;
    }

    public String getNif()
    {
        return nif;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean isVotacionesRealizadas()
    {
        return votacionesRealizadas;
    }
}
