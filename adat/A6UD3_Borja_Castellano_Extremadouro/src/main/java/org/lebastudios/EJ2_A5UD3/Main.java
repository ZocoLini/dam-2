package org.lebastudios.EJ2_A5UD3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main
{
    private static final String DB_NAME = "BDEmpresa";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "abc123.";

    public static void main(String[] args)
    {
        try (Connection con = DriverManager.getConnection(
                String.format("jdbc:sqlserver://localhost\\MV-PROG:1433;database=%s;trustServerCertificate=true",
                        DB_NAME),
                DB_USER, DB_PASS))
        {
            con.setAutoCommit(false);

            try (Statement statement = con.createStatement())
            {
                createTables(statement);
                con.commit();
            }


        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void createTables(Statement statement) throws SQLException
    {
        statement.addBatch("drop table if exists VehiculoRenting");
        statement.addBatch("drop table if exists VehiculoPropio");
        statement.addBatch("drop table if exists Vehiculo");
        
        statement.addBatch("""
create table Vehiculo
(
    id int identity(1, 1),
    matricula char(7) not null,
    marca varchar(50) not null,
    modelo varchar(50) not null,
    combustible char(1) not null,
    constraint PK_VEHICULO primary key (id),
    constraint CK_MATRICULA_VEHICULO check (matricula like '[0-9][0-9][0-9][0-9] [A-Z][A-Z][A-Z]'),
    constraint CK_COMBUSTIBLE_VEHICULO check (combustible in ('G', 'D')),
    constraint UQ_MATRICULA_VEHICULO unique (matricula)
)
                """);
        
        statement.addBatch("""
create table VehiculoRenting
(
    id int,
    fecha_inicio timestamp not null,
    precio_mensual decimal(10, 2) not null,
    meses_alquilado int not null,
    constraint PK_VEHICULO_RENTING primary key (id),
    constraint FK_VEHICULO_RENTING_VEHICULO foreign key (id) references Vehiculo (id)
)""");
        
        statement.addBatch("""
create table VehiculoPropio
(
    id int,
    fecha_compra timestamp not null,
    precio_compra decimal(10, 2) not null,
    constraint PK_VEHICULO_PROPIO primary key (id),
    constraint FK_VEHICULO_PROPIO_VEHICULO foreign key (id) references Vehiculo (id)
)""");
        statement.executeBatch();
    }
}
