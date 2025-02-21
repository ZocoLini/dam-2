package org.lebastudios.examen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import lombok.Getter;
import lombok.Setter;
import org.lebastudios.engine.Scene;
import org.lebastudios.examen.game.GameScene;
import org.lebastudios.examen.menu.MainMenuScene;


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
    @Getter private float gameTimeElapsed = 0;

    public float getScoreActualDifficulty()
    {
        return preferences.getFloat("record-" + difficulty, 0);
    }

    public void addTime(float time)
    {
        gameTimeElapsed += time;
    }

    public void resetGameState()
    {
        gameTimeElapsed = 0;
    }

    public void clearRecords()
    {
        for (int i = 1; i < 10; i++)
        {
            preferences.putFloat("record-" + difficulty, 0);
        }

        preferences.flush();
    }

    public void finishRun()
    {
        float actualRecord = getScoreActualDifficulty();
        float newTime = gameTimeElapsed;

        if (newTime > actualRecord)
        {
            preferences.putFloat("record-" + difficulty, newTime);
            preferences.flush();
        }

        GameScene.getInstance().goToMainMenu();
    }
}
