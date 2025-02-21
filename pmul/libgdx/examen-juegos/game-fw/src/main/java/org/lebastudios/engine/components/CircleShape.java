package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

public class CircleShape extends Component
{
    @Setter @Getter private float radius;
    @Setter @Getter private boolean filled;

    @Override
    public void onRender(SpriteBatch batch)
    {
        this.getGameObject().getScene().getShapeRenderer().circle(
            this.getTransform().getPosition().x,
            this.getTransform().getPosition().y,
            radius
        );
    }
}
