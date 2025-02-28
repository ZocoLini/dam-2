package org.lebastudios.restapiclientexamen.httpbodies;

import java.lang.reflect.Field;

public class URLEncoder
{
    public static String encode(Object object)
    {
        try
        {
            Class<?> clazz = object.getClass();

            StringBuilder sb = new StringBuilder();

            for (Field field : clazz.getDeclaredFields())
            {
                if (field.getAnnotation(IgnoreURLEncode.class) != null) continue;
                
                String fieldName = field.getName();
                field.setAccessible(true);
                Object fieldValue = field.get(object);
                String fieldValueString = fieldValue == null ? "" : fieldValue.toString();

                sb.append(fieldName).append("=").append(fieldValueString).append("&");
            }
            
            sb.deleteCharAt(sb.length() - 1);
            
            return sb.toString();
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception);
        }
    }
}
