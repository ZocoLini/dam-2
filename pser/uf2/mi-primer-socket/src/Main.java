import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main
{
    private static final int PORT = 8080;
    private static boolean SERVER_UP = true;
    
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    
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

            boolean exit = false;

            String username = inputStream.readUTF();
            
            Session session = sessions.get(username);
            if (session == null) 
            {
                session = new Session(socket, username);
                sessions.put(username, session);
            }
            else
            {
                if (session.isConnected()) session.disconnect();
                
                session.newConnection(socket);
            }
            
            outputStream.writeUTF("Session iniciada: " + username + " " + session.getLogins() + " veces connectado");
            
            while (!exit)
            {
                String input = inputStream.readUTF();
                outputStream.writeUTF(input);
                
                if (input.equals("fin"))
                {
                    exit = true;
                    session.disconnect();
                }

                if (input.equals("shut down")) 
                {
                    shutDownServer();
                }
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
}