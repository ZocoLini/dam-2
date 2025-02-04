package org.lebastudios.engine.components;

public abstract class Collider2D<T extends Collider2D<T>> extends Component
{
    public abstract boolean collides(float x, float y);

    public abstract boolean collides(T other);
}
