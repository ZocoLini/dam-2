package threads.ej6_conversacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Conversacion
{
    public static final String ETIQUETA_END = "${END}";
    private static final String ETIQUETA_NOMBRE = "${NOMBRE}";
    private static final String ARCHIVO_CONVERSACION = "mensajes";
    
    private BufferedReader reader =  new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(ARCHIVO_CONVERSACION)));

    public void comenzarConversacion()
    {
        new Conversador(this, "Juan").start();
        new Conversador(this, "Miguel").start();
    }

    private String readConversationLine() throws IOException
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

    public synchronized void conversar(String nombrePersona) throws InterruptedException, IOException
    {
        String mensaje = readConversationLine();
        
        if (mensaje == null) 
        {
            reader.close();
            reader = null;
            notify();
            return;
        }
        
        mensaje = mensaje.replace(ETIQUETA_NOMBRE, nombrePersona);
        
        System.out.println(nombrePersona + ": " + mensaje);

        notify();
        wait();
    }

    public boolean isOver()
    {
        return reader == null;
    }
}
