package org.lebastudios.moscas.menu;

import org.lebastudios.engine.components.Component;

public class ImageMovementController extends Component
{
    private int direction = 1;
    private int speed = 60;

    @Override
    public void onStart()
    {
    }

    @Override
    public void onUpdate(float deltaTime)
    {
        this.getTransform().translate(
            speed * direction * deltaTime,
            0,
            0
        );

        System.out.println(this.getTransform().getPosition());
    }
}
