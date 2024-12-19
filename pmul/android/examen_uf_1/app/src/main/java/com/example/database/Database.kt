package com.example.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

object Database
{
    private lateinit var connection: SQLiteDatabase;
    private var initialized = false;

    fun init(context: Context)
    {
        if (initialized) return

        val database = DatabaseInitializer(context);

        connection = database.writableDatabase;

        initialized = true;
    }

    fun resetTables()
    {
        connection.execSQL("delete from alerts");
    }

    fun connect(consumer: (SQLiteDatabase) -> Unit)
    {
        consumer(connection);
    }
}