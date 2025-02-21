package org.lebastudios.engine;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lebastudios.engine.camera.Camera;
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

    private final HashMap<GameObject, HashMap<GameObject, Boolean>> collisionsStates = new HashMap<>();

    private final List<IEnumerator> coroutines = new ArrayList<>();

    private final Consumer<GameObject> removeConsumer = gameObject ->
    {
        collisionsStates.entrySet().removeIf(entry ->
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
    private final Consumer<GameObject> addConsumer = gameObject ->
    {

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

        collisionsStates.put(gameObject, new HashMap<>());
        var thisGameObjectCollisionsState = collisionsStates.get(gameObject);

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

    private boolean paused = true;

    public Scene(SceneMetadata sceneMetadata)
    {
        this.metadata = sceneMetadata;
        create();
    }

    public Scene()
    {
        this(new SceneMetadata(Scene.class.getSimpleName()));
    }

    public final void create()
    {
        setup();

        camera = new Camera(this);
        camera.position.set(0, 0, 0);
        camera.update();

        shapeRenderer.setAutoShapeType(true);

        gameObjects.update(addConsumer, removeConsumer);
    }

    @Override
    public void render(float delta)
    {
        if (paused) return;

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

        camera.onRender(this);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        batch.begin();

        for (GameObject gameObject : gameObjects)
        {
            gameObject.render(batch);
        }

        shapeRenderer.end();
        batch.end();
    }

    public HashMap<GameObject, Boolean> getCollisionsStates(GameObject gameObject)
    {
        return collisionsStates.get(gameObject);
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
    public void show()
    {
        paused = false;
    }

    @Override
    public void resize(int width, int height)
    {
        camera.onResize(width, height, this);

        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause()
    {
        paused = true;
    }

    @Override
    public void resume()
    {
        paused = false;
    }

    @Override
    public void hide()
    {
        paused = true;
    }

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

    public abstract float getCameraWidth();

    public abstract float getCameraHeight();

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
