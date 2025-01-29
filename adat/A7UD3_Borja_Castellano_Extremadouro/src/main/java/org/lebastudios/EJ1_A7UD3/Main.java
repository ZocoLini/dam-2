package org.lebastudios.EJ1_A7UD3;

import lombok.SneakyThrows;
import org.lebastudios.Database;

import java.sql.SQLException;
import java.sql.Statement;

public class Main
{
    @SneakyThrows
    public static void main(String[] args)
    {
        crearBaseDatos();
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
}
