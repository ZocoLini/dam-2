package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;
import lombok.Setter;

public class CircleShape extends Component
{
    @Setter @Getter private float radius;
    @Setter @Getter private boolean filled;

    @Setter @Getter private Color color = Color.BLACK;

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

        shapeRenderer.circle(
            this.getTransform().getPosition().x,
            this.getTransform().getPosition().y,
            radius
        );

        if (filled)
        {
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        }
    }
}
