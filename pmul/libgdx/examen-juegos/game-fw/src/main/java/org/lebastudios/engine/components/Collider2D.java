package org.lebastudios.engine.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import lombok.Getter;
import lombok.Setter;
import org.lebastudios.engine.Camera;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.engine.physics.Physics2D;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

// TODO: Implement collision resolution
//  this.getGameObject().onCollision2DEnter(collider);
public abstract class Collider2D extends Component
{
    @Getter @Setter protected String layer = "Default";
    protected boolean isTrigger = true;

    @Override
    public void onStart()
    {
        final Camera camera = this.getGameObject().getScene().getCamera();

        InputManager.getInstance().addTouchDownListener((screenX, screenY, _, _) ->
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
        var collidersState = this.getGameObject().getScene().getCollidersState(this.getGameObject());

        gameObjectsLoop:
        for (var colliderState : collidersState.entrySet())
        {
            GameObject other = colliderState.getKey();

            if (!other.isEnabled()) continue gameObjectsLoop;

            boolean state = colliderState.getValue();

            boolean exited = false;
            collidersLoop:
            for (Collider2D collider2D : other.getColliders())
            {
                if (!Physics2D.getCollisionMatrix().canCollide(collider2D.layer, this.layer)) continue;

                if (state)
                {
                    if (!this.collides(collider2D))
                    {
                        other.onTrigger2DExit(this);
                        this.getGameObject().onTrigger2DExit(collider2D);
                        exited = true;
                        break collidersLoop;
                    }
                    else
                    {
                        other.onTrigger2DStays(this);
                        this.getGameObject().onTrigger2DStays(collider2D);
                        continue gameObjectsLoop;
                    }
                }
                else
                {
                    if (this.collides(collider2D))
                    {
                        other.onTrigger2DEnter(this);
                        this.getGameObject().onTrigger2DEnter(collider2D);
                        colliderState.setValue(true);
                        continue gameObjectsLoop;
                    }
                }
            }

            if (exited)
            {
                colliderState.setValue(false);
            }
        }
    }

    public boolean collides(Vector2 point) { return collides(point.x, point.y); }

    public abstract boolean collides(float x, float y);
    public abstract boolean collides(Collider2D other);
    protected abstract boolean collidesWithCircle(CircleCollider2D other);
    protected abstract boolean collidesWithBox(BoxCollider2D other);

}
