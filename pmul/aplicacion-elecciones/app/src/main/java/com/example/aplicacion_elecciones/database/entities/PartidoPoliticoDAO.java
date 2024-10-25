package com.example.aplicacion_elecciones.database.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aplicacion_elecciones.database.Database;

public class PartidoPoliticoDAO
{
    public static final String TABLE_DEFINITION = "create table PartidoPolitico " +
            "(id integer primary key autoincrement, " +
            "nombre text not null unique, " +
            "color_red integer not null, " +
            "color_green integer not null, " +
            "color_blue integer not null, " +
            "siglas text not nul;";

    public static PartidoPolitico select(int id)
    {
        PartidoPolitico[] partidoPolitico = {null};

        Database.getInstance().connect(session ->
        {
            try (Cursor cursos = session.rawQuery(
                    "select id, nombre, color_red, color_green, color_blue, siglas " +
                            "from PartidoPolitico where id = ?",
                    new String[]{String.valueOf(id)}))
            {
                if (cursos.moveToNext())
                {
                    partidoPolitico[0] = new PartidoPolitico(
                            cursos.getInt(0),
                            cursos.getString(1),
                            cursos.getInt(2),
                            cursos.getInt(3),
                            cursos.getInt(4),
                            cursos.getString(5)
                    );
                }
            }
        });

        return partidoPolitico[0];
    }

    public static boolean insert(PartidoPolitico partidoPolitico)
    {
        if (partidoPolitico == null)
            throw new IllegalArgumentException("El partido político no puede ser nulo");
        if (partidoPolitico.getId() != -1)
            throw new IllegalArgumentException("El partido político ya existe");

        boolean[] result = {false};

        Database.getInstance().connect(session -> result[0] = insert(partidoPolitico, session));

        return result[0];
    }

    public static boolean insert(PartidoPolitico partidoPolitico, SQLiteDatabase session)
    {
        session.execSQL(
                "insert into PartidoPolitico (nombre, color_red, color_green, color_blue, siglas) " +
                        "values (?, ?, ?, ?, ?)",
                new Object[]{
                        partidoPolitico.getNombre(),
                        partidoPolitico.getColorRed(),
                        partidoPolitico.getColorGreen(),
                        partidoPolitico.getColorBlue(),
                        partidoPolitico.getSiglas()
                }
        );

        return true;
    }
}
