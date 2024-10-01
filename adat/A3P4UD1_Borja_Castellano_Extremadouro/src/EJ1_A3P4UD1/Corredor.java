package EJ1_A3P4UD1;

import java.io.Serializable;

public record Corredor(String nombre, int dorsal, int tiempo, boolean borrado) implements Serializable
{

    public double getTiempo()
    {
        return tiempo;
    }

    public static Corredor fromString(String line)
    {
        String[] parts = line.split(",");
        return new Corredor(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Boolean.parseBoolean(parts[3]));
    }

    @Override
    public String toString()
    {
        return String.format("%s,%d,%d,%b", nombre, dorsal, tiempo, borrado);
    }
}
