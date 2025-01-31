package org.lebastudios.moscas.game;

import com.badlogic.gdx.graphics.Texture;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.BoxCollider2D;
import org.lebastudios.engine.components.SpriteRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.moscas.config.WorldConfig;

public class GameScene extends Scene
{
    @Override
    protected void setup()
    {
        GameObject character = new GameObject(new Transform(0, 0, 0), this);

        SpriteRenderer spriteRenderer = new SpriteRenderer();
        spriteRenderer.setSpriteTexture(new Texture("insects/insect_1.png"));
        character.addComponent(spriteRenderer);
        BoxCollider2D collider2D = new BoxCollider2D();
        collider2D.setHeigth(50);
        collider2D.setWidth(50);
        character.addComponent(collider2D);
        character.addComponent(new MoscaController());

        this.addGameObject(character);
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
