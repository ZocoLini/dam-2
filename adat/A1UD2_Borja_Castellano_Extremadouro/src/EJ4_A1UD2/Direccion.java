package EJ4_A1UD2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Direccion
{
    private String calle = "";
    private int numero = -1;
    private String codigoPostal = "";

    public Direccion(String calle, int numero, String codigoPostal)
    {
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
    }

    public Direccion(Node node)
    {
        if (!node.getNodeName().equals("Direccion")) throw new IllegalArgumentException();

        for (int i = 0; i < node.getChildNodes().getLength(); i++)
        {
            Node child = node.getChildNodes().item(i);

            switch (child.getNodeName()) 
            {
                case "Calle":
                    this.calle = child.getTextContent();
                    break;
                case "Numero":
                    this.numero = Integer.parseInt(child.getTextContent());
                    break;
                case "CodigoPostal":
                    this.codigoPostal = child.getTextContent();
                    break;
                default:
            }
        }
    }

    public Node intoNode(Document document) 
    {
        Element root = document.createElement("Direccion");

        Element calleNode = document.createElement("Calle");
        calleNode.setTextContent(this.calle);

        Element numNode = document.createElement("Numero");
        numNode.setTextContent(String.valueOf(this.numero));

        Element codigoPostalNode = document.createElement("CodigoPostal");
        codigoPostalNode.setTextContent(this.codigoPostal);

        root.appendChild(calleNode);
        root.appendChild(numNode);
        root.appendChild(codigoPostalNode);

        return root;
    }

    public void save(RandomAccessFile raf) throws IOException
    {
        raf.writeUTF(calle);
        raf.writeInt(numero);
        raf.writeUTF(codigoPostal);
    }

    public static Direccion read(RandomAccessFile raf) throws IOException
    {
        String calle = raf.readUTF();
        int numero = raf.readInt();
        String codigoPostal = raf.readUTF();
        
        return new Direccion(calle, numero, codigoPostal);
    }

    public static Direccion generate() 
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Introduce la calle: ");
        String calle = scanner.nextLine();

        System.out.print("Introduce el numero: ");
        int numero = scanner.nextInt();

        scanner.nextLine();
        
        System.out.print("Introduce el codigo postal: ");
        String codigoPostal = scanner.nextLine();

        return new Direccion(calle, numero, codigoPostal);
    }
}
