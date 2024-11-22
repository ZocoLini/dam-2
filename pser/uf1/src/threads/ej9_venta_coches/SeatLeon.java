package threads.ej9_venta_coches;

public class SeatLeon extends Seat
{
    public SeatLeon(int identificador)
    {
        super(identificador);
    }

    @Override
    protected String getModeloCoche()
    {
        return "Leon";
    }
}
