package org.lebastudios.aplicacioncompleja.database.entities;

import lombok.Getter;
import org.lebastudios.aplicacioncompleja.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Perro implements IEntity
{
    private final String chip;
    private final String nome;
    private final int codRaza;
    private final String dniPropietario;

    private Raza raza;
    private Propietario propietario;
    
    public Perro(String chip, String nome, int codRaza, String dniPropietario)
    {
        this.chip = chip;
        this.nome = nome;
        this.codRaza = codRaza;
        this.dniPropietario = dniPropietario;
    }

    @Override
    public void loadRelations()
    {
        if (raza != null || propietario != null) return;
        
        raza = Raza.select(codRaza);
        propietario = Propietario.select(dniPropietario);
    }

    public static Perro select(String chip) 
    {
        List<Perro> perros = new ArrayList<>();

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT chip, nome, codRaza, dniPropietario FROM cans " +
                                "WHERE chip = ?"
                );
                statement.setString(1, chip);
                statement.execute();
                ResultSet result = statement.getResultSet();

                if (result.next())
                {
                    Perro perro = new Perro(
                            result.getString(1),
                            result.getString(2),
                            result.getInt(3),
                            result.getString(4)
                    );

                    perros.add(perro);
                }
            }
            catch (SQLException e)
            {
                perros.clear();
            }
        });

        if (perros.isEmpty()) return null;
        
        return perros.getFirst();
    }
    
    public boolean insert()
    {
        if (exists(this)) throw new IllegalStateException("This instance already exists in the database.");

        boolean[] insertado = {true};

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT cans (chip, nome, dniPropietario) " +
                                "VALUES (?, ?, ?)");
                statement.setString(1, chip);
                statement.setString(2, nome);
                statement.setString(3, dniPropietario);
                statement.execute();

            }
            catch (SQLException e)
            {
                insertado[0] = false;
            }
        });

        return insertado[0];
    }

    public static List<Perro> selectAll(String orderBy)
    {
        return selectAll(orderBy, null);
    }

    public static List<Perro> selectAll(String orderBy, String dniPropietario) 
    {
        List<Perro> perros = new ArrayList<>();

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement;
                if (dniPropietario == null || dniPropietario.isBlank()) 
                {
                    statement = connection.prepareStatement(
                            "SELECT chip, nome, codRaza, dniPropietario FROM cans"
                    );
                }
                else
                {
                    statement = connection.prepareStatement(
                            "SELECT chip, nome, codRaza, dniPropietario FROM cans " +
                                    "WHERE dniPropietario = ?"
                    );
                    statement.setString(1, dniPropietario);
                }
                
                statement.execute();
                ResultSet result = statement.getResultSet();

                while (result.next())
                {
                    Perro perro = new Perro(
                            result.getString(1),
                            result.getString(2),
                            result.getInt(3),
                            result.getString(4)
                    );

                    perros.add(perro);
                }
            }
            catch (SQLException e)
            {
                perros.clear();
            }
        });

        return perros;
    }


    public static boolean exists(Perro perro)
    {
        return exists(perro.chip);
    }

    public static boolean exists(String chipPerro)
    {
        boolean[] insertado = {true};

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT nome FROM cans where chip = ?");
                statement.setString(1, chipPerro);
                statement.execute();
                insertado[0] = statement.getResultSet().first();

            }
            catch (SQLException e)
            {
                insertado[0] = false;
            }
        });

        return insertado[0];
    }
}
