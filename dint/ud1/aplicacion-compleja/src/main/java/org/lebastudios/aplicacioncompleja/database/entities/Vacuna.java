package org.lebastudios.aplicacioncompleja.database.entities;

import lombok.Getter;
import org.lebastudios.aplicacioncompleja.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
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
    
    public boolean save()
    {
        return false;
    }
    
    public static Vacuna select(int codVacina)
    {
        List<Vacuna> vacunas = new ArrayList<>();

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT codVacina, nomeVacina, numTotalDoses FROM vacinas " +
                                "WHERE codVacina = ?");
                statement.setInt(1, codVacina);
                statement.execute();
                ResultSet result = statement.getResultSet();

                if (result.next())
                {
                    Vacuna vacuna = new Vacuna(
                            result.getInt(1),
                            result.getString(2),
                            result.getInt(3)
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
    
    public static List<Vacuna> selectAll(String orderBy)
    {
        List<Vacuna> vacunas = new ArrayList<>();

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT codVacina, nomeVacina, numTotalDoses FROM vacinas");
                statement.execute();
                ResultSet result = statement.getResultSet();

                while (result.next())
                {
                    Vacuna vacuna = new Vacuna(
                            result.getInt(1),
                            result.getString(2),
                            result.getInt(3)
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
