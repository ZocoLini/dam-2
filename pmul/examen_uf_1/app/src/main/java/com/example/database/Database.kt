package com.example.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.core.util.Consumer
import java.util.Stack

object Database
{
    private const val MAX_CONNECTIONS = 3;

    private var connections = Stack<SQLiteDatabase>()
    private var initialized = false;

    fun init(context: Context)
    {
        if (initialized) return

        val database = DatabaseInitializer(context);

        for (i in 0 until MAX_CONNECTIONS)
        {
            connections.push(database.writableDatabase);
        }

        initialized = true;
    }

    fun connect(consumer: Consumer<SQLiteDatabase>)
    {
        if (connections.isEmpty())
        {
            throw Exception("No connections available");
        }

        val connection = connections.pop();

        consumer.accept(connection);

        connections.push(connection);
    }
}