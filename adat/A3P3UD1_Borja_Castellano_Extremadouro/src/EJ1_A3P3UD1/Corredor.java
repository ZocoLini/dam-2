package EJ1_A3P3UD1;

import java.io.Serializable;

public record Corredor(int dorsal, String name, int seconds, boolean borrado) implements Serializable
{
    public Corredor(String data, int dorsal, boolean borrado)
    {
        this(dorsal, data.split(" ")[0].trim(), Integer.parseInt(data.split(" ")[1].trim()), borrado);
    }

    public boolean isLast()
    {
        return name.equals("*");
    }

    public static Corredor generate(int dorsal)
    {
        System.out.println("Introduce el nombre del corredor: ");
        String name = new java.util.Scanner(System.in).nextLine();
        
        System.out.println("Introduce los segundos del corredor: ");
        int seconds = new java.util.Scanner(System.in).nextInt();
        
        return new Corredor(dorsal, name, seconds, false);
    }
    
    @Override
    public String toString()
    {
        return "Corredor{" +
                "dorsal=" + dorsal +
                ", name='" + name + '\'' +
                ", seconds=" + seconds +
                '}';
    }
}
