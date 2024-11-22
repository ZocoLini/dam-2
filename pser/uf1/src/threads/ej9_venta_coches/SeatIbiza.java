package threads.ej9_venta_coches;

public class SeatIbiza extends Seat
{
    public SeatIbiza(int identificador)
    {
        super(identificador);
    }

    @Override
    protected String getModeloCoche()
    {
        return "Ibiza";
    }
}
