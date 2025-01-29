package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteRenderer extends Component
{
    private final String spriteName;
    private Sprite sprite;

    public SpriteRenderer(String spriteName)
    {
        super();
        this.spriteName = spriteName;
    }

    @Override
    public void onAwake()
    {
        TextureRegion textureRegion = new TextureRegion();
        textureRegion.setRegion(new Texture("libgdx.png"));

        sprite = new Sprite();
        sprite.setRegion(textureRegion);

        sprite.setSize(300, 300);
        sprite.setOriginCenter();
        sprite.setPosition(100, 50);
        sprite.setRotation(0);
    }

    @Override
    public void onRender(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    @Override
    public void onDispose()
    {
        sprite.getTexture().dispose();
    }
}
