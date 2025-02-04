package org.lebastudios.engine.components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import lombok.Getter;
import lombok.Setter;
import org.lebastudios.engine.Camera;
import org.lebastudios.engine.GameAdapter;
import org.lebastudios.engine.input.InputManager;

import java.util.HashMap;

@Getter
@Setter
public class BoxCollider2D extends Collider2D<BoxCollider2D>
{
    // TODO: Implement collision resolution
    //  this.getGameObject().onCollision2DEnter(collider);
    private boolean isTrigger = true;
    private Rectangle rectangle;
    @Setter @Getter private float width;
    @Setter @Getter private float heigth;
    private HashMap<BoxCollider2D, Boolean> trackedCollidersState = new HashMap<>();

    @Override
    public void onStart()
    {
        rectangle = new Rectangle();
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

    public void trackCollider(BoxCollider2D collider)
    {
        trackedCollidersState.put(collider, false);
    }

    @Override
    public void onPhysicsUpdate(float deltaTime)
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

        if (GameAdapter.DEBUG)
        {
            this.getGameObject().getScene().getShapeRenderer().rect(
                rectangle.x,
                rectangle.y,
                rectangle.width,
                rectangle.height
            );
        }

        for (var colliderStateEntry : trackedCollidersState.entrySet())
        {
            if (colliderStateEntry.getValue())
            {
                if (!colliderStateEntry.getKey().collides(this))
                {
                    colliderStateEntry.getKey().getGameObject().onTrigger2DExit(this);
                    colliderStateEntry.setValue(false);
                }
                else
                {
                    colliderStateEntry.getKey().getGameObject().onTrigger2DStays(this);
                }
            }
            else
            {
                if (colliderStateEntry.getKey().collides(this))
                {
                    colliderStateEntry.getKey().getGameObject().onTrigger2DEnter(this);
                    colliderStateEntry.setValue(true);
                }
            }
        }
    }

    @Override
    public boolean collides(float x, float y)
    {
        return rectangle.contains(x, y);
    }

    @Override
    public boolean collides(BoxCollider2D other)
    {
        return rectangle.overlaps(other.getRectangle());
    }
}
