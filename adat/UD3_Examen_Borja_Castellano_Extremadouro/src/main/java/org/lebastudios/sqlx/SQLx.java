package org.lebastudios.sqlx;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLx
{
    private static <T> T constructClassFromResultSet(Class<T> clazz, ResultSet resultSet) throws SQLException
    {
        try
        {
            var instance = clazz.getConstructor().newInstance();
            
            for (var field : clazz.getDeclaredFields())
            {
                if (field.getAnnotation(IgnoreField.class) != null) continue;
                
                String columnName = field.getAnnotation(Column.class) == null 
                        ? field.getName()
                        : field.getAnnotation(Column.class).name();
                
                final var method = clazz.getMethod(
                        "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1),
                        field.getType()
                );
                
                method.invoke(instance, resultSet.getObject(columnName));
            }

            return instance;
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            throw new RuntimeException("Could not instantiate class", e);
        }
    }
    
    public static <T> T query(String query, Connection connection, Class<T> clazz) throws SQLException
    {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        if (resultSet.next()) 
        {
            return constructClassFromResultSet(clazz, resultSet);
        }
        else
        {
            return null;
        }
    }

    public static <T> List<T> queryAll(String query, Connection connection, Class<T> clazz) throws SQLException
    {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<T> items = new ArrayList<>();
        
        while (resultSet.next())
        {
            items.add(constructClassFromResultSet(clazz, resultSet));
        }
        
        return items;
    }
}
