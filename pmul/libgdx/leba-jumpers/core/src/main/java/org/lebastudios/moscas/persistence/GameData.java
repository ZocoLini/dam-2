package org.lebastudios.moscas.persistence;

import java.util.HashMap;

public class GameData
{
    private static GameData instance;

    public static GameData getInstance()
    {
        if (instance == null) instance = new GameData();

        instance.load();

        return instance;
    }

    private HashMap<Integer, Integer> records;

    private GameData() {}

    private void load()
    {
        // TODO: Load records from a file
        records = new HashMap<>(9);
    }

    public void reset()
    {
        records.clear();
    }
}
