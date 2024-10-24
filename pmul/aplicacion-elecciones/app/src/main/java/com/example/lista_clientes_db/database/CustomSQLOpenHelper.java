package com.example.lista_clientes_db.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lista_clientes_db.database.entities.ClientDAO;
import com.example.lista_clientes_db.database.entities.ProvinciaDAO;
import com.example.lista_clientes_db.database.entities.UsuarioDAO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomSQLOpenHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 2;

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
            throw new RuntimeException("Should not happend");
        }
    }

    public void version1(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(ProvinciaDAO.TABLE_DEFINITION);

        sqLiteDatabase.execSQL("insert into provincia (name) values ('A Coruña')");
        sqLiteDatabase.execSQL("insert into provincia (name) values ('Pontevedra')");
        sqLiteDatabase.execSQL("insert into provincia (name) values ('Lugo')");
        sqLiteDatabase.execSQL("insert into provincia (name) values ('Ourense')");

        sqLiteDatabase.execSQL(ClientDAO.TABLE_DEFINITION);
    }

    public void version2(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(UsuarioDAO.TABLE_DEFINITION);

        sqLiteDatabase.execSQL("insert into usuario (name, password) values ('admin', 'admin')");
    }
}
