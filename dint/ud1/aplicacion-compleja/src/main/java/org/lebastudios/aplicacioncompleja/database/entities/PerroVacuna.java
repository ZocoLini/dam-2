package org.lebastudios.aplicacioncompleja.database.entities;

import java.time.LocalDate;

public class PerroVacuna
{
    private final int codVacinacion;
    private final String chip;
    private final int codVacina;
    private final LocalDate data;
    private final String observaciones;
    private final int dose;

    PerroVacuna(int codVacinacion, String chip, int codVacina, LocalDate data, String observaciones, int dose)
    {
        this.codVacinacion = codVacinacion;
        this.chip = chip;
        this.codVacina = codVacina;
        this.data = data;
        this.observaciones = observaciones;
        this.dose = dose;
    }

    public PerroVacuna(String chip, int codVacina, LocalDate data, String observaciones, int dose)
    {
        this(-1, chip, codVacina, data, observaciones, dose);
    }
}
