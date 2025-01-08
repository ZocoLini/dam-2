package org.lebastudios.aplicacioncompleja.database.entities;

import java.time.LocalDate;

public class Operacion implements IEntity
{
    private final int codOperacion;
    private final String chip;
    private final String descripcion;
    private final LocalDate data;
    private final int anestesia;
    private final int raios;
    private final int sangue;
    private final int scaner;

    private Perro perro;
    
    Operacion(int codOperacion, String chip, String descripcion, LocalDate data, int anestesia, int raios,
            int sangue, int scaner)
    {
        this.codOperacion = codOperacion;
        this.chip = chip;
        this.descripcion = descripcion;
        this.data = data;
        this.anestesia = anestesia;
        this.raios = raios;
        this.sangue = sangue;
        this.scaner = scaner;
    }

    public Operacion(String chip, String descripcion, LocalDate data, int anestesia, int raios, int sangue, int scaner)
    {
        this(-1, chip, descripcion, data, anestesia, raios, sangue, scaner);
    }

    @Override
    public void loadRelations()
    {
        
    }
}
