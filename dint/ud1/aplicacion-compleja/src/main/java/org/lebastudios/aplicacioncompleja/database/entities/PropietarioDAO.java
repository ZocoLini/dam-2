package org.lebastudios.aplicacioncompleja.database.entities;

import org.lebastudios.aplicacioncompleja.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PropietarioDAO
{
    //(C)RUD propietarios
    public static boolean eliminarUnPropietarioDadoSeuDni(String dni)
    {
        boolean[] result = new boolean[1];
        
        Database.getInstance().connect(session ->
        {
            try
            {
                Statement stmt = session.createStatement();
                String consulta = "delete from propietarios where dni='" + dni + "'";
                stmt.executeUpdate(consulta);
                result[0] = true;
            }
            catch (SQLException e)
            {
                result[0] = false;
            }
        });
        
        return result[0];
    }

    //Recuperar un propietario dado o seu DNI
    public static Propietario recuperarPropietario(String dniPropietario)
    {
        Propietario[] propietario = new Propietario[1];
        
        Database.getInstance().connect(session ->
        {
            try
            {
                Statement stmt = session.createStatement();
                String consulta = "select * from propietarios where dni='" + dniPropietario + "'";
                ResultSet rs = stmt.executeQuery(consulta);
                rs.next();
                Propietario p =
                        new Propietario(rs.getString("dni"), rs.getString("nome"), rs.getString("ap1"), rs.getString("ap2"),
                                rs.getString("tlf"), rs.getString("eMail"));
                propietario[0] = p;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        });
        
        return propietario[0];
    }

    public static boolean actualizarPropietario(String dniAntiguo, Propietario c)
    {
        boolean[] result = new boolean[1];

        Database.getInstance().connect(session ->
        {
            try
            {
                Statement stmt = session.createStatement();
                String consulta =
                        "UPDATE propietarios SET dni = '" + c.getDni() + "', nome = '" + c.getNome() + "', ap1 = '" +
                                c.getAp1() + "', ap2 = '" + c.getAp2() + "', tlf = '" + c.getTlf() + "', eMail = '" +
                                c.getEmail() + "' WHERE dni = '" + dniAntiguo + "'";
                stmt.executeUpdate(consulta);
                result[0] = true;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                result[0] = false;
            }
        });

        return result[0];
    }

}
