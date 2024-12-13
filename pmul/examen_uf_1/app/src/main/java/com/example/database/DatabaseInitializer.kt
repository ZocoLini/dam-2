package com.example.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.entities.Alert

const val DATABASE_NAME = "database"
const val DATABASE_VERSION = 1

class DatabaseInitializer(
    context: Context?,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    override fun onCreate(conn: SQLiteDatabase)
    {
        conn.execSQL(Alert.DAO.TABLE_DEF)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
    }
}