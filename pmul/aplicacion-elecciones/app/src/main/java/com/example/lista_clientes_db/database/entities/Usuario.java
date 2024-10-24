package com.example.lista_clientes_db.database.entities;

public class Usuario
{
    private final int id;
    private final String name;
    private final String password;

    public Usuario(int id, String name, String password)
    {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPassword()
    {
        return password;
    }
}
