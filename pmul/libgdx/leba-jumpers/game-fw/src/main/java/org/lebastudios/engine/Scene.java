package org.lebastudios.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    @Getter private Camera camera;
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

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new Camera(getCameraWidth(), getCameraHeight() * (h / w));
        camera.position.set(0, 0, 0);
        camera.update();

        for (GameObject gameObject : gameObjects)
        {
            gameObject.create();
        }
    }

    @Override
    public final void render(float delta)
    {
        batch.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(0, 0, 1, 1);

        for (GameObject gameObject : gameObjects)
        {
            gameObject.physicsUpdate(delta);
        }

        for (GameObject gameObject : gameObjects)
        {
            gameObject.update(delta);
        }

        batch.begin();

        for (GameObject gameObject : gameObjects)
        {
            gameObject.render(batch);
        }

        batch.end();
    }

    public void addGameObject(GameObject gameObject)
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
        camera.viewportWidth = getCameraWidth();
        camera.viewportHeight = getCameraHeight() * height/width;
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

    protected abstract float getCameraWidth();

    protected abstract float getCameraHeight();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SceneMetadata
    {
        private String name;
    }
}
