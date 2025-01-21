import java.io.IOException;
import java.net.Socket;

public class Session
{
    private Socket socket;
    private final String username;
    private int logins;

    public Session(Socket socket, String username)
    {
        this.socket = socket;
        this.username = username;
        this.logins = 1;
    }

    private void addLogin() {this.logins++;}

    public boolean isConnected() {return socket != null && socket.isConnected();}

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

        addLogin();
        this.socket = socket;
    }

    public String getUsername()
    {
        return username;
    }

    public int getLogins()
    {
        return logins;
    }
}
