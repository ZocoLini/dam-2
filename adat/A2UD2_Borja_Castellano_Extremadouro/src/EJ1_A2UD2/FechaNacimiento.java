package EJ1_A2UD2;

public class FechaNacimiento
{
    private String format;
    private String fecha;

    void setFormat(String format)
    {
        this.format = format;
    }

    void setFecha(String fecha)
    {
        this.fecha = fecha;
    }

    @Override
    public String toString()
    {
        return fecha;
    }
}
