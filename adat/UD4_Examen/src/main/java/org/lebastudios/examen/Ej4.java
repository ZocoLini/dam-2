package org.lebastudios.examen;

import org.lebastudios.examen.database.Database;

public class Ej4
{
    public static void main(String[] args)
    {
        Database.getInstance().init();
        
        
        
        Database.getInstance().close();
    }
}
