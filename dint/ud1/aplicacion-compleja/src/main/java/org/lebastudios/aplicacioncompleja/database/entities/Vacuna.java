package org.lebastudios.aplicacioncompleja.database.entities;

public class Vacuna implements IEntity
{
    private final int codVacina;
    private final String nomeVacina;
    private final int numTotalDoses;

    Vacuna(int codVacina, String nomeVacina, int numTotalDoses)
    {
        this.codVacina = codVacina;
        this.nomeVacina = nomeVacina;
        this.numTotalDoses = numTotalDoses;
    }

    public Vacuna(String nomeVacina, int numTotalDoses)
    {
        this(-1, nomeVacina, numTotalDoses);
    }
}
