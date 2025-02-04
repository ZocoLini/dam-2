package org.lebastudios.finger;

import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.components.CircleCollider2D;
import org.lebastudios.engine.components.CircleShape;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.components.Transform;

public class EnemyGeneratorController extends Component
{
    private float timeElapsed = 0;
    private float generattionEnemyRate = 4;

    @Override
    public void onUpdate(float deltaTime)
    {
        timeElapsed += deltaTime;

        if (timeElapsed >= generattionEnemyRate)
        {
            this.getGameObject().getScene().addGameObject(getAvailableEnemy());
            timeElapsed = 0;
        }
    }

    public GameObject getAvailableEnemy()
    {
        return createEnemy();
    }

    private GameObject createEnemy()
    {
        GameObject enemy = new GameObject(new Transform(0, 0, 0));
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(5);
        enemy.addComponent(circleShape);
        CircleCollider2D circleCollider2D = new CircleCollider2D();
        circleCollider2D.setRadius(4.5f);
        enemy.addComponent(circleCollider2D);
        enemy.addComponent(new EnemyController());

        return enemy;
    }
}
