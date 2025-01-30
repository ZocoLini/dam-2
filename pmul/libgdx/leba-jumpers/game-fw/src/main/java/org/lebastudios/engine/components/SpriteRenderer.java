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
        this.spriteName = spriteName;
    }

    @Override
    public void onStart()
    {
        TextureRegion textureRegion = new TextureRegion();
        textureRegion.setRegion(new Texture(spriteName));

        sprite = new Sprite();
        sprite.setRegion(textureRegion);

        sprite.setSize(sprite.getTexture().getWidth(), sprite.getTexture().getHeight());
        sprite.setOriginCenter();
        sprite.setRotation(0);
    }

    @Override
    public void onRender(SpriteBatch batch)
    {
        Transform transform = this.getGameObject().getTransform();

        sprite.setPosition(transform.getPosition().x, transform.getPosition().y);
        sprite.setScale(transform.getScale().x, transform.getScale().y);

        sprite.draw(batch);
    }

    @Override
    public void onDispose()
    {
        sprite.getTexture().dispose();
    }
}
