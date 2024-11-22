package threads.ej9_venta_coches;

public abstract class Seat
{
    private int contadorVecesVisto = 0;
    private final int identificador;

    public Seat(int identificador)
    {
        this.identificador = identificador;
    }

    public void aumentarVecesVisto()
    {
        contadorVecesVisto++;
    }

    public int getContadorVecesVisto()
    {
        return contadorVecesVisto;
    }

    protected abstract String getModeloCoche();

    public String getIdCoche()
    {
        return "Ibiza " + getModeloCoche() + " #" + identificador;
    }
}
