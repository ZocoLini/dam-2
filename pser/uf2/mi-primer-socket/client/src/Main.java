import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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
                
                outputStream.writeUTF(msg);
                String receivedMsg = inputStream.readUTF();

                System.out.println(receivedMsg);
            }
        }
        catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}