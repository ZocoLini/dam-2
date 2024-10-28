package org.lebastudios.aplicacioncompleja.database.entities;

public class Perro
{
    private final String chip;
    private final String nome;
    private final int codRaza;
    private final String dniPropietario;

    public Perro(String chip, String nome, int codRaza, String dniPropietario)
    {
        this.chip = chip;
        this.nome = nome;
        this.codRaza = codRaza;
        this.dniPropietario = dniPropietario;
    }
}
