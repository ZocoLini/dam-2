package org.lebastudios.EJ2_A7UD3;

import org.lebastudios.Database;
import org.lebastudios.EJ1_A7UD3.Familiar;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main
{
    private static void insertarFamiliar(Familiar familiar, String NssEmpleado)
    {
        Database.getInstance().getConnections().forEach(connection ->
        {
            try (CallableStatement callableStatement = connection.prepareCall("{CALL pr_existeEmpleadoParaFamiliar(?," +
                    " ?, ?)}");
                 CallableStatement idFamiliarCall = connection.prepareCall("{? = CALL fn_obtenerIdFamiliar()}");
                 PreparedStatement statement = connection.prepareStatement("insert into Familiares (NSS_Empregado, " +
                         "Numero, NSS, Nome, " +
                         "Apelido1, Apelido2, DataNacemento, Parentesco, Sexo) values (?, ?, ?, ?, ?, ?, ?, ?, ?)"))
            {
                callableStatement.setString(1, NssEmpleado);
                callableStatement.setString(2, familiar.getNss());

                callableStatement.registerOutParameter(3, java.sql.Types.BIT);

                callableStatement.execute();

                if (callableStatement.getBoolean(3))
                {
                    throw new IllegalArgumentException("Este familiar xa está asociado a el empleado");
                }

                idFamiliarCall.registerOutParameter(1, java.sql.Types.SMALLINT);

                idFamiliarCall.execute();

                statement.setString(1, NssEmpleado);
                statement.setString(2, String.valueOf(idFamiliarCall.getShort(1)));
                statement.setString(3, familiar.getNss());
                statement.setString(4, familiar.getNome());
                statement.setString(5, familiar.getApelido1());
                statement.setString(6, familiar.getApelido2());
                statement.setDate(7, java.sql.Date.valueOf(familiar.getDataNacemento()));
                statement.setString(8, familiar.getParentesco());
                statement.setString(9, String.valueOf(familiar.getSexo()));
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
}
