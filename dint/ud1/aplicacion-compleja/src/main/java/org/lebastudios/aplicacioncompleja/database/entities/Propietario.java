package org.lebastudios.aplicacioncompleja.database.entities;

import lombok.Getter;

@Getter
public class Propietario
{
   private final String dni;
    private final String nome;
    private final String ap1;
    private final String ap2;
    private final String email;
    private final String tlf;

    public Propietario(String dni, String nome, String ap1, String ap2, String email,
            String tlf)
    {
        this.dni = dni;
        this.nome = nome;
        this.ap1 = ap1;
        this.ap2 = ap2;
        this.email = email;
        this.tlf = tlf;
    }
    
}
