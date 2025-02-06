package org.lebastudios.finger.game;

import com.badlogic.gdx.graphics.Texture;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.BoxCollider2D;
import org.lebastudios.engine.components.SpriteRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.finger.config.WorldConfig;

public class MainScene extends Scene
{
    @Override
    protected void setup()
    {
        GameObject finger  = new GameObject(new Transform(- WorldConfig.WIDTH / 2f, 0, 0));
        finger.getMetadata().setTag("Player");
        finger.addComponent(new FingerController());
        final var spriteRenderer = new SpriteRenderer();
        spriteRenderer.setSpriteTexture(new Texture("insects/insect_1.png"));
        finger.addComponent(spriteRenderer);
        BoxCollider2D boxCollider2D = new BoxCollider2D();
        boxCollider2D.setWidth(20);
        boxCollider2D.setHeigth(20);
        finger.addComponent(boxCollider2D);

        GameObject enemyGenerator = new GameObject(new Transform(0, 0, 0));
        enemyGenerator.addComponent(new EnemyGeneratorController(finger));

        this.addGameObject(finger);
        this.addGameObject(enemyGenerator);
    }

    @Override
    protected float getCameraWidth()
    {
        return WorldConfig.WIDTH;
    }

    @Override
    protected float getCameraHeight()
    {
        return WorldConfig.HEIGHT;
    }
}
