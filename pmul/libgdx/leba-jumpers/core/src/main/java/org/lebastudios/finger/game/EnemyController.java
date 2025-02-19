package org.lebastudios.finger.game;

import org.lebastudios.engine.components.Collider2D;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.components.TextRenderer;

public class EnemyController extends Component
{
    private int life = 1;
    private float speed = 50;
    private TextRenderer textRenderer;

    @Override
    public void onStart()
    {
        reasignLife();

        textRenderer = this.getGameObject().getComponent(TextRenderer.class);
    }

    public void reasignLife()
    {
        life = (int) (Math.random() * 5 + 3);
    }

    @Override
    public void onUpdate(float deltaTime)
    {
        textRenderer.setText(String.valueOf(life));

        this.getTransform().translate(
            -1 * speed * deltaTime,
            0,
            0
        );
    }

    @Override
    public void onTrigger2DEnter(Collider2D other)
    {
        if (other.getGameObject().getMetadata().getTag().equals("Bullet"))
        {
            life--;
            other.getGameObject().setEnabled(false);

            if (life <= 0)
            {
                this.getGameObject().setEnabled(false);
            }
        }

    }
}
