package org.lebastudios.examen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

    private final Preferences preferences;

    private GameState()
    {
        preferences = Gdx.app.getPreferences("preferences-examen");
    }

    @Setter @Getter private int difficulty = 1;

    public float getScoreActualDifficulty()
    {
        return preferences.getFloat("record-" + difficulty, 0);
    }

    public void setScoreActualDifficulty(float score)
    {
        if (score < 0)
        {
            System.out.println("[WARNING] Tryied to save a negative score");
            return;
        }

        preferences.putFloat("record-" + difficulty, score);
    }
}
