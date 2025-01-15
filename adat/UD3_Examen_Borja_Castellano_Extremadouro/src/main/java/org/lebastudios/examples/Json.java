package org.lebastudios.examples;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Json
{
    /* JsonObject: Representa un objeto JSON (un conjunto de pares clave-valor).
    ▪ JsonArray: Representa un array JSON (una lista ordenada de valores).
    ▪ JsonPrimitive: Representa valores primitivos JSON (como cadenas de texto, números y
    booleanos).
    ▪ JsonNull: Representa el valor null en JSON.
     */
    
    private static void a()
    {
        // Leer un JSON y guardar un DOM en memoria
        JsonElement jsonElement = JsonParser.parseString("{ \"nombre\": \"Juan\" }");
        
        System.out.println(jsonElement.getAsJsonObject());
        
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {}.getType();
        
        List<String> list = gson.fromJson("[\"a\", \"b\", \"c\"]", listType);
    }
}
