package org.lebastudios.finger.game;

import org.lebastudios.engine.components.Component;

public class BulletController extends Component
{
    private float speed = 50;

    @Override
    public void onUpdate(float deltaTime)
    {
        this.getTransform().translate(
            1 * speed * deltaTime,
            0,
            0
        );
    }
}
