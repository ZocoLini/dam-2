package org.lebastudios.engine;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public class Animation<T> extends com.badlogic.gdx.graphics.g2d.Animation<T>
{
    private Consumer<T> onUpdate = _ -> {};
    @Setter @Getter private boolean looping;

    public Animation(Consumer<T> onUpdate, float frameDuration, T... keyFrames)
    {
        super(frameDuration, keyFrames);
        this.onUpdate = onUpdate;
    }

    public void animate(float stateTime)
    {
        onUpdate.accept(this.getKeyFrame(stateTime, looping));
    }
}
