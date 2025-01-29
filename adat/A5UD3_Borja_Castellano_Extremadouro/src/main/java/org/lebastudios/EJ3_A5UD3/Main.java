package org.lebastudios.EJ3_A5UD3;

import org.lebastudios.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main
{
    public static void main(String[] args)
    {
        
    }

    public static void gestionarDepartamento(String departamentoAEliminar, String departamentoReasignar)
            throws SQLException
    {
        Connection conn = Database.getInstance().getConnections().getFirst();
        
        conn.setAutoCommit(false);
        
        try (PreparedStatement psReasignarProyectos = conn.prepareStatement("UPDATE PROYECTO SET departamento_id = " +
                "(SELECT id FROM DEPARTAMENTO WHERE nombre = ?) WHERE departamento_id = " +
                        "(SELECT id FROM DEPARTAMENTO WHERE nombre = ?))");
             PreparedStatement psReasignarEmpleados = conn.prepareStatement("UPDATE PROYECTO SET departamento_id = \" +\n" +
                     "                    \"(SELECT id FROM DEPARTAMENTO WHERE nombre = ?) WHERE departamento_id = \" +\n" +
                     "                    \"(SELECT id FROM DEPARTAMENTO WHERE nombre = ?)");
             PreparedStatement psEliminarDepartamento = conn.prepareStatement("DELETE FROM DEPARTAMENTO WHERE nombre = ?")) {

            psReasignarProyectos.setString(1, departamentoReasignar);
            psReasignarProyectos.setString(2, departamentoAEliminar);
            psReasignarProyectos.executeUpdate();

            psReasignarEmpleados.setString(1, departamentoReasignar);
            psReasignarEmpleados.setString(2, departamentoAEliminar);
            psReasignarEmpleados.executeUpdate();

            psEliminarDepartamento.setString(1, departamentoAEliminar);
            psEliminarDepartamento.executeUpdate();

            conn.commit();
            System.out.println("El departamento fue eliminado y los datos reasignados correctamente.");

        } catch (SQLException e) {
            conn.rollback();
        }
    }
}
