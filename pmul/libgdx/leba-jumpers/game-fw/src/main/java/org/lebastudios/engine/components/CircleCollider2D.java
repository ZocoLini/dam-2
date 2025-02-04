package org.lebastudios.engine.components;

import com.badlogic.gdx.math.Circle;
import lombok.Setter;

public class CircleCollider2D extends Collider2D<CircleCollider2D>
{
    private Circle circle;
    @Setter private float radius;

    @Override
    public boolean collides(float x, float y)
    {
        return circle.contains(x, y);
    }

    @Override
    public boolean collides(CircleCollider2D other)
    {
        return circle.overlaps(other.circle);
    }
}
