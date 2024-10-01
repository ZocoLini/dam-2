package EJ1_A3P3UD1;

import java.io.Serializable;

public class Corredor implements Serializable
{

    private final int dorsal;
    private final String name;
    private final int seconds;
    private final boolean borrado;

    public Corredor(String data, int dorsal, boolean borrado)
    {
        String[] fields = data.split(" ");
        
        this.dorsal = dorsal;
        name = fields[0].trim();
        seconds = Integer.parseInt(fields[1].trim());
        this.borrado = borrado;
    }

    public boolean isLast()
    {
        return name.equals("*");
    }
    
    public int getDorsal()
    {
        return dorsal;
    }

    public int getSeconds()
    {
        return seconds;
    }

    public String getName()
    {
        return name;
    }

    public boolean isBorrado()
    {
        return borrado;
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
