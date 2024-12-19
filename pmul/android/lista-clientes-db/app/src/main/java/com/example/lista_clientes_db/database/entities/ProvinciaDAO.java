package com.example.lista_clientes_db.database.entities;

import android.database.Cursor;

import com.example.lista_clientes_db.ObjWrapper;
import com.example.lista_clientes_db.database.Database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ProvinciaDAO
{
    public static final String TABLE_DEFINITION = "create table Provincia " +
            "(id integer primary key autoincrement, name text)";

    public static Optional<Provincia> select(int id)
    {
        ObjWrapper<Provincia> provincia = new ObjWrapper<>();

        Database.getInstance().connect(db ->
        {
            try (Cursor cursor = db.rawQuery("select id, name from Provincia where id = ?",
                    new String[]{String.valueOf(id)}))
            {
                if (cursor.moveToNext())
                {
                    provincia.setObj(new Provincia(cursor.getInt(0), cursor.getString(1)));
                }
            }
        });

        return provincia.intoOptional();
    }

    public static Collection<Provincia> selectAll()
    {
        return selectAll("");
    }

    public static Collection<Provincia> selectAll(String orderBy)
    {
        List<Provincia> provincias = new ArrayList<>();

        Database.getInstance().connect(db ->
        {
            try (Cursor cursor = db.rawQuery("select * from Provincia " + orderBy, null))
            {
                while (cursor.moveToNext())
                {
                    provincias.add(new Provincia(cursor.getInt(0), cursor.getString(1)));
                }
            }
        });

        return provincias;
    }
}
