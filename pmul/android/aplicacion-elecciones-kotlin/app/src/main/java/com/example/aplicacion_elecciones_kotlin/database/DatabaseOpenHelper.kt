package com.example.aplicacion_elecciones_kotlin.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.aplicacion_elecciones_kotlin.database.entities.*
import java.lang.reflect.Method

const val DATABASE_NAME = "database"
const val DATABASE_VERSION = 1

class DatabaseOpenHelper(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    override fun onCreate(p0: SQLiteDatabase)
    {
        onUpgrade(p0, 0, DATABASE_VERSION)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        require(newVersion >= oldVersion) { "newVersion < oldVersion" }

        try
        {
            for (i in oldVersion until newVersion)
            {
                println("Executing version" + (i + 1))

                val metodo: Method = DatabaseOpenHelper::class.java.getMethod(
                    "version" + (i + 1),
                    SQLiteDatabase::class.java
                )

                metodo.invoke(this, sqLiteDatabase)
            }
        }
        catch (e: Exception)
        {
            throw RuntimeException("Should not happend: " + e.message)
        }
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    fun version1(connection: SQLiteDatabase)
    {
        connection.execSQL(UsuarioDAO.TABLE_DEFINITION)

        // Rellena con inserciones de usuarios usando el DAO
        UsuarioDAO.insert(Usuario(-1, "77482884R", "abc123.", false), connection)

        connection.execSQL(PartidoPoliticoDAO.TABLE_DEFINITION)

        // Rellena con inserciones de partido politicos españoles usando el DAO
        PartidoPoliticoDAO.insert(
            PartidoPolitico(-1, "Partido Popular", 0, 0, 255, "PP"),
            connection
        )
        PartidoPoliticoDAO.insert(
            PartidoPolitico(
                -1,
                "Partido Socialista Obrero Español",
                255,
                0,
                0,
                "PSOE"
            ), connection
        )
        PartidoPoliticoDAO.insert(
            PartidoPolitico(-1, "Unidas Podemos", 255, 255, 0, "UP"),
            connection
        )
        PartidoPoliticoDAO.insert(PartidoPolitico(-1, "Vox", 0, 255, 0, "Vox"), connection)
        PartidoPoliticoDAO.insert(PartidoPolitico(-1, "Ciudadanos", 0, 255, 255, "Cs"), connection)

        connection.execSQL(CandidatoEleccionesDAO.TABLE_DEFINITION)

        // Rellena con inserciones de candidatos a elecciones usando el DAO
        CandidatoEleccionesDAO.insert(CandidatoElecciones(-1, "Pablo Casado", 1, 0), connection)
        CandidatoEleccionesDAO.insert(CandidatoElecciones(-1, "Pedro Sánchez", 2, 0), connection)
        CandidatoEleccionesDAO.insert(CandidatoElecciones(-1, "Pablo Iglesias", 3, 0), connection)
        CandidatoEleccionesDAO.insert(CandidatoElecciones(-1, "Santiago Abascal", 4, 0), connection)
        CandidatoEleccionesDAO.insert(CandidatoElecciones(-1, "Albert Rivera", 5, 0), connection)
    }
}