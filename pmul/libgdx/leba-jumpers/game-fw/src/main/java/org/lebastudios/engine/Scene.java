package org.lebastudios.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene implements Screen
{
    private SceneMetadata metadata;
    private final List<GameObject> gameObjects = new ArrayList<>();
    @Getter private final Camera camera = new Camera();
    @Getter private final SpriteBatch batch = new SpriteBatch();

    public Scene(SceneMetadata sceneMetadata)
    {
        this.metadata = sceneMetadata;
    }

    public Scene()
    {
        this(new SceneMetadata(Scene.class.getSimpleName()));
    }

    public final void create()
    {
        setup();

        batch.setProjectionMatrix(camera.combined);

        for (GameObject gameObject : gameObjects)
        {
            gameObject.create();
        }
    }

    @Override
    public final void render(float delta)
    {
        ScreenUtils.clear(0, 0, 1, 1);

        updateAndRender(delta, batch);
    }

    private void updateAndRender(float deltaTime, SpriteBatch batch)
    {
        for (GameObject gameObject : gameObjects)
        {
            gameObject.update(deltaTime);
        }

        batch.begin();

        for (GameObject gameObject : gameObjects)
        {
            gameObject.render(batch);
        }

        batch.end();
    }

    public void addSceneObject(GameObject gameObject)
    {
        gameObjects.add(gameObject);
    }

    public void removeSceneObject(GameObject gameObject)
    {
        gameObjects.remove(gameObject);
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height)
    {
        camera.setToOrtho(false, 300, 200);
        camera.update();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose()
    {
        for (GameObject gameObject : gameObjects)
        {
            gameObject.dispose();
        }
    }

    protected abstract void setup();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SceneMetadata
    {
        private String name;
    }
}
