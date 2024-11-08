package org.lebastudios.aplicacioncompleja.database.entities;

import lombok.Getter;
import lombok.Setter;
import org.lebastudios.aplicacioncompleja.database.Database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    public PerroVacuna(PerroVacuna perroVacuna, String observaciones, LocalDate data)
    {
        this(perroVacuna.codVacinacion, perroVacuna.chip, perroVacuna.codVacina, data, observaciones, perroVacuna.dose);
    }
    
    @Override
    public void loadRelations()
    {
        if (vacuna != null || perro != null) return;
        
        vacuna = Vacuna.select(codVacina);
        perro = Perro.select(chip);
    }
    
    public boolean saveIncrementandoDosis()
    {
        if (codVacinacion == -1) throw new IllegalStateException("This entity doesn't exist in the database.");

        boolean[] saved = {true};

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE vacinacions SET dose = ?, data = ?, observacions = ? WHERE chip = ? and codVacina = ?");
                statement.setInt(1, dose + 1);
                statement.setDate(2, Date.valueOf(data));
                statement.setString(3, observaciones);
                statement.setString(4, chip);
                statement.setInt(5, codVacina);
                
                saved[0] = !statement.execute();
            }
            catch (SQLException e)
            {
                saved[0] = false;
            }
        });

        return saved[0];
    }
    
    public boolean insert()
    {
        if (codVacinacion != -1) throw new IllegalStateException("This entity already exist in the database.");

        boolean[] saved = {true};

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT vacinacions (chip, codVacina, data, observacions, dose) " +
                                "VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, chip);
                statement.setInt(2, codVacina);
                statement.setDate(3, Date.valueOf(data));
                statement.setString(4, observaciones);
                statement.setInt(5, dose);

                saved[0] = !statement.execute();
            }
            catch (SQLException e)
            {
                saved[0] = false;
            }
        });

        return saved[0];
    }
    
    public static PerroVacuna select(String chipPerro, int codVacina)
    {
        List<PerroVacuna> vacunas = new ArrayList<>();

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT codVacinacion, chip, codVacina, data, observacions, dose " +
                                "FROM vacinacions WHERE chip = ? and codVacina = ?");
                statement.setString(1, chipPerro);
                statement.setInt(2, codVacina);
                statement.execute();
                ResultSet result = statement.getResultSet();

                if (result.next())
                {
                    PerroVacuna vacuna = new PerroVacuna(
                            result.getInt(1),
                            result.getString(2),
                            result.getInt(3),
                            result.getDate(4).toLocalDate(),
                            result.getString(5),
                            result.getInt(6)
                    );

                    vacunas.add(vacuna);
                }
            }
            catch (SQLException e)
            {
                vacunas.clear();
            }
        });

        if (vacunas.isEmpty()) return null;
        
        return vacunas.getFirst();
    }
    
    public static List<PerroVacuna> selectAll(String dogChip)
    {
        List<PerroVacuna> vacunas = new ArrayList<>();

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT codVacinacion, chip, codVacina, data, observacions, dose " +
                                "FROM vacinacions WHERE chip = ?");
                statement.setString(1, dogChip);
                statement.execute();
                ResultSet result = statement.getResultSet();

                while (result.next())
                {
                    PerroVacuna vacuna = new PerroVacuna(
                            result.getInt(1),
                            result.getString(2),
                            result.getInt(3),
                            result.getDate(4).toLocalDate(),
                            result.getString(5),
                            result.getInt(6)
                    );

                    vacunas.add(vacuna);
                }
            }
            catch (SQLException e)
            {
                vacunas.clear();
            }
        });
        
        return vacunas;
    }
}
