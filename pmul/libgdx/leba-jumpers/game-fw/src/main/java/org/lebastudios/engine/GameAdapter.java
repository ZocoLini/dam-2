package org.lebastudios.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Collections;
import lombok.Getter;
import org.lebastudios.engine.input.InputManager;

@Getter
public abstract class GameAdapter extends Game
{
    protected abstract Scene getFirstScene();
    public static final boolean DEBUG = true;

    public GameAdapter()
    {
        super();
    }

    @Override
    public void create()
    {
        Gdx.input.setInputProcessor(InputManager.getInstance());
        Collections.allocateIterators = true;

        onCreate();

        setScene(getFirstScene());
    }

    protected void onCreate()
    {
    }

    public final Scene getScene()
    {
        return (Scene) super.screen;
    }

    public final void setScene(Scene scene)
    {
        scene.create();

        super.setScreen(scene);
    }
}
