package org.lebastudios.engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public class Animation<T> extends com.badlogic.gdx.graphics.g2d.Animation<T>
{
    private Consumer<T> onUpdate = _ ->
    {};
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

    public void dispose()
    {
        // TODO: Don't do the check every iteration
        for (T keyFrame : getKeyFrames())
        {
            if (keyFrame instanceof TextureRegion)
            {
                ((TextureRegion) keyFrame).getTexture().dispose();
            }
            else
            {
                if (keyFrame instanceof TextureAtlas)
                {
                    ((TextureAtlas) keyFrame).dispose();
                }
                else
                {
                    if (keyFrame instanceof Texture)
                    {
                        ((Texture) keyFrame).dispose();
                    }
                }
            }
        }
    }
}
