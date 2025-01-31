package org.lebastudios.engine;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public abstract class Scene implements Screen
{
    private final SceneMetadata metadata;
    private final Array<GameObject> gameObjects = new Array<>();
    @Getter private Camera camera;
    @Getter private final SpriteBatch batch = new SpriteBatch();
    @Getter private final ShapeRenderer shapeRenderer = new ShapeRenderer();

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

        camera = new Camera();
        camera.position.set(0, 0, 0);
        camera.update();

        shapeRenderer.setAutoShapeType(true);

        for (GameObject gameObject : gameObjects)
        {
            gameObject.create();
        }
    }

    @Override
    public final void render(float delta)
    {
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

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
        gameObject.setScene(this);
    }

    public void removeSceneObject(GameObject gameObject)
    {
        if (gameObjects.removeValue(gameObject, true))
        {
            gameObject.setScene(null);
            gameObject.dispose();
        }

    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height)
    {
        float aspectRatio = (float) height / width;
        float desiredAspectRatio = getCameraHeight() / getCameraWidth();

        if (aspectRatio < desiredAspectRatio)
        {
            camera.viewportWidth = getCameraWidth() / aspectRatio * desiredAspectRatio;
            camera.viewportHeight = getCameraHeight();
        }
        else
        {
            camera.viewportWidth = getCameraWidth();
            camera.viewportHeight = getCameraHeight() * aspectRatio / desiredAspectRatio;
        }

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
