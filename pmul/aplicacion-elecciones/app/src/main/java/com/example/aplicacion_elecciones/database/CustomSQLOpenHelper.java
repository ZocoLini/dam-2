package com.example.aplicacion_elecciones.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aplicacion_elecciones.database.entities.CandidatoElecciones;
import com.example.aplicacion_elecciones.database.entities.CandidatoEleccionesDAO;
import com.example.aplicacion_elecciones.database.entities.PartidoPolitico;
import com.example.aplicacion_elecciones.database.entities.PartidoPoliticoDAO;
import com.example.aplicacion_elecciones.database.entities.Usuario;
import com.example.aplicacion_elecciones.database.entities.UsuarioDAO;

import java.lang.reflect.Method;

public class CustomSQLOpenHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;

    private static CustomSQLOpenHelper instance;

    static CustomSQLOpenHelper getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new CustomSQLOpenHelper(context);
        }

        return instance;
    }

    private CustomSQLOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        databaseUpdate(sqLiteDatabase, 0, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        databaseUpdate(sqLiteDatabase, i, i1);
    }

    private void databaseUpdate(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        if (newVersion < oldVersion) throw new IllegalArgumentException("newVersion < oldVersion");

        try
        {
            for (int i = oldVersion; i < newVersion; i++)
            {
                System.out.println("Executing version" + (i + 1));

                Method metodo = CustomSQLOpenHelper.class
                        .getMethod("version" + (i + 1), SQLiteDatabase.class);

                metodo.invoke(this, sqLiteDatabase);
            }
        } catch (Exception e)
        {
            throw new RuntimeException("Should not happend: " + e.getMessage());
        }
    }

    public void version1(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(UsuarioDAO.TABLE_DEFINITION);
        // Rellena con inserciones de usuarios usando el DAO
        UsuarioDAO.insert(new Usuario(-1, "77482884R", "abc123.", false), sqLiteDatabase);

        sqLiteDatabase.execSQL(PartidoPoliticoDAO.TABLE_DEFINITION);
        // Rellena con inserciones de partido politicos españoles usando el DAO
        PartidoPoliticoDAO.insert(new PartidoPolitico(-1, "Partido Popular", 0, 0, 255, "PP"), sqLiteDatabase);
        PartidoPoliticoDAO.insert(new PartidoPolitico(-1, "Partido Socialista Obrero Español", 255, 0, 0, "PSOE"), sqLiteDatabase);
        PartidoPoliticoDAO.insert(new PartidoPolitico(-1, "Unidas Podemos", 255, 255, 0, "UP"), sqLiteDatabase);
        PartidoPoliticoDAO.insert(new PartidoPolitico(-1, "Vox", 0, 255, 0, "Vox"), sqLiteDatabase);
        PartidoPoliticoDAO.insert(new PartidoPolitico(-1, "Ciudadanos", 0, 255, 255, "Cs"), sqLiteDatabase);

        sqLiteDatabase.execSQL(CandidatoEleccionesDAO.TABLE_DEFINITION);
        // Rellena con inserciones de candidatos a elecciones usando el DAO
        CandidatoEleccionesDAO.insert(new CandidatoElecciones(-1, "Pablo Casado", 1), sqLiteDatabase);
        CandidatoEleccionesDAO.insert(new CandidatoElecciones(-1, "Pedro Sánchez", 2), sqLiteDatabase);
        CandidatoEleccionesDAO.insert(new CandidatoElecciones(-1, "Pablo Iglesias", 3), sqLiteDatabase);
        CandidatoEleccionesDAO.insert(new CandidatoElecciones(-1, "Santiago Abascal", 4), sqLiteDatabase);
        CandidatoEleccionesDAO.insert(new CandidatoElecciones(-1, "Albert Rivera", 5), sqLiteDatabase);
    }
}
