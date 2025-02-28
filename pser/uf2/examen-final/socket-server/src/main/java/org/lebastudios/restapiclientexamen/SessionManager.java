package org.lebastudios.restapiclientexamen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SessionManager
{
    private static SessionManager instance;

    public static SessionManager getInstance()
    {
        if (instance == null) instance = new SessionManager();

        return instance;
    }

    private static final Map<String, Integer> acontecimientos = Map.of(
            "torres", 2001,
            "mundial", 2010,
            "luna", 1969,
            "Berlin", 1989
    );

    private final HashMap<String, Session> sesiones = new HashMap<>();

    private SessionManager() {}

    public Session getSession(String username)
    {
        return sesiones.get(username);
    }

    public Session startANewSession(Socket socket, DataOutputStream outputStream, DataInputStream inputStream,
            String username)
            throws IOException
    {
        if (sesiones.containsKey(username))
        {
            throw new RuntimeException("Ya existia una sesion para este usuario");
        }

        int edad;

        switch ((int) (Math.random() * 3))
        {
            case 0 ->
            {
                outputStream.writeUTF("En que año has nacido?");
                edad = LocalDate.now().getYear() - Integer.parseInt(inputStream.readUTF());
            }
            case 1 ->
            {
                outputStream.writeUTF("Que edad tienes?");
                edad = Integer.parseInt(inputStream.readUTF());
            }
            case 2 ->
            {
                outputStream.writeUTF("Acontecimiento currido en tu año de nacimiento?");
                Integer fechaAcontecimiento = acontecimientos.get(inputStream.readUTF());

                while (fechaAcontecimiento == null)
                {
                    outputStream.writeUTF("Ese acontecimiento no es valido, pruebe otra vez");
                    fechaAcontecimiento = acontecimientos.get(inputStream.readUTF());
                }
                
                edad = LocalDate.now().getYear() - fechaAcontecimiento;
            }
            default -> throw new RuntimeException("No deberia suceder nunca");
        }

        outputStream.writeUTF("Connected");
        Session session = new Session(socket, username, edad);
        synchronized (sesiones)
        {
            sesiones.put(username, session);
        }

        return session;
    }

    public String usuariosNacidosEn(int year)
    {
        StringBuilder sb = new StringBuilder();
        sesiones.values().stream()
                .filter(session -> session.getEdad() == LocalDate.now().getYear() - year)
                .forEach(session -> sb.append(session.getUsername()).append("\n"));
        
        return sb.toString();
    }

    public String usuariosConEdad(int edad)
    {
        StringBuilder sb = new StringBuilder();
        sesiones.values().stream()
                .filter(session -> session.getEdad() == edad)
                .forEach(session -> sb.append(session.getUsername()).append("\n"));
        
        return sb.toString();
    }

    public String edadDelUsuario(String nombreUsuario)
    {
        if (!sesiones.containsKey(nombreUsuario)) return "No conozco a ese susario";
        
        return String.valueOf(sesiones.get(nombreUsuario).getEdad());
    }
}
