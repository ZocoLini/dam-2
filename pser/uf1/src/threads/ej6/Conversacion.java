package threads.ej6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Conversacion
{
    private static final String ETIQUETA_END = "${END}";
    private static final String ETIQUETA_NOMBRE = "${NOMBRE}";
    private static final String ARCHIVO_CONVERSACION = "mensajes";
    
    private final Queue<String> queue = new ArrayDeque<>();

    public void comenzarConversacion()
    {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Main.class.getResourceAsStream(ARCHIVO_CONVERSACION))))
        {
            String linea;
            while ((linea = readConversationLine(reader)) != null)
            {
                queue.add(linea);
            }
        }
        catch (Exception exception)
        {
            return;
        }
        
        new Conversador(this, "Juan").start();
        new Conversador(this, "Miguel").start();
    }

    private String readConversationLine(BufferedReader reader) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        String linea;
        boolean end = false;

        while (!end)
        {
            linea = reader.readLine();

            if (linea == null)
            {
                if (sb.isEmpty()) return null;

                end = true;
                continue;
            }

            if (linea.endsWith(ETIQUETA_END))
            {
                end = true;
                linea = linea.substring(0, linea.length() - ETIQUETA_END.length());
            }

            sb.append(linea).append("\n");
        }

        return sb.toString();
    }

    public synchronized void conversar(String nombrePersona) throws InterruptedException
    {
        String mensaje = queue.poll();

        assert mensaje != null;
        
        mensaje = mensaje.replace(ETIQUETA_NOMBRE, nombrePersona);
        
        System.out.println(nombrePersona + ": " + mensaje);

        notify();
        
        if (!queue.isEmpty()) wait();
    }

    public boolean isOver()
    {
        return queue.isEmpty();
    }
}
