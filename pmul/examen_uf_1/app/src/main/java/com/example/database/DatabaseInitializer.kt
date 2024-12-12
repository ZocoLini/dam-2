package com.example.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.reflect.Method

const val DATABASE_NAME = "database"
const val DATABASE_VERSION = 1

class DatabaseInitializer(
    context: Context?,
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

                val method: Method = DatabaseInitializer::class.java.getMethod(
                    "version" + (i + 1),
                    SQLiteDatabase::class.java
                )

                method.invoke(this, sqLiteDatabase)
            }
        }
        catch (e: Exception)
        {
            throw RuntimeException("Should not happen: " + e.message)
        }
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    fun version1(connection: SQLiteDatabase)
    {

    }
}