package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoxShape extends Component
{
    private float width;
    private float height;

    @Override
    public void onRender(SpriteBatch batch)
    {
        this.getGameObject().getScene().getShapeRenderer().rect(
            this.getTransform().getPosition().x - width / 2,
            this.getTransform().getPosition().y - height / 2,
            width,
            height
        );
    }
}
