package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import lombok.Getter;
import lombok.Setter;
import org.lebastudios.engine.GameAdapter;

@Getter
@Setter
public class BoxCollider2D extends Collider2D
{
    private Rectangle rectangle = new Rectangle();
    private float width;
    private float heigth;

    @Override
    public void onUpdate(float deltaTime)
    {
        Transform transform = this.getTransform();

        rectangle.setCenter(
            transform.getPosition().x,
            transform.getPosition().y
        );

        rectangle.setSize(
            width * transform.getScale().x,
            heigth * transform.getScale().y
        );
    }

    @Override
    public void onRender(SpriteBatch batch)
    {
        if (GameAdapter.DEBUG)
        {
            this.getGameObject().getScene().getShapeRenderer().rect(
                rectangle.x,
                rectangle.y,
                rectangle.width,
                rectangle.height
            );
        }
    }

    @Override
    public boolean collides(float x, float y)
    {
        return rectangle.contains(x, y);
    }

    @Override
    public boolean collides(Collider2D other)
    {
        return other.collidesWithBox(this);
    }

    @Override
    protected boolean collidesWithCircle(CircleCollider2D other)
    {
        return other.collidesWithBox(this);
    }

    @Override
    protected boolean collidesWithBox(BoxCollider2D other)
    {
        return rectangle.overlaps(other.getRectangle());
    }
}
