package org.lebastudios;

import java.sql.SQLException;
import java.sql.Statement;

public class Main
{
    public static void main(String[] args)
    {
        String sql = 
                "create table LUGAR(\n" + 
                "  Nome_lugar varchar(35),\n" + 
                "  Num_departamento int,\n" + 
                "  constraint PK_LUGAR primary key (Nome_lugar),\n" + 
                "  constraint FK_LUGAR_DEPARTAMENTO foreign key (Num_departamento) references DEPARTAMENTO " +
                        "(Num_departamento)\n" + 
                ")";
        
        Database.getInstance().getConnections().forEach(session ->
        {
            try (Statement statement = session.createStatement())
            {
                statement.execute(sql);
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
        
        
    }
}