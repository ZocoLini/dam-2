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
        life = (int) (Math.random() * 5 + 3);

        textRenderer = this.getGameObject().getComponent(TextRenderer.class);
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
        if (true)
        {
            System.out.println("Enter");
        }

        if (other.getGameObject().getMetadata().getTag().equals("Bullet"))
        {
            life--;

            if (life <= 0)
            {
                this.getGameObject().destroy();
            }
        }

    }

    @Override
    public void onTrigger2DStays(Collider2D collider2D)
    {
        System.out.println("Stay");
    }

    @Override
    public void onTrigger2DExit(Collider2D collider2D)
    {
        System.out.println("Exit");
    }
}
