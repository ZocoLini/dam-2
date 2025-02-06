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

import java.util.ArrayList;
import java.util.List;

public abstract class Scene implements Screen
{
    private final SceneMetadata metadata;
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final List<GameObject> gameObjectsToAdd = new ArrayList<>();
    private final List<GameObject> gameObjectsToRemove = new ArrayList<>();
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

        updateGameObjectList();

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

        updateGameObjectList();

        batch.begin();
        shapeRenderer.begin();

        for (GameObject gameObject : gameObjects)
        {
            gameObject.render(batch);
        }

        shapeRenderer.end();
        batch.end();
    }

    public synchronized void addGameObject(GameObject gameObject)
    {
        gameObjectsToAdd.add(gameObject);
    }

    public synchronized void removeSceneObject(GameObject gameObject)
    {
        gameObjectsToRemove.add(gameObject);
    }

    private void updateGameObjectList()
    {
        if (gameObjectsToRemove.isEmpty() && gameObjectsToAdd.isEmpty()) return;

        for (GameObject gameObject : gameObjectsToRemove)
        {
            if (gameObjects.remove(gameObject))
            {
                gameObject.setScene(null);
                gameObject.dispose();
            }
        }

        for (GameObject gameObject : gameObjectsToAdd)
        {
            gameObjects.add(gameObject);
            gameObject.setScene(this);
            gameObject.create();
        }

        gameObjectsToAdd.clear();
        gameObjectsToRemove.clear();
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
        Thread updatingGameObjectsList = new Thread(this::updateGameObjectList);
        updatingGameObjectsList.start();

        batch.dispose();
        shapeRenderer.dispose();

        try
        {
            updatingGameObjectsList.join();
        }
        catch (InterruptedException _) {System.err.println("Error updating the scene gameObjects");}

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
