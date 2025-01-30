package org.lebastudios.engine.components;

import org.lebastudios.engine.Animation;

import java.util.ArrayList;
import java.util.List;

public class Animator extends Component
{
    private float stateTime = 0;
    private final List<Animation<?>> animations = new ArrayList<>();

    public void addAnimation(Animation<?> animation) {
        animations.add(animation);
    }

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
