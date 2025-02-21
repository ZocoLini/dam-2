package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

public class LineShape extends Component
{
    @Setter @Getter private Vector2 start;
    @Setter @Getter private Vector2 end;

    @Setter @Getter private Color color = Color.BLACK;

    private ShapeRenderer shapeRenderer;
    private Transform transform;

    @Override
    public void onStart()
    {
        shapeRenderer = this.getGameObject().getScene().getShapeRenderer();
        transform = this.getTransform();
    }

    @Override
    public void onRender(SpriteBatch batch)
    {
        shapeRenderer.setColor(color);

        shapeRenderer.line(
            start.x + transform.getPosition().x, start.y + transform.getPosition().y,
            end.x + transform.getPosition().x, end.y + transform.getPosition().y
        );
    }
}
