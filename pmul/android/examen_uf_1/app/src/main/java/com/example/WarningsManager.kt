package com.example

import com.example.database.Database
import com.example.entities.Alert

object WarningsManager
{
    private var actualWarning: Warning? = null;
    private var warningOrigin: MainActivity.CniSensorListener? = null;

    fun getActualWarning(): Warning?
    {
        return actualWarning;
    }

    fun showWarning(warning: Warning, warningOrigin: MainActivity.CniSensorListener)
    {
        actualWarning = warning;
        this.warningOrigin = warningOrigin;
    }

    fun disableWarning()
    {
        warningOrigin!!.deactivate();
    }

    fun saveWaningAsAlert(accepted: Boolean)
    {
        if (actualWarning == null) return;

        Database.connect { conn ->
            Alert.insert(
                Alert(
                    actualWarning!!.token, actualWarning!!.context,
                    accepted, actualWarning!!.orgName
                ), conn
            );
        }
    }

    class Warning(val token: String, val context: String, val orgName: String, val controlName:
    String)
}