package org.lebastudios.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import lombok.Getter;
import org.lebastudios.engine.input.InputManager;

@Getter
public abstract class GameAdapter extends ApplicationAdapter
{
    protected Scene scene;

    protected abstract Scene createFirstScene();

    @Override
    public void create()
    {
        scene = createFirstScene();

        Gdx.input.setInputProcessor(InputManager.getInstance());

        scene.create();
    }

    @Override
    public void render()
    {
        scene.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height)
    {
        if (scene != null) scene.resize(width, height);
    }

    public final Scene getScene()
    {
        return scene;
    }

    public final void setScene(Scene scene)
    {
        if (this.scene != null) this.scene.hide();
        this.scene = scene;
        if (this.scene != null) {
            this.scene.show();
            this.scene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }
}
