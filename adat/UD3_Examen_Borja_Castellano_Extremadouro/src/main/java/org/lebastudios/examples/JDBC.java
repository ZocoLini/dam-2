package org.lebastudios.examples;

import lombok.SneakyThrows;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBC
{
    @SneakyThrows
    private void a()
    {
        Connection connection = null;

        // Disable auto commit
        connection.setAutoCommit(false);
        
        // Callable Statements
        CallableStatement callableStatement = connection.prepareCall("{? = call sp_insertar_empleado()}",
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Empleado", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        
        // Prepared Statements metadata
        preparedStatement.getMetaData().getColumnLabel(2);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        // ResultSet metadata
        resultSet.getMetaData().getColumnLabel(1);
        
        // Using result set dinamico
        resultSet.moveToInsertRow();
        resultSet.updateInt("id", 1);
        resultSet.updateString("nombre", "Pepe");
        resultSet.updateString("apellido1", "Perez");
        resultSet.insertRow();
        
        resultSet.moveToCurrentRow();
        
        // Prepared Statements using batch
        PreparedStatement preparedStatement2 = connection.prepareStatement("delete from Empleado where id = ?");
        
        preparedStatement2.setInt(1, 1);
        preparedStatement2.addBatch();
        
        preparedStatement2.setInt(1, 2);
        preparedStatement2.addBatch();
        
        preparedStatement2.executeBatch();
        
        // commit
        connection.commit();
    }
}
