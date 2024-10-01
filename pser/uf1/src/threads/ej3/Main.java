package threads.ej3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main
{
    private static final int CENSO = 10000;
    private static final int NUM_PARTIDOS = 5;

    public static void main(String[] args)
    {
        AtomicInteger[] votos = new AtomicInteger[NUM_PARTIDOS];
        List<Votante> votantes = new ArrayList<>();

        for (int i = 0; i < NUM_PARTIDOS; i++)
        {
            votos[i] = new AtomicInteger();
        }

        for (int i = 0; i < CENSO; i++)
        {
            var votante = new Votante(votos);
            votante.start();
            votantes.add(votante);
        }

        try
        {
            for (var votante : votantes)
            {
                votante.join();
            }
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        int sum = votos[0].get();
        List<Integer> winners = new ArrayList<>();
        winners.add(0);
        for (int i = 1; i < NUM_PARTIDOS; i++)
        {
            var value = votos[i].get();
            sum += value;

            if (value > votos[winners.getFirst()].get())
            {
                winners.clear();
                winners.add(i);
            } else if (value == winners.getFirst())
            {
                winners.add(i);
            }
        }

        System.out.println(winners.size() == 1 ? "Ganador: " : "Empate entre: ");
        for (var winner : winners)
        {
            System.out.println("\tPartido " + winner + " con " + votos[winner].get() + " votos");
        }

        System.out.println("Votos totales: " + sum);
    }
}
