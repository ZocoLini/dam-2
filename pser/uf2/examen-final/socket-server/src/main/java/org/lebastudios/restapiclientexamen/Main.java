package org.lebastudios.restapiclientexamen;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main
{
    private static final int PORT = 8080;
    private static boolean SERVER_UP = true;
    
    public static void main(String[] args)
    {
        try (ServerSocket serverSocket = new ServerSocket(PORT))
        {
            while (SERVER_UP)
            {
                Socket socket = serverSocket.accept();
                new Thread(() -> talkWithAClient(socket)).start();
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void talkWithAClient(Socket socket)
    {
        try (socket)
        {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            String username = inputStream.readUTF();

            Session session = SessionManager.getInstance().getSession(username);
            if (session != null) 
            {
                if (session.isConnected()) 
                {
                    outputStream.writeUTF("alreadyConnected");
                }
                else
                {
                    outputStream.writeUTF("Welcome back");
                    session.newConnection(socket);
                }
            }
            else
            {
                session = SessionManager.getInstance().startANewSession(socket, outputStream, inputStream, username);
            }
            
            boolean exit = false;
            while (!exit && !socket.isClosed())
            {
                String input = inputStream.readUTF();
                
                if (input.equals("salir")) 
                {
                    outputStream.writeUTF("Mensaje de cierre recibido, cerrando...");
                    session.disconnect();
                    exit = true;
                    continue;
                }
                
                outputStream.writeUTF(processRequest(input));
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    private static void shutDownServer()
    {
        SERVER_UP = false;
    }
    
    private static String processRequest(String request)
    {
        if (request.matches("\\d{2}")) 
        {
            return SessionManager.getInstance().usuariosConEdad(Integer.parseInt(request));
        }
        
        if (request.matches("\\d{4}")) 
        {
            return SessionManager.getInstance().usuariosNacidosEn(Integer.parseInt(request));
        }
        
        return SessionManager.getInstance().edadDelUsuario(request);
    }
}
