package org.lebastudios.moscas.game;

import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.SpriteRenderer;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.moscas.config.ScreenConfig;

public class GameScene extends Scene
{
    @Override
    protected void setup()
    {
        GameObject character = new GameObject(new Transform(0, 0, 0));

        character.addComponent(new SpriteRenderer());
        character.addComponent(new MoscaController());

        this.addGameObject(character);
    }

    @Override
    protected float getCameraWidth()
    {
        return ScreenConfig.WIDTH;
    }

    @Override
    protected float getCameraHeight()
    {
        return ScreenConfig.HEIGHT;
    }
}
