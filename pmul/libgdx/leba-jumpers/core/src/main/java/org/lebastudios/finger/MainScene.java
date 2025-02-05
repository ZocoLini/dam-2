package org.lebastudios.finger;

import com.badlogic.gdx.graphics.Texture;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.SpriteRenderer;
import org.lebastudios.engine.components.Transform;

public class MainScene extends Scene
{
    @Override
    protected void setup()
    {
        GameObject finger  = new GameObject(new Transform(0, 0, 0));
        finger.addComponent(new FingerController());
        final var spriteRenderer = new SpriteRenderer();
        spriteRenderer.setSpriteTexture(new Texture("insects/insect_1.png"));
        finger.addComponent(spriteRenderer);

        GameObject enemyGenerator = new GameObject(new Transform(0, 0, 0));
        enemyGenerator.addComponent(new EnemyGeneratorController());

        this.addGameObject(finger);
        this.addGameObject(enemyGenerator);
    }

    @Override
    protected float getCameraWidth()
    {
        return 0;
    }

    @Override
    protected float getCameraHeight()
    {
        return 0;
    }
}
