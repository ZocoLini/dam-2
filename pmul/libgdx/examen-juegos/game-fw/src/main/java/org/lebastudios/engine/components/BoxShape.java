package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;
import lombok.Setter;

public class BoxShape extends Component
{
    @Setter @Getter private float width;
    @Setter @Getter private float height;
    @Setter @Getter private boolean filled = false;
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
        else
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
    }
}
