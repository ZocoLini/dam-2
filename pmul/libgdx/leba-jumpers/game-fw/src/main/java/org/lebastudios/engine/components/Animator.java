package org.lebastudios.engine.components;

import org.lebastudios.engine.Animation;

import java.util.ArrayList;
import java.util.List;

public class Animator extends Component
{
    private float stateTime = 0;
    private List<Animation<?>> animations = new ArrayList<>();

    public void addAnimation() {}

    @Override
    public void onUpdate(float deltaTime)
    {
        stateTime += deltaTime;

        for (var animation : animations)
        {
            animation.animate(stateTime);
        }
    }
}
