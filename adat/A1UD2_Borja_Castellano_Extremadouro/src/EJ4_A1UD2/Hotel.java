package EJ4_A1UD2;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Hotel
{
    private int codigoHotel;
    private String nombre;
    private String fechaInauguaracion;
    private String telefono;
    private Direccion direccion;

    public Hotel(int codigoHotel, String nombre, String fechaInauguaracion, String telefono,
            Direccion direccion)
    {
        this.codigoHotel = codigoHotel;
        this.nombre = nombre;
        this.fechaInauguaracion = fechaInauguaracion;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Hotel(Node node)
    {
        if (!node.getNodeName().equals("Hotel")) throw new IllegalArgumentException();

        this.codigoHotel = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());
        
        for (int i = 0; i < node.getChildNodes().getLength(); i++) 
        {
            Node child = node.getChildNodes().item(i);
            
            switch (child.getNodeName()) 
            {
                case "Nombre":
                    this.nombre = child.getTextContent();
                    break;
                case "FechaInauguracion":
                    this.fechaInauguaracion = child.getTextContent();
                    break;
                case "Telefono":
                    this.telefono = child.getTextContent();
                    break;
                case "Direccion":
                    this.direccion = new Direccion(child);
                    break;
                default:
            }
        }
    }
    
    public Node intoNode(Document document)
    {
        Element root = document.createElement("Hotel");
        
        root.setAttribute("id", String.valueOf(this.codigoHotel));
        
        Element nameNode = document.createElement("Nombre");
        nameNode.setTextContent(this.nombre);
        
        Element telefonoNode = document.createElement("Telefono");
        telefonoNode.setTextContent(this.telefono);
        
        Node direccionNode = direccion.intoNode(document);
        
        root.appendChild(nameNode);
        root.appendChild(telefonoNode);
        root.appendChild(direccionNode);
        
        return root;
    }
    
    public void save(RandomAccessFile raf) throws IOException
    {
        raf.writeInt(codigoHotel);
        raf.writeUTF(nombre);
        raf.writeUTF(telefono);
        raf.writeUTF(fechaInauguaracion);
        direccion.save(raf);
    }
    
    public static Hotel read(RandomAccessFile raf) throws IOException
    {
        int codigoHotel = raf.readInt();
        String nombre = raf.readUTF();
        String telefono = raf.readUTF();
        String fechaInauguaracion = raf.readUTF();
        Direccion direccion = Direccion.read(raf);
        
        return new Hotel(codigoHotel, nombre, fechaInauguaracion, telefono, direccion);
    }
    
    public static Hotel generate()
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Introduce el codigo del hotel: ");
        int codigoHotel = scanner.nextInt();

        scanner.nextLine();
        
        System.out.print("Introduce el nombre del hotel: ");
        String nombre = scanner.nextLine();

        System.out.print("Introduce el telefono del hotel: ");
        String telefono = scanner.nextLine();

        System.out.print("Introduce la fecha de inauguracion: ");
        String fechaInauguaracion = scanner.nextLine();

        System.out.println("Introduce la direccion del hotel: ");
        Direccion direccion = Direccion.generate();

        return new Hotel(codigoHotel, nombre, fechaInauguaracion, telefono, direccion);
    }
}
