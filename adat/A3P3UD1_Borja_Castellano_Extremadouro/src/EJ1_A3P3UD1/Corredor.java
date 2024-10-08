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
