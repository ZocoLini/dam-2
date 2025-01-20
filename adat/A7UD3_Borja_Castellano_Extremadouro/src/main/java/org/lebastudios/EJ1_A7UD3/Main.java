package org.lebastudios.EJ1_A7UD3;

import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.lebastudios.Database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Main
{
    @SneakyThrows
    public static void main(String[] args)
    {
        //crearBaseDatos();
        
        Empleados empleados = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create()
                .fromJson(Files.readString(Path.of("insercionEmpleados.json")),
            Empleados.class);
        
        empleados.getEmpleados().forEach(empleado ->
        {
            empleado.getFamiliares().forEach(f -> System.out.println(f.getApelido1()));
        });
    }

    private static void crearBaseDatos()
    {
        Database.getInstance().getConnections().forEach(connection ->
        {
            try (Statement statement = connection.createStatement())
            {
                statement.addBatch("""
                        drop table if exists Familiares""");

                statement.addBatch("""
                        create table Familiares
                        (
                            NSS_Empregado varchar(15) not null,
                            Numero smallint not null,
                            NSS varchar(15) not null,
                            Nome varchar(25) not null,
                            Apelido1 varchar(25) not null,
                            Apelido2 varchar(25),
                            DataNacemento date,
                            Parentesco varchar(20) not null,
                            Sexo char(1) not null default 'M'
                        )""");
           
                statement.addBatch("""
                        alter table Familiares
                            add constraint PK_Familiares primary key (Numero)""");

                statement.addBatch("""
                        alter table Familiares
                            add constraint FK_Familiares_Empregado foreign key (NSS_Empregado) references Empregados(NSS)""");

                statement.addBatch("""
                        alter table Familiares
                            add constraint CK_Familiares_Sexo check (Sexo in ('H', 'M'))""");

                statement.addBatch("""
                        alter table Familiares
                            add constraint UQ_Familiares_NSS_Empregado unique (NSS_Empregado, NSS)""");

                statement.executeBatch();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
    
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
