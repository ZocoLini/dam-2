package com.example.lista_clientes_db.database.entities;

import android.database.Cursor;

import com.example.lista_clientes_db.database.Database;

public class UsuarioDAO
{
    public static final String TABLE_DEFINITION = "create table Usuario " +
            "(id integer primary key autoincrement, name text, password text)";

    public static boolean validateUser(String name, String password)
    {
        final boolean[] result = {false};

        Database.getInstance().connect(db ->
        {
            try (Cursor cursor = db.rawQuery(
                    "select * from Usuario where name = ? and password = ?",
                    new String[]{name, password})
            )
            {
                result[0] = cursor.moveToNext();
            }
        });

        return result[0];
    }
}
