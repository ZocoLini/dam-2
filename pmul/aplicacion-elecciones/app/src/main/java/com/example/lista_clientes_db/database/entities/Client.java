package com.example.lista_clientes_db.database.entities;

import androidx.annotation.NonNull;

public class Client
{
    private final int id;
    private final String name;
    private final String nif;
    private final boolean vip;
    private final Provincia provincia;

    public Client(int id, String name, String nif, boolean vip, Provincia provincia)
    {
        this.id = id;
        this.name = name;
        this.nif = nif;
        this.vip = vip;
        this.provincia = provincia;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getNif()
    {
        return nif;
    }

    public boolean isVip()
    {
        return vip;
    }

    public Provincia getProvincia()
    {
        return provincia;
    }

    @NonNull
    @Override
    public String toString()
    {
        return id + " - " + name + " - " + nif + " - " + (vip ? "VIP" : "No VIP") + " - " + provincia.getNombre();
    }
}
