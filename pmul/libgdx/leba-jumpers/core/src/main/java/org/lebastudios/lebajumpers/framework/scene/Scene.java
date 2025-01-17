package org.lebastudios.lebajumpers.framework.scene;

import org.lebastudios.lebajumpers.framework.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Scene
{
    private final List<GameObject> gameObjects;

    public Scene()
    {
        gameObjects = new ArrayList<>();
    }

    public boolean addGameObject(GameObject gameObject)
    {
        return gameObjects.add(gameObject);
    }

    public boolean removeGameObject(GameObject gameObject)
    {
        return gameObjects.remove(gameObject);
    }
}
