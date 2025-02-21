package org.lebastudios.examen.game;

import com.badlogic.gdx.graphics.Color;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.CircleCollider2D;
import org.lebastudios.engine.components.CircleShape;
import org.lebastudios.engine.components.TextRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.engine.physics.Physics2D;
import org.lebastudios.examen.world.WorldConfig;

public class GameScene extends Scene
{
    @Override
    protected void setup()
    {
        Physics2D.getCollisionMatrix().addLayer("enemy");
        Physics2D.getCollisionMatrix().addLayer("player");

        Physics2D.getCollisionMatrix().setCollision("enemy", "player", true);

        GameObject player = new GameObject(new Transform(0, 0, 0));
        player.getMetadata().setName("player");

        CircleShape circleShape = new CircleShape();
        circleShape.setColor(Color.WHITE);
        circleShape.setRadius(10);
        circleShape.setFilled(true);
        player.addComponent(circleShape);

        CircleCollider2D circleCollider2D = new CircleCollider2D();
        circleCollider2D.setRadius(8);
        circleCollider2D.setLayer("player");
        player.addComponent(circleShape);

        player.addComponent(new PlayerController());

        this.addGameObject(player);

        GameObject enemyGenerator = new GameObject(new Transform(0, 0, 0));
        enemyGenerator.addComponent(new EnemyGeneratorController());

        this.addGameObject(enemyGenerator);
    }

    @Override
    public float getCameraWidth()
    {
        return WorldConfig.WORLD_WIDTH;
    }

    @Override
    public float getCameraHeight()
    {
        return WorldConfig.WORLD_HEIGHT;
    }
}
