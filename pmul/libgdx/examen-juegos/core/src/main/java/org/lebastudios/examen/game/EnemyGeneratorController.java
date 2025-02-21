package org.lebastudios.examen.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector3;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.util.Pool;
import org.lebastudios.examen.world.WorldConfig;

import java.util.HashSet;
import java.util.Iterator;

public class EnemyGeneratorController extends Component
{
    private static final float ENEMY_GEN_TIME = 2;

    private final HashSet<GameObject> enemiesInstantiated = new HashSet<>();

    private float timeElapsed = 0;
    private final Pool<GameObject> enemiesRecicler = new Pool<>(() -> switch ((int) (Math.random() * 3))
    {
        case 0 -> EnemyController.createBoxEnemy();
        case 1 -> EnemyController.createCircleEnemy();
        case 2 -> EnemyController.createCrossEnemy();
        default -> throw new RuntimeException("[ERROR] El numero generado no es válido");
    });

    @Override
    public void onUpdate(float deltaTime)
    {
        timeElapsed += deltaTime;

        if (timeElapsed > ENEMY_GEN_TIME)
        {
            timeElapsed = 0;
            instantiateEnemy();
        }

        Iterator<GameObject> iterator = enemiesInstantiated.iterator();
        while (iterator.hasNext())
        {
            GameObject enemy = iterator.next();

            if (!enemy.isEnabled())
            {
                System.out.println("[INFO] Enemy disabled found, saved for recycling");
                iterator.remove();
                enemiesRecicler.release(enemy);
            }
        }
    }

    private void instantiateEnemy()
    {
        GameObject enemy = enemiesRecicler.request();
        enemy.setEnabled(true);

        int xSide = switch ((int) (Math.random() * 2))
        {
            case 0 -> -1;
            case 1 -> 1;
            default -> throw new RuntimeException("[ERROR] El número generado no es valido");
        };

        float ySide = (float) (Math.random() * WorldConfig.WORLD_HEIGHT) - (WorldConfig.WORLD_HEIGHT / 2f - GameScene.INFO_DISPLAY_HEIGTH);

        enemy.getTransform().setPosition(new Vector3(
            WorldConfig.WORLD_WIDTH / 2f * xSide,
            ySide,
            0
        ));

        final var enemyController = enemy.getComponent(EnemyController.class);

        if (enemyController == null) throw new IllegalStateException("EnemyController should be added to the enemy gameobject");

        enemyController.setDireccion(xSide * -1);
        enemyController.resetState();

        enemiesInstantiated.add(enemy);
        this.getGameObject().instantiate(enemy);
    }
}
