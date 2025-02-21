package org.lebastudios.examen.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import lombok.Setter;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.components.*;
import org.lebastudios.examen.GameState;
import org.lebastudios.examen.world.WorldConfig;

@Setter
public abstract class EnemyController extends Component
{
    private static final float CELERITY = 60;
    private int direccion = 0;
    protected int rebotes;
    private TextRenderer textRenderer;

    @Override
    public void onStart()
    {
        textRenderer = this.getGameObject().getComponent(TextRenderer.class);
        resetState();
    }

    public void resetState()
    {
        rebotes = ((int) (Math.random() * (GameState.getInstance().getDifficulty() - 1))) + 1;
    }

    @Override
    public void onUpdate(float deltaTime)
    {
        this.getTransform().translate(direccion * CELERITY * deltaTime, 0, 0);

        if (Math.abs(this.getTransform().getPosition().x) > WorldConfig.WORLD_WIDTH / 2f && rebotes > 1)
        {
            rebotes--;
            direccion *= -1;
        }
        else
        {
            if (Math.abs(this.getTransform().getPosition().x) > WorldConfig.WORLD_WIDTH / 2f + 30)
            {
                this.getGameObject().setEnabled(false);
            }
        }
    }

    public static GameObject createCircleEnemy()
    {
        GameObject enemy = new GameObject(new Transform(0, 0, 0));
        enemy.getMetadata().setName(CircleEnemyController.GO_NAME);

        CircleCollider2D circleCollider2D = new CircleCollider2D();
        circleCollider2D.setRadius(15);
        circleCollider2D.setLayer("enemy");
        enemy.addComponent(circleCollider2D);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(17);
        circleShape.setFilled(true);
        circleShape.setColor(Color.RED);
        enemy.addComponent(circleShape);

        TextRenderer lifes = new TextRenderer();
        enemy.addComponent(lifes);

        enemy.addComponent(new CircleEnemyController());

        return enemy;
    }

    public static GameObject createBoxEnemy()
    {
        GameObject enemy = new GameObject(new Transform(0, 0, 0));
        enemy.getMetadata().setName(BoxEnemyController.GO_NAME);

        BoxCollider2D boxCollider2D = new BoxCollider2D();
        boxCollider2D.setWidth(15);
        boxCollider2D.setHeigth(15);
        boxCollider2D.setLayer("enemy");
        enemy.addComponent(boxCollider2D);

        BoxShape boxShape = new BoxShape();
        boxShape.setWidth(17);
        boxShape.setHeight(17);
        boxShape.setFilled(true);
        boxShape.setColor(Color.RED);
        enemy.addComponent(boxShape);

        TextRenderer lifes = new TextRenderer();
        enemy.addComponent(lifes);

        enemy.addComponent(new BoxEnemyController());

        return enemy;
    }

    public static GameObject createCrossEnemy()
    {
        GameObject enemy = new GameObject(new Transform(0, 0, 0));
        enemy.getMetadata().setName(CrossEnemyController.GO_NAME);

        BoxCollider2D boxCollider2D = new BoxCollider2D();
        boxCollider2D.setWidth(15);
        boxCollider2D.setHeigth(15);
        boxCollider2D.setLayer("enemy");
        enemy.addComponent(boxCollider2D);

        LineShape lineShape = new LineShape();
        lineShape.setColor(Color.RED);
        lineShape.setStart(new Vector2(0, -17));
        lineShape.setEnd(new Vector2(0, 17));
        enemy.addComponent(lineShape);

        lineShape = new LineShape();
        lineShape.setColor(Color.RED);
        lineShape.setStart(new Vector2(-17, 0));
        lineShape.setEnd(new Vector2(17, 0));
        enemy.addComponent(lineShape);

        enemy.addComponent(new CrossEnemyController());

        return enemy;
    }
}
