package org.lebastudios.aplicacioncompleja.database.entities;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PerroVacuna implements IEntity
{
    private final int codVacinacion;
    private final String chip;
    private final int codVacina;
    private final LocalDate data;
    private final String observaciones;
    private final int dose;

    private Perro perro;
    private Vacuna vacuna;
    
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

    @Override
    public void loadRelations()
    {
        
    }
    
    public static List<PerroVacuna> selectAllFrom(String dogChip)
    {
        List<PerroVacuna> vacunas = new ArrayList<>();
        
        return vacunas;
    }
}
