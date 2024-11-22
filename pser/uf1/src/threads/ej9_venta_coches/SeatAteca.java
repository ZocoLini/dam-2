package threads.ej9_venta_coches;

public class SeatAteca extends Seat
{
    public SeatAteca(int identificador)
    {
        super(identificador);
    }

    @Override
    protected String getModeloCoche()
    {
        return "Ateca";
    }
}
