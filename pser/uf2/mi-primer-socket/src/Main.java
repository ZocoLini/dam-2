import java.io.*;
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
        try
        {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            boolean exit = false;

            while (!exit)
            {
                String input = inputStream.readUTF();

                if (input.equals("fin"))
                {
                    exit = true;
                }

                if (input.equals("shut down")) 
                {
                    shutDownServer();
                }
                
                outputStream.writeUTF(input);
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