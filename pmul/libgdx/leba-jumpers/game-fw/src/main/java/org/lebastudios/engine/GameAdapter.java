package org.lebastudios.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import lombok.Getter;
import org.lebastudios.engine.input.InputManager;

@Getter
public abstract class GameAdapter extends Game
{
    protected abstract Scene createFirstScene();

    public GameAdapter()
    {
        super();
    }

    @Override
    public void create()
    {
        Gdx.input.setInputProcessor(InputManager.getInstance());

        setScene(createFirstScene());
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
