package com.example.aplicacion_elecciones.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Stack;
import java.util.function.Consumer;

public class Database
{
    private static final int CONNECTIONS = 5;

    private static Database instance;

    public static Database getInstance()
    {
        if (instance == null)
        {
            throw new RuntimeException("Database not initialized");
        }

        return instance;
    }

    public static void initialize(Context context)
    {
        if (instance == null)
        {
            instance = new Database(context);
        }
    }

    private final Context context;
    private Stack<SQLiteDatabase> stack = new Stack<>();

    private Database(Context context)
    {
        this.context = context;

        for (int i = 0; i < CONNECTIONS; i++)
        {
            stack.push(CustomSQLOpenHelper.getInstance(context).getWritableDatabase());
        }
    }

    public void connect(Consumer<SQLiteDatabase> consumer)
    {
        if (stack.isEmpty())
        {
            throw new RuntimeException("No connections available");
        }

        SQLiteDatabase db = stack.pop();

        consumer.accept(db);

        stack.push(db);
    }
}
