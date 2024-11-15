package threads.ej7_apuestas;

import java.util.*;

public class Porra
{
    private static final int GOLES_MAX_DM2 = 5;
    private static final int GOLES_MAX_PRF = 10;
    private static final int GOLES_MIN_PRF = 2;

    private static Porra instance;

    public static Porra getInstance()
    {
        if (instance == null) instance = new Porra();

        return instance;
    }

    private Porra() 
    {
        Arrays.stream(TipoApuesta.values()).forEach(tipoApuesta -> dineroApostado.put(tipoApuesta, 0));
        Arrays.stream(TipoApuesta.values()).forEach(tipoApuesta -> numeroApostadores.put(tipoApuesta, new HashSet<>()));
    }

    private final HashMap<TipoApuesta, Integer> dineroApostado = new HashMap<>();
    private final HashMap<TipoApuesta, Set<Apostador>> numeroApostadores = new HashMap<>();
    private final List<Apuesta> apuestas = new ArrayList<>();
    private boolean apuestasAbiertas = true;

    public synchronized void apostar(Apuesta apuesta)
    {
        if (!apuestasAbiertas) return;

        apuestas.add(apuesta);
        
        final var tipoApuesta = apuesta.getTipoApuesta();
        final var cantidad = apuesta.getCantidad();
        
        dineroApostado.put(tipoApuesta, dineroApostado.get(tipoApuesta) + cantidad);
        numeroApostadores.computeIfAbsent(tipoApuesta, _ -> new HashSet<>()).add(apuesta.getApostador());
    }

    private ResultadoPartido generarResultado()
    {
        return new ResultadoPartido(
                (int) (Math.random() * (GOLES_MAX_DM2 + 1)), 
                (int) Math.min(GOLES_MAX_DM2, ((Math.random() * (GOLES_MAX_PRF - GOLES_MIN_PRF + 1)) + GOLES_MIN_PRF))
        );
    }

    public void finalizarPeriodoApuestas()
    {
        apuestasAbiertas = false;

        ResultadoPartido resultado = generarResultado();

        System.out.println("Goles DM2: " + resultado.getGolesDM2());
        System.out.println("Goles PRF: " + resultado.getGolesPRF());
        System.out.println();
        System.out.println("Dinero apostado: " + dineroApostado.values().stream().reduce(Integer::sum).orElse(1) + " €");
        System.out.println("\tGana DM2: " + dineroApostado.get(TipoApuesta.GANA_DM2) + " €");
        System.out.println("\tGana PRF: " + dineroApostado.get(TipoApuesta.GANA_PRF) + " €");
        System.out.println("\tEmpate: " + dineroApostado.get(TipoApuesta.EMPATE) + " €");

        TipoApuesta apuestaGanadora = apuestaGanadora(resultado);
        
        System.out.println();
        switch (apuestaGanadora)
        {
            case GANA_DM2 -> System.out.println("Gana DM2");
            case GANA_PRF -> System.out.println("Gana PRF");
            case EMPATE -> System.out.println("Empate");
        }

        int dineroARepartir = dineroApostado.entrySet().stream().reduce(
                0,
                (acc, entry) -> entry.getKey() == apuestaGanadora ? acc : acc + entry.getValue(), 
                Integer::sum
        );
        
        System.out.println("Dinero a repartir: " + dineroARepartir + " € entre " + numeroApostadores.get(apuestaGanadora).size() + " ganadores");
        System.out.println("Ha cada ganador le toca: " 
                + (float) dineroARepartir / numeroApostadores.get(apuestaGanadora).size() 
                + " €"
        );
    }
    
    private TipoApuesta apuestaGanadora(ResultadoPartido resultadoPartido)
    {
        if (resultadoPartido.getGolesDM2() > resultadoPartido.getGolesPRF()) return TipoApuesta.GANA_DM2;
        if (resultadoPartido.getGolesDM2() < resultadoPartido.getGolesPRF()) return TipoApuesta.GANA_PRF;
        return TipoApuesta.EMPATE;
    }
}
