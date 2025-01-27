package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteRenderer extends Component
{
    private String spriteName;
    private Sprite sprite;

    public SpriteRenderer(String spriteName)
    {
        this.spriteName = spriteName;

        sprite = new Sprite();
    }

    @Override
    public void onRender(SpriteBatch batch)
    {
        sprite.draw(batch);
    }
}
