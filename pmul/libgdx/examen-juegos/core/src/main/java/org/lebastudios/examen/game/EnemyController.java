package org.lebastudios.examen.game;

import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.components.Transform;

public abstract class EnemyController extends Component
{
    public static GameObject createCircleEnemy()
    {
        GameObject enemy = new GameObject(new Transform(0, 0, 0));

        return enemy;
    }

    public static GameObject createBoxEnemy()
    {
        GameObject enemy = new GameObject(new Transform(0, 0, 0));

        return enemy;
    }

    public static GameObject createCrossEnemy()
    {
        GameObject enemy = new GameObject(new Transform(0, 0, 0));

        return enemy;
    }
}
