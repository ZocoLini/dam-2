package org.lebastudios.restapiclientexamen;

import lombok.Getter;

import java.io.IOException;
import java.net.Socket;

@Getter
public class Session
{
    private Socket socket;
    private final String username;
    private final int edad;

    public Session(Socket socket, String username, int edad)
    {
        this.socket = socket;
        this.username = username;
        this.edad = edad;
    }
    
    public boolean isConnected() {return socket != null && !socket.isClosed() && socket.isConnected();}

    public void disconnect() throws IOException
    {
        socket.close();
        socket = null;
    }

    public void newConnection(Socket socket)
    {
        if (this.isConnected())
        {
            throw new IllegalStateException("Que cierre primero el otro la conexion carajo");
        }

        this.socket = socket;
    }
}
