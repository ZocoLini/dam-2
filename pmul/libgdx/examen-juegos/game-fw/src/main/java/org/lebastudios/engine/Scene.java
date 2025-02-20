package org.lebastudios.engine;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lebastudios.engine.coroutine.IEnumerator;
import org.lebastudios.engine.util.LazyArrayList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public abstract class Scene implements Screen
{
    private final SceneMetadata metadata;

    private final LazyArrayList<GameObject> gameObjects = new LazyArrayList<>();

    private final HashMap<GameObject, HashMap<GameObject, Boolean>> collidersState = new HashMap<>();

    private final List<IEnumerator> coroutines = new ArrayList<>();

    private final Consumer<GameObject> removeConsumer = gameObject -> {
        collidersState.entrySet().removeIf(entry ->
        {
            if (entry.getKey() == gameObject)
            {
                return true;
            }

            entry.getValue().remove(gameObject);
            return false;
        });

        gameObject.setScene(null);
        gameObject.dispose();
    };
    private final Consumer<GameObject> addConsumer = gameObject -> {

        if (gameObject.getScene() != null)
        {
            if (gameObject.getScene() == this)
            {
                System.out.println("[WARNING] GameObject already in scene");
            }
            else
            {
                System.out.println("[ERROR] GameObject already in another scene");
            }

            return;
        }

        collidersState.put(gameObject, new HashMap<>());
        var thisGameObjectCollisionsState = collidersState.get(gameObject);

        gameObjects.forEach(go ->
        {
            if (go == gameObject) return;

            thisGameObjectCollisionsState.put(go, false);
        });

        gameObject.setScene(this);
        gameObject.create();
    };

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

        gameObjects.update(addConsumer, removeConsumer);
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

        Iterator<IEnumerator> iterator = coroutines.iterator();

        while (iterator.hasNext())
        {
            IEnumerator coroutine = iterator.next();

            if (!coroutine.moveNext(delta)) continue;
            if (coroutine.getAction().get()) continue;

            iterator.remove();
        }

        gameObjects.update(addConsumer, removeConsumer);

        shapeRenderer.begin();
        batch.begin();

        for (GameObject gameObject : gameObjects)
        {
            gameObject.render(batch);
        }

        batch.end();
        shapeRenderer.end();
    }

    public HashMap<GameObject, Boolean> getCollidersState(GameObject gameObject)
    {
        return collidersState.get(gameObject);
    }

    public synchronized void addGameObject(GameObject gameObject)
    {
        gameObjects.addLazy(gameObject);
    }

    public synchronized void removeGameObject(GameObject gameObject)
    {
        gameObjects.removeLazy(gameObject);
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
        Thread updatingGameObjectsList = new Thread(() -> gameObjects.update(addConsumer, removeConsumer));
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

    public void startCoroutine(IEnumerator coroutine)
    {
        coroutines.add(coroutine);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SceneMetadata
    {
        private String name;
    }
}
