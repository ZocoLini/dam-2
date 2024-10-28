package org.lebastudios.aplicacioncompleja.database.entities;

public class Raza implements IEntity
{
    private final int codRaza;
    private final String descripcion;

    Raza(int codRaza, String descripcion)
    {
        this.codRaza = codRaza;
        this.descripcion = descripcion;
    }
    
    public Raza(String descripcion)
    {
        this(-1, descripcion);
    }

}
