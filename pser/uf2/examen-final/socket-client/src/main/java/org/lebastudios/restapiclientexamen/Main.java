package org.lebastudios.restapiclientexamen;

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

            String msg;

            // Conexion inicial
            System.out.println("Introduce tu nombre: ");
            msg = new Scanner(System.in).nextLine();
            outputStream.writeUTF(msg);
            
            String receivedMsg = inputStream.readUTF();
            
            if (receivedMsg.equals("alreadyConnected")) 
            {
                System.out.println("Este usuario ya se encuentra conectado al servidor. Finalizando ejecución");
                return;
            }

            System.out.printf("Mensaje del servidor: %s\n", receivedMsg);
            if (!receivedMsg.equals("Welcome back"))
            {
                // Contestando a la pregunta
                msg = new Scanner(System.in).nextLine();
                outputStream.writeUTF(msg);

                String response = inputStream.readUTF();

                while (response.equals("Ese acontecimiento no es valido, pruebe otra vez"))
                {
                    System.out.println(response);
                    outputStream.writeUTF(new Scanner(System.in).nextLine());
                    response = inputStream.readUTF();
                }

                System.out.println(response);
                System.out.println("El servidor ya conoce tu edad, puede continuar.");
            }
            
            while (!msg.equals("salir"))
            {
                System.out.println("Inserte un año, edad o nombre de usuario");
                msg = new Scanner(System.in).nextLine();
                
                if (socket.isClosed())
                {
                    System.out.println("Se ha cerrado la conexión");
                    return;
                }

                outputStream.writeUTF(msg);
                receivedMsg = inputStream.readUTF();

                System.out.println(receivedMsg);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
