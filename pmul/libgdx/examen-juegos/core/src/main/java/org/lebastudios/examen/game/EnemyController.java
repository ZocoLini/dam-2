package org.lebastudios.examen.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import lombok.Setter;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.components.*;
import org.lebastudios.examen.world.WorldConfig;

@Setter
public abstract class EnemyController extends Component
{
    private static final int MAX_REBOTES = 5;
    private static final float CELERITY = 90;
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
        rebotes = ((int) (Math.random() * (MAX_REBOTES - 1))) + 1;
    }

    @Override
    public void onUpdate(float deltaTime)
    {
        this.getTransform().translate(direccion * CELERITY * deltaTime, 0, 0);

        if (rebota() && Math.abs(this.getTransform().getPosition().x) > WorldConfig.WORLD_WIDTH / 2f && rebotes > 1)
        {
            rebotes--;
            direccion *= -1;
            textRenderer.setText(rebotes + "");

            if (rebotes == 1)
            {
                pintarRojo();
            }
        }
        else
        {
            if (Math.abs(this.getTransform().getPosition().x) > WorldConfig.WORLD_WIDTH / 2f + 30)
            {
                this.getGameObject().setEnabled(false);
            }
        }
    }

    public boolean rebota() { return true; }
    public abstract void pintarRojo();

    public static GameObject createCircleEnemy()
    {
        GameObject enemy = new GameObject(new Transform(WorldConfig.WORLD_WIDTH, 0, 0));
        enemy.getMetadata().setName(CircleEnemyController.GO_NAME);

        CircleCollider2D circleCollider2D = new CircleCollider2D();
        circleCollider2D.setRadius(7.5f);
        circleCollider2D.setLayer("enemy");
        enemy.addComponent(circleCollider2D);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(7.5f);
        circleShape.setFilled(true);
        circleShape.setColor(Color.ORANGE);
        enemy.addComponent(circleShape);

        TextRenderer lifes = new TextRenderer();
        enemy.addComponent(lifes);

        enemy.addComponent(new CircleEnemyController());

        return enemy;
    }

    public static GameObject createBoxEnemy()
    {
        GameObject enemy = new GameObject(new Transform(WorldConfig.WORLD_WIDTH, 0, 0));
        enemy.getMetadata().setName(BoxEnemyController.GO_NAME);

        BoxCollider2D boxCollider2D = new BoxCollider2D();
        boxCollider2D.setWidth(15);
        boxCollider2D.setHeigth(15);
        boxCollider2D.setLayer("enemy");
        enemy.addComponent(boxCollider2D);

        BoxShape boxShape = new BoxShape();
        boxShape.setWidth(15);
        boxShape.setHeight(15);
        boxShape.setFilled(true);
        boxShape.setColor(Color.ORANGE);
        enemy.addComponent(boxShape);

        TextRenderer lifes = new TextRenderer();
        enemy.addComponent(lifes);

        enemy.addComponent(new BoxEnemyController());

        return enemy;
    }

    public static GameObject createCrossEnemy()
    {
        GameObject enemy = new GameObject(new Transform(WorldConfig.WORLD_WIDTH, 0, 0));
        enemy.getMetadata().setName(CrossEnemyController.GO_NAME);

        BoxCollider2D boxCollider2D = new BoxCollider2D();
        boxCollider2D.setWidth(15);
        boxCollider2D.setHeigth(15);
        boxCollider2D.setLayer("enemy");
        enemy.addComponent(boxCollider2D);

        LineShape lineShape = new LineShape();
        lineShape.setColor(Color.ORANGE);
        lineShape.setStart(new Vector2(0, -7.5f));
        lineShape.setEnd(new Vector2(0, 7.5f));
        enemy.addComponent(lineShape);

        lineShape = new LineShape();
        lineShape.setColor(Color.ORANGE);
        lineShape.setStart(new Vector2(-7.5f, 0));
        lineShape.setEnd(new Vector2(7.5f, 0));
        enemy.addComponent(lineShape);

        enemy.addComponent(new CrossEnemyController());

        return enemy;
    }
}
