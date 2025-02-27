package org.lebastudios.restapiclientexamen;

import org.lebastudios.restapiclientexamen.handles.Handle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        final Matcher requestMatcher = Pattern.compile("^(GET|POST|PUT|DELETE)\\s+(\\S+)$").matcher("");
        
        try (socket)
        {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            
            String request = inputStream.readUTF();
            Optional<String> response = Handle.handleRquest(request, requestMatcher);
            outputStream.writeUTF(response.orElse("Invalid request"));

            boolean exit = false;
            while (!exit && !socket.isClosed())
            {
                request = inputStream.readUTF();
                response = Handle.handleRquest(request, requestMatcher);
                outputStream.writeUTF(response.orElse("Invalid request"));
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
