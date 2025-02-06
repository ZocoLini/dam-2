package org.lebastudios.finger.game;

import lombok.Getter;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.components.*;
import org.lebastudios.finger.config.WorldConfig;

import java.util.ArrayList;


public class EnemyGeneratorController extends Component
{
    private float timeElapsed = 0;
    private float generattionEnemyRate = 2;
    // TODO: Esto debe ser un array y deberia haber otro para los colliders
    //  Tambien buscar una manera de hacer el trackeo y automaticamente quitar los elementos eliminados del tracking
    @Getter private static final ArrayList<GameObject> enemies = new ArrayList<>();
    @Getter private static final ArrayList<Collider2D> colliders = new ArrayList<>();
    private final GameObject finger;

    public EnemyGeneratorController(GameObject finger)
    {
        this.finger = finger;
    }

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
        float height = (float) (Math.random() * WorldConfig.HEIGHT - WorldConfig.HEIGHT / 2f);
        final var enemyTransform = new Transform(WorldConfig.WIDTH / 2f, height, 0);

        GameObject enemy = EnemyFactory.createEnemy(enemyTransform);

        enemies.add(enemy);

        return enemy;
    }

    private static class EnemyFactory
    {
        public static GameObject createEnemy(Transform transform)
        {
            GameObject enemy = new GameObject(transform);
            enemy.getMetadata().setTag("Enemy");

            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(7);
            enemy.addComponent(circleShape);

            CircleCollider2D circleCollider2D = new CircleCollider2D();
            circleCollider2D.setRadius(6.5f);
            enemy.addComponent(circleCollider2D);

            enemy.addComponent(new EnemyController());
            enemy.addComponent(new TextRenderer());

            return enemy;
        }
    }
}
