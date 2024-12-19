package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class Settings
{
    private static Settings instance;

    public static Settings getInstance(AppCompatActivity context)
    {
        if (instance == null)
        {
            instance = new Settings(context);
        }
        return instance;
    }

    private static final String SETTINGS_FILE = "settings.xml";
    private static final String SPINNER_FIELD = "spinner_field";
    private static final String NAME_FIELD = "name_field";
    private static final String HELPED_RECEIVED_FIELD = "helped_received_field";

    private final SharedPreferences sp;

    private Settings(AppCompatActivity context)
    {
        sp = context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    public String getName()
    {
        return sp.getString(NAME_FIELD, "");
    }

    public int getSpinnerSelection()
    {
        return sp.getInt(SPINNER_FIELD, 0);
    }

    public boolean getHelpedReceived()
    {
        return sp.getBoolean(HELPED_RECEIVED_FIELD, false);
    }

    public boolean savePreferences(String name, int spinnerSelection, boolean helpedReceived)
    {
        return sp.edit().putString(NAME_FIELD, name)
                .putInt(SPINNER_FIELD, spinnerSelection)
                .putBoolean(HELPED_RECEIVED_FIELD, helpedReceived)
                .commit();
    }
}
