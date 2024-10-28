package org.lebastudios.aplicacioncompleja.database.entities;

import lombok.Getter;

@Getter
public class Propietario
{
    private final int id;
    private final String dni;
    private final String name;
    private final String apellido1;
    private final String apellido2;
    private final String email;
    private final String telefono;

    Propietario(int id, String dni, String name, String apellido1, String apellido2, String email,
            String telefono)
    {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.telefono = telefono;
    }

    public Propietario(String dni, String name, String apellido1, String apellido2, String email,
            String telefono)
    {
        this(-1, dni, name, apellido1, apellido2, email, telefono);
    }
    
}
