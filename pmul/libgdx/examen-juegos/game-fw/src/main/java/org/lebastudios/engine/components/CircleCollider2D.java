package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import lombok.Getter;
import lombok.Setter;
import org.lebastudios.engine.GameAdapter;

@Setter
@Getter
public class CircleCollider2D extends Collider2D
{
    private Circle circle = new Circle();
    private float radius;

    @Override
    public void onUpdate(float deltaTime)
    {
        Transform transform = this.getTransform();

        circle.setPosition(
            transform.getPosition().x,
            transform.getPosition().y
        );

        circle.setRadius(
            radius * Math.min(transform.getScale().x, transform.getScale().y)
        );
    }

    @Override
    public void onRender(SpriteBatch batch)
    {
        if (GameAdapter.DEBUG)
        {
            this.getGameObject().getScene().getShapeRenderer().circle(
                circle.x,
                circle.y,
                circle.radius
            );
        }
    }

    @Override
    public boolean collides(float x, float y)
    {
        return circle.contains(x, y);
    }

    @Override
    public boolean collides(Collider2D other)
    {
        return other.collidesWithCircle(this);
    }

    @Override
    protected boolean collidesWithCircle(CircleCollider2D other)
    {
        return this.circle.overlaps(other.circle);
    }

    @Override
    protected boolean collidesWithBox(BoxCollider2D other)
    {
        return Intersector.overlaps(circle, other.getRectangle());
    }
}
