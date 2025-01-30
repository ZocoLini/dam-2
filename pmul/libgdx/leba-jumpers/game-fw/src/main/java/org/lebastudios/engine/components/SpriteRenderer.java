package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

public class SpriteRenderer extends Component
{
    private Sprite sprite = new Sprite();
    @Getter @Setter private boolean flipX;
    @Getter @Setter private boolean flipY;

    @Override
    public void onStart()
    {
        sprite.setOriginCenter();
    }

    @Override
    public void onRender(SpriteBatch batch)
    {
        if (sprite.getTexture() == null) return;

        // Cambiar la imagen si necesario
        Transform transform = this.getGameObject().getTransform();

        sprite.setCenter(transform.getPosition().x, transform.getPosition().y);
        sprite.setScale(transform.getScale().x, transform.getScale().y);

        sprite.draw(batch);
    }

    public void setSpriteTexture(Texture texture)
    {
        sprite.setRegion(texture);
        sprite.setTexture(texture);
        sprite.setSize(texture.getWidth(), texture.getHeight());
    }

    public void flipX(boolean flip)
    {
        this.flipX = flip;
        sprite.flip(flipX, flipY);
    }

    public void flipY(boolean flip)
    {
        this.flipY = flip;
        sprite.flip(flipX, flipY);
    }

    @Override
    public void onDispose()
    {
        sprite.getTexture().dispose();
    }
}
