package threads.ej3;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    private static final int CENSO = 10000;

    private static final Partido[] partidos =
            {
                    new Partido("PP"),
                    new Partido("PSOE"),
                    new Partido("VOX"),
                    new Partido("SUMAR"),
            };

    public static void main(String[] args)
    {
        List<Votante> votantes = new ArrayList<>();

        for (int i = 0; i < CENSO; i++)
        {
            var votante = new Votante(partidos);
            votante.start();
            votantes.add(votante);
        }

        try
        {
            for (var votante : votantes) votante.join();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }


        int sum = partidos[0].getVotos();
        List<Integer> winners = new ArrayList<>();
        winners.add(0);

        for (int i = 1; i < partidos.length; i++)
        {
            var value = partidos[i].getVotos();
            final var actualWinnerCount = partidos[winners.getFirst()].getVotos();
            sum += value;

            if (value > actualWinnerCount)
            {
                winners.clear();
                winners.add(i);
            }
            else
            {
                if (value == actualWinnerCount)
                {
                    winners.add(i);
                }
            }
        }

        System.out.println(winners.size() == 1 ? "Ganador: " : "Empate entre: ");
        for (var winner : winners)
        {
            final var partido = partidos[winner];

            System.out.println("\tEl partido " + partido.getNombre() + " con " + partido.getVotos() + " " +
                    "partidos");
        }

        System.out.println("Votos totales: " + sum);
    }
}
