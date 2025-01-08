package com.example.aplicacion_elecciones.database.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aplicacion_elecciones.database.Database;

import java.util.ArrayList;
import java.util.List;

public class CandidatoEleccionesDAO
{
    public static final String TABLE_DEFINITION = "create table CandidatoElecciones " +
            "(id integer primary key autoincrement, " +
            "name text not null, " +
            "id_partido_politico integer not null, " +
            "cantidad_votos_recibidos integer not null default 0)";

    public static List<CandidatoElecciones> selectAll()
    {
        List<CandidatoElecciones> candidatos = new ArrayList<>();

        Database.getInstance().connect(session ->
        {
            try (Cursor cursor = session.rawQuery(
                    "select id, name, id_partido_politico, cantidad_votos_recibidos from CandidatoElecciones",
                    null)
            )
            {
                while (cursor.moveToNext())
                {
                    candidatos.add(new CandidatoElecciones(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3)
                    ));
                }
            }
        });

        return candidatos;
    }

    public static boolean insert(CandidatoElecciones candidatoElecciones)
    {
        if (candidatoElecciones == null)
            throw new IllegalArgumentException("El candidato a elecciones no puede ser nulo");
        if (candidatoElecciones.getId() != -1)
            throw new IllegalArgumentException("El candidato a elecciones ya existe");

        boolean[] result = {false};

        Database.getInstance().connect(session -> result[0] = insert(candidatoElecciones, session));

        return result[0];
    }

    public static boolean insert(CandidatoElecciones candidatoElecciones, SQLiteDatabase session)
    {
        session.execSQL(
                "insert into CandidatoElecciones (name, id_partido_politico) " +
                        "values (?, ?)",
                new Object[]{
                        candidatoElecciones.getName(),
                        candidatoElecciones.getIdPartidoPolitico()
                }
        );

        return true;
    }

    public static void updateNumVotos(CandidatoElecciones candidatoElecciones)
    {
        if (candidatoElecciones == null)
            throw new IllegalArgumentException("El candidato a elecciones no puede ser nulo");
        if (candidatoElecciones.getId() == -1)
            throw new IllegalArgumentException("El candidato a elecciones no existe");

        Database.getInstance().connect(session ->
        {
            session.execSQL(
                    "update CandidatoElecciones cantidad_votos_recibidos = ? " +
                            "where id = ?",
                    new Object[]{
                            candidatoElecciones.getCantidadVotosRecibidos(),
                            candidatoElecciones.getId()
                    }
            );
        });
    }
}
