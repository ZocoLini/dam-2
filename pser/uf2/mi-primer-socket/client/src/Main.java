import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        try (Socket socket = new Socket("127.0.0.1", 8080))
        {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            String msg = "";

            while (!msg.equals("fin"))
            {
                msg = new Scanner(System.in).nextLine();

                if (socket.isClosed()) 
                {
                    System.out.println("Se ha cerrado la conexión");
                    return;
                }
                
                outputStream.writeUTF(msg);
                String receivedMsg = inputStream.readUTF();

                System.out.println(receivedMsg);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}