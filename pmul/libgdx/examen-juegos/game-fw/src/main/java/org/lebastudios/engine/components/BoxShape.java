package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoxShape extends Component
{
    private float width;
    private float height;
    private boolean filled = false;
    private Color color = Color.BLACK;

    private ShapeRenderer shapeRenderer;

    @Override
    public void onStart()
    {
        shapeRenderer = this.getGameObject().getScene().getShapeRenderer();
    }

    @Override
    public void onRender(SpriteBatch batch)
    {
        if (filled)
        {
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        }

        shapeRenderer.setColor(color);

        shapeRenderer.rect(
            this.getTransform().getPosition().x - width / 2,
            this.getTransform().getPosition().y - height / 2,
            width,
            height
        );

        if (filled)
        {
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        }
    }
}
