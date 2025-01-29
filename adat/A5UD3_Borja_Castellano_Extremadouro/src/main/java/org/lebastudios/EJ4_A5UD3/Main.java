package org.lebastudios.EJ4_A5UD3;

import org.lebastudios.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main
{
    public static void main(String[] args)
    {

    }

    public static void eliminarProyecto(int numeroProyecto) throws SQLException
    {
        Connection conn = Database.getInstance().getConnections().getFirst();

        conn.setAutoCommit(false);

        try (PreparedStatement psEliminarEmpleadoProyecto = conn.prepareStatement(
                "DELETE FROM Empleado_Proyecto WHERE id = ?");
             PreparedStatement psEliminarProyecto = conn.prepareStatement("DELETE FROM Proyecto WHERE id = ?"))
        {
            psEliminarEmpleadoProyecto.setInt(1, numeroProyecto);
            psEliminarEmpleadoProyecto.executeUpdate();

            psEliminarProyecto.setInt(1, numeroProyecto);
            psEliminarProyecto.executeUpdate();

            // Confirmar la transacción
            conn.commit();
            System.out.println("El proyecto y sus relaciones fueron eliminados correctamente.");
        }
        catch (SQLException e)
        {
            conn.rollback();
        }
    }
}
