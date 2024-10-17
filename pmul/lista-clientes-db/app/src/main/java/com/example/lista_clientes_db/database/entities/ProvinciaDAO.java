package com.example.lista_clientes_db.database.entities;

public class ProvinciaDAO
{
    public static final String TABLE_DEFINITION = "create table Provincia " +
            "(id integer primary key autoincrement, name text)";

    public static Provincia select(int id)
    {
        return null;
    }
}
