package threads.ej9_venta_coches;

public class Cliente extends Thread
{
    private final int identificador;

    public Cliente(int identificador)
    {
        this.identificador = identificador;
    }

    public String getIdentificador()
    {
        return "CLiente #" + identificador;
    }

    @Override
    public void run()
    {
        Seat seat;
        
        while ((seat = Concesionario.getInstance().pedirCitaParaVerUnCoche()) != null)
        {
            try
            {
                sleep((long) (Math.random() * 20));
            }
            catch (Exception exception) {throw new Error(exception);}

            if ((int) (Math.random() * 100) < seat.getContadorVecesVisto())
            {
                Concesionario.getInstance().dejarDeVerUnCocheComprando(seat, this);
                break;
            }
            else
            {
                Concesionario.getInstance().dejarDeVerUnCocheSinComprar(seat);
            }
        }
    }
}
