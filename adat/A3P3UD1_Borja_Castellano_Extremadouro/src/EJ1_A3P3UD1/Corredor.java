package EJ1_A3P3UD1;

import java.io.Serializable;

public class Corredor implements Serializable
{
    private final int dorsal;
    private final String name;
    private final int seconds;

    public Corredor(String data)
    {
        String[] fields = data.split(" ");
        
        dorsal = Integer.parseInt(fields[0].trim());
        name = fields[1].trim();
        seconds = Integer.parseInt(fields[2].trim());
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
