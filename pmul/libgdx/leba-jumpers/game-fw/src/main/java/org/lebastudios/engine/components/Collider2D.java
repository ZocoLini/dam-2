package org.lebastudios.engine.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.lebastudios.engine.Camera;
import org.lebastudios.engine.input.InputManager;

import java.util.Collection;
import java.util.HashMap;

// TODO: Implement collision resolution
//  this.getGameObject().onCollision2DEnter(collider);
public abstract class Collider2D extends Component
{
    protected boolean isTrigger = true;
    protected HashMap<Collider2D, Boolean> trackedCollidersState = new HashMap<>();

    @Override
    public void onStart()
    {
        final Camera camera = this.getGameObject().getScene().getCamera();

        InputManager.getInstance().addTouchDownListener((screenX, screenY, pointer, button) ->
        {
            final var unprojected = camera.unproject(new Vector3(screenX, screenY, 0));

            if (collides(unprojected.x, unprojected.y))
            {
                this.getGameObject().onClicked();
            }
        });
    }

    @Override
    public void onPhysicsUpdate(float deltaTime)
    {
        for (var otherStateEntry : trackedCollidersState.entrySet())
        {
            final var other = otherStateEntry.getKey();
            final var otherState = otherStateEntry.getValue();

            if (otherState)
            {
                if (!this.collides(other))
                {
                    other.getGameObject().onTrigger2DExit(this);
                    this.getGameObject().onTrigger2DExit(other);
                    otherStateEntry.setValue(false);
                }
                else
                {
                    other.getGameObject().onTrigger2DStays(this);
                    this.getGameObject().onTrigger2DStays(other);
                }
            }
            else
            {
                if (this.collides(other))
                {
                    other.getGameObject().onTrigger2DEnter(this);
                    this.getGameObject().onTrigger2DEnter(other);
                    otherStateEntry.setValue(true);
                }
            }
        }
    }

    public void trackCollider(Collider2D... colliders)
    {
        for (var collider : colliders)
        {
            trackedCollidersState.put(collider, false);
        }
    }

    public void trackCollider(Collection<Collider2D> colliders)
    {
        for (var collider : colliders)
        {
            trackedCollidersState.put(collider, false);
        }
    }

    @Override
    public void onDispose()
    {
        super.onDispose();
    }

    public boolean collides(Vector2 point) { return collides(point.x, point.y); }

    public abstract boolean collides(float x, float y);
    public abstract boolean collides(Collider2D other);
    protected abstract boolean collidesWithCircle(CircleCollider2D other);
    protected abstract boolean collidesWithBox(BoxCollider2D other);

}
