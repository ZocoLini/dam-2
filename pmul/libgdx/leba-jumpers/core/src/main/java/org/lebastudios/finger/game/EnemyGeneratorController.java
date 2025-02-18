package org.lebastudios.finger.game;

import com.badlogic.gdx.math.Vector3;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.components.*;
import org.lebastudios.engine.util.Pool;
import org.lebastudios.finger.config.WorldConfig;

import java.util.HashSet;
import java.util.Iterator;


public class EnemyGeneratorController extends Component
{
    private float timeElapsed = 0;
    private float generationEnemyRate = 2;
    private final Pool<GameObject> enemyPool = new Pool<>(this::createEnemy);

    private final HashSet<GameObject> spawnedEnemies = new HashSet<>();

    @Override
    public void onUpdate(float deltaTime)
    {
        Iterator<GameObject> spawnedEnemiesIterator = spawnedEnemies.iterator();

        while (spawnedEnemiesIterator.hasNext())
        {
            GameObject enemy = spawnedEnemiesIterator.next();
            final var enemyPosition = enemy.getTransform().getPosition();

            if (Math.abs(enemyPosition.x) > WorldConfig.WIDTH / 2f || !enemy.isEnabled())
            {
                spawnedEnemiesIterator.remove();
                enemy.setEnabled(false);
                enemyPool.release(enemy);
            }
        }

        timeElapsed += deltaTime;

        if (timeElapsed >= generationEnemyRate)
        {
            final var newEnemy = enemyPool.request();

            newEnemy.setEnabled(true);

            float y = (float) (Math.random() * WorldConfig.HEIGHT - WorldConfig.HEIGHT / 2f);
            float x = WorldConfig.WIDTH / 2f;
            newEnemy.getTransform().setPosition(new Vector3(x, y, 0));

            spawnedEnemies.add(newEnemy);
            timeElapsed = 0;
        }
    }

    public GameObject createEnemy()
    {
        GameObject enemy = new GameObject(new Transform(0, 0, 0));
        enemy.getMetadata().setTag("Enemy");

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(7);
        enemy.addComponent(circleShape);

        CircleCollider2D circleCollider2D = new CircleCollider2D();
        circleCollider2D.setRadius(6.5f);
        circleCollider2D.setLayer("Enemy");
        enemy.addComponent(circleCollider2D);

        enemy.addComponent(new EnemyController());
        enemy.addComponent(new TextRenderer());

        this.getGameObject().getScene().addGameObject(enemy);

        return enemy;
    }
}
