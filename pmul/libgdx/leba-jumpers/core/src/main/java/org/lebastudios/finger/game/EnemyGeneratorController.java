package org.lebastudios.finger.game;

import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.components.*;
import org.lebastudios.finger.config.WorldConfig;


public class EnemyGeneratorController extends Component
{
    private float timeElapsed = 0;
    private float generationEnemyRate = 2;
    private final GameObject finger;

    public EnemyGeneratorController(GameObject finger)
    {
        this.finger = finger;
    }

    @Override
    public void onUpdate(float deltaTime)
    {
        timeElapsed += deltaTime;

        if (timeElapsed >= generationEnemyRate)
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
        float height = (float) (Math.random() * WorldConfig.HEIGHT - WorldConfig.HEIGHT / 2f);
        final var enemyTransform = new Transform(WorldConfig.WIDTH / 2f, height, 0);

        return new EnemyFactory().createEnemy(enemyTransform, finger);
    }

    private static class EnemyFactory
    {
        public GameObject createEnemy(Transform transform, GameObject finger)
        {
            GameObject enemy = new GameObject(transform);
            enemy.getMetadata().setTag("Enemy");

            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(7);
            enemy.addComponent(circleShape);

            CircleCollider2D circleCollider2D = new CircleCollider2D();
            circleCollider2D.setRadius(6.5f);
            circleCollider2D.trackCollider(finger.getComponent(Collider2D.class));
            enemy.addComponent(circleCollider2D);

            colliders.add(circleCollider2D);

            enemy.addComponent(new EnemyController());
            enemy.addComponent(new TextRenderer());

            return enemy;
        }
    }
}
