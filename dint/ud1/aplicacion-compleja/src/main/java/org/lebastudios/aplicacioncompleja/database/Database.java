package org.lebastudios.aplicacioncompleja.database;

import org.lebastudios.aplicacioncompleja.events.AppLifeCicleEvents;

public class Database
{
    private static Database instance;

    private Database()
    {
        AppLifeCicleEvents.OnAppCloseRequest.addListener(() -> {System.out.println("Hola");});
    }

    public static Database getInstance()
    {
        if (instance == null) instance = new Database();

        return instance;
    }

    private void buildSessionFactory()
    {
        
    }

    public void connectTransaction()
    {
        
    }

    public void connectQuery()
    {
        
    }
}
