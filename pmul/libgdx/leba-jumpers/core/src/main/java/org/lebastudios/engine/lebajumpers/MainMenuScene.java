package org.lebastudios.engine.lebajumpers;

import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.Scene;
import org.lebastudios.engine.components.SpriteRenderer;
import org.lebastudios.engine.components.Transform;

public class MainMenuScene extends Scene
{
    @Override
    protected void setup()
    {
        GameObject character = new GameObject(new Transform(0, 0, 0));

        character.addComponent(new SpriteRenderer("warrior.png"));

    }
}
