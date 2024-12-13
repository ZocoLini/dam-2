package com.example.entities

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.database.Database

class Alert(val token: String, val context: String, val accepted: Boolean)
{
    companion object DAO
    {
        val TABLE_DEF = """
            CREATE TABLE alerts (
                id integer primary key autoincrement,
                token varchar,
                context varchar,
                accepted boolean
            );
        """.trimIndent();


        @JvmStatic
        fun selectAll(): List<Alert>
        {
            val alerts = ArrayList<Alert>();

            Database.connect { conn ->
                val cursor = conn.rawQuery("SELECT token, context, accepted FROM alerts", null);

                while (cursor.moveToNext())
                {
                    alerts.add(Alert(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2) == 1
                    ));
                }

                cursor.close();
            }

            return alerts;
        }

        @JvmStatic
        fun insert(alert: Alert, connection: SQLiteDatabase)
        {
            connection.insert("alerts", null, ContentValues().apply {
                put("token", alert.token);
                put("context", alert.context);
                put("accepted", alert.accepted);
            });
        }
    }
}