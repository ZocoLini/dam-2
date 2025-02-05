package org.lebastudios.engine.components;

import com.badlogic.gdx.utils.Array;
import org.lebastudios.engine.Animation;

public class Animator extends Component
{
    private float stateTime = 0;
    private final Array<Animation<?>> animations = new Array<>(true, 2);

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

    @Override
    public void onDispose()
    {
        for (var animation : animations)
        {
            animation.dispose();
        }
    }
}
