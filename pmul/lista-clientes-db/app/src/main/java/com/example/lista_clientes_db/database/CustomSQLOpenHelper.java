package com.example.lista_clientes_db.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lista_clientes_db.database.entities.ClientDAO;
import com.example.lista_clientes_db.database.entities.ProvinciaDAO;

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
        sqLiteDatabase.execSQL(ProvinciaDAO.TABLE_DEFINITION);

        sqLiteDatabase.execSQL("insert into provincia (name) values ('A Coruña')");
        sqLiteDatabase.execSQL("insert into provincia (name) values ('Pontevedra')");
        sqLiteDatabase.execSQL("insert into provincia (name) values ('Lugo')");
        sqLiteDatabase.execSQL("insert into provincia (name) values ('Ourense')");

        sqLiteDatabase.execSQL(ClientDAO.TABLE_DEFINITION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
