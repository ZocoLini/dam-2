package org.lebastudios.engine.components;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import lombok.Getter;
import lombok.Setter;
import org.lebastudios.engine.Camera;
import org.lebastudios.engine.input.InputManager;

@Getter
@Setter
public class BoxCollider2D extends Component
{
    private boolean isTrigger;
    private Rectangle rectangle;
    @Setter @Getter private float width;
    @Setter @Getter private float heigth;

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
    }

    public boolean collides(float x, float y)
    {
        return rectangle.contains(x, y);
    }
}
