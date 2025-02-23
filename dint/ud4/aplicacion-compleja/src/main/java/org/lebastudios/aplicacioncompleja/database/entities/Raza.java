package org.lebastudios.aplicacioncompleja.database.entities;

import lombok.Getter;
import org.lebastudios.aplicacioncompleja.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
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

    public static Raza select(int codRaza) 
    {
        List<Raza> razas = new ArrayList<>();

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT codRaza, descripcion FROM razas " +
                                "WHERE codRaza = ?");
                statement.setInt(1, codRaza);
                statement.execute();
                ResultSet result = statement.getResultSet();

                if (result.next())
                {
                    Raza raza = new Raza(
                            result.getInt(1),
                            result.getString(2)
                    );

                    razas.add(raza);
                }
            }
            catch (SQLException e)
            {
                razas.clear();
            }
        });

        if (razas.isEmpty()) return null;
        
        return razas.getFirst();
    }

    public boolean insert()
    {
        if (codRaza != -1) throw new IllegalStateException("This instance already exists in the database.");
        
        boolean[] insertado = {true};
        
        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement("INSERT razas (descripcion) VALUES (?)");
                statement.setString(1, descripcion);
                statement.execute();
                
            }
            catch (SQLException e)
            {
                insertado[0] = false;
            }
        });
        
        return insertado[0];
    }

    public static List<Raza> selectAll(String orderBy)
    {
        List<Raza> razas = new ArrayList<>();

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT codRaza, descripcion FROM razas");
                statement.execute();
                ResultSet result = statement.getResultSet();

                while (result.next())
                {
                    Raza raza = new Raza(
                            result.getInt(1),
                            result.getString(2)
                    );

                    razas.add(raza);
                }
            }
            catch (SQLException e)
            {
                razas.clear();
            }
        });

        return razas;
    }
}
