package org.lebastudios.moscas.game;

import lombok.Getter;
import lombok.Setter;

public class GameState
{
    private static GameState instance;

    public static GameState getInstance()
    {
        if (instance == null) instance = new GameState();

        return instance;
    }

    @Setter @Getter private int numInsectos = 9;

    private GameState() {}

    public void reset()
    {
        instance = null;
    }
}
