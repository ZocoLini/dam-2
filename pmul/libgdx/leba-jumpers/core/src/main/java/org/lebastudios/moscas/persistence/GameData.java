package org.lebastudios.moscas.persistence;

import lombok.Getter;
import org.lebastudios.moscas.game.GameState;

import java.util.HashMap;

public class GameData
{
    private static GameData instance;

    public static GameData getInstance()
    {
        if (instance == null) instance = new GameData();

        return instance;
    }

    @Getter private HashMap<Integer, Integer> records;

    private GameData()
    {
        load();
    }

    private void load()
    {
        // TODO: Load records from a file
        records = new HashMap<>(9);
    }

    public void reset()
    {
        records.clear();
    }

    public void registerNewScore(int seconds)
    {
        final var numInsectos = GameState.getInstance().getNumInsectos();
        Integer oldRecord = records.get(numInsectos);

        if (oldRecord != null && oldRecord <= seconds) return;

        records.put(numInsectos, seconds);
    }
}
