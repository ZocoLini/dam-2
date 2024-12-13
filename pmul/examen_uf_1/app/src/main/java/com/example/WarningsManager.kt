package com.example

import android.content.Context
import com.example.database.Database
import com.example.entities.Alert

object WarningsManager
{
    private var actualWarning: Warning? = null;

    fun getActualWarning(): Warning?
    {
        return actualWarning;
    }

    fun setWarning(warning: Warning, context: Context)
    {
        actualWarning = warning;
    }

    fun saveWaningAsAlert()
    {
        if (actualWarning == null) return;

        Database.connect { conn ->
            Alert.insert(
                Alert(
                    actualWarning!!.token, actualWarning!!.context,
                    true, actualWarning!!.org_name
                ), conn
            );
        }
    }

    class Warning(val token: String, val context: String, val org_name: String, val control_name:
    String)
}