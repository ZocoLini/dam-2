package org.lebastudios.aplicacioncompleja.database.entities;

import lombok.Getter;
import org.lebastudios.aplicacioncompleja.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Propietario implements IEntity
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

    public static Propietario select(String dniPropietario) 
    {
        List<Propietario> propietarios = new ArrayList<>();

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT dni, nome, ap1, ap2, eMail, tlf FROM propietarios " +
                                "WHERE dni = ?");
                statement.setString(1, dniPropietario);
                statement.execute();
                ResultSet result = statement.getResultSet();

                if (result.next())
                {
                    Propietario propietario = new Propietario(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            result.getString(6)
                    );

                    propietarios.add(propietario);
                }
            }
            catch (SQLException e)
            {
                propietarios.clear();
            }
        });
        
        if (propietarios.isEmpty()) return null;

        return propietarios.getFirst();
    }

    public boolean hasDogs()
    {
        boolean[] hasDogs = {false};
        
        Database.getInstance().connect(session ->
        {
            try
            {
                PreparedStatement statement = session.prepareStatement("SELECT chip from cans WHERE dniPropietario = ?");
                statement.setString(1, dni);
                
               hasDogs[0] = statement.executeQuery().next();
            }
            catch (SQLException e) {}

        });
        
        return hasDogs[0];
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
                        "INSERT propietarios (dni, nome, ap1, ap2, tlf, eMail) " +
                                "VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(1, dni);
                statement.setString(2, nome);
                statement.setString(3, ap1);
                statement.setString(4, ap2);
                statement.setString(5, tlf);
                statement.setString(6, email);
                statement.execute();

            }
            catch (SQLException e)
            {
                insertado[0] = false;
            }
        });

        return insertado[0];
    }

    public static List<Propietario> selectAll(String orderBy)
    {
        List<Propietario> propietarios = new ArrayList<>();

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT dni, nome, ap1, ap2, eMail, tlf FROM propietarios");
                statement.execute();
                ResultSet result = statement.getResultSet();
                
                while (result.next()) 
                {
                    Propietario propietario = new Propietario(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            result.getString(6)
                    );
                    
                    propietarios.add(propietario);
                }
            }
            catch (SQLException e)
            {
                propietarios.clear();
            }
        });

        return propietarios;
    }

    public static boolean exists(Propietario propietario)
    {
        return exists(propietario.dni);
    }

    public static boolean exists(String dniPropietario)
    {
        boolean[] insertado = {true};

        Database.getInstance().connect(connection ->
        {
            try
            {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT nome FROM propietarios where dni = ?");
                statement.setString(1, dniPropietario);
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

    @Override
    public final boolean equals(Object o)
    {
        if (!(o instanceof Propietario that)) return false;

        return Objects.equals(dni, that.dni) && Objects.equals(nome, that.nome) &&
                Objects.equals(ap1, that.ap1) && Objects.equals(ap2, that.ap2) &&
                Objects.equals(email, that.email) && Objects.equals(tlf, that.tlf);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hashCode(dni);
        result = 31 * result + Objects.hashCode(nome);
        result = 31 * result + Objects.hashCode(ap1);
        result = 31 * result + Objects.hashCode(ap2);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + Objects.hashCode(tlf);
        return result;
    }
}
