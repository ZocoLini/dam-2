package org.lebastudios.examen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import lombok.Getter;
import org.lebastudios.engine.components.CircleCollider2D;
import org.lebastudios.engine.components.CircleShape;
import org.lebastudios.engine.components.Collider2D;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.events.IEventMethod;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.examen.GameState;
import org.lebastudios.examen.world.WorldConfig;

public class PlayerController extends Component
{
    private final IEventMethod onGoUpKeyDown = () -> direccion = 1;
    private final IEventMethod onGoUpKeyUp =
        () -> direccion = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN) ? -1 : 0;
    private final IEventMethod onGoDownKeyDown = () -> direccion = -1;
    private final IEventMethod onGoDownKeyUp =
        () -> direccion = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) ? 1 : 0;

    private final IEventMethod spaceAction = () ->
    {
        actualShape = switch (PlayerController.this.actualShape)
        {
            case CircleEnemyController.GO_NAME -> BoxEnemyController.GO_NAME;
            case BoxEnemyController.GO_NAME -> CrossEnemyController.GO_NAME;
            case CrossEnemyController.GO_NAME -> CircleEnemyController.GO_NAME;
            default -> throw new IllegalStateException("[ERROR] Unknown shape in player detected");
        };

        updateShape();
    };

    private int direccion;
    private int sizeIncrementDirection = 1;
    private final float SIZE_INCREMENT_SPEED = 20f;
    private float actualSize;

    private CircleCollider2D collider2D;
    private CircleShape circleShape;

    private static final float CELERITY = 75;
    private static final int MAX_RADIUS = 50;
    private static final int MIN_RADIUS = 10;

    private String actualShape = CircleEnemyController.GO_NAME;
    @Getter private int life = GameState.getInstance().getDifficulty();

    @Override
    public void onStart()
    {
        collider2D = this.getGameObject().getComponent(CircleCollider2D.class);
        circleShape = this.getGameObject().getComponent(CircleShape.class);

        actualSize = MIN_RADIUS;
        collider2D.setRadius(actualSize);
        circleShape.setRadius(actualSize);

        // TODO: Remove this listener when is the moment
        InputManager.getInstance().addKeyDownListener(onGoDownKeyDown, Input.Keys.S, Input.Keys.DOWN);
        InputManager.getInstance().addKeyDownListener(onGoDownKeyUp, Input.Keys.W, Input.Keys.UP);
        InputManager.getInstance().addKeyUpListener(onGoUpKeyDown, Input.Keys.S, Input.Keys.DOWN);
        InputManager.getInstance().addKeyUpListener(onGoUpKeyUp, Input.Keys.W, Input.Keys.UP);

        InputManager.getInstance().addKeyDownListener(Input.Keys.SPACE, spaceAction);
    }

    private void updateShape()
    {

    }

    @Override
    public void onUpdate(float deltaTime)
    {
        GameState.getInstance().addTime(deltaTime);

        if (actualSize >= MAX_RADIUS || actualSize <= MIN_RADIUS)
        {
            sizeIncrementDirection *= -1;
        }

        actualSize += SIZE_INCREMENT_SPEED * deltaTime * sizeIncrementDirection;

        circleShape.setRadius(actualSize);
        collider2D.setRadius(actualSize);

        if (this.getTransform().getPosition().y > WorldConfig.WORLD_HEIGHT / 2f) return;
        if (this.getTransform().getPosition().y < - WorldConfig.WORLD_HEIGHT / 2f + GameScene.INFO_DISPLAY_HEIGTH)
        {

        }

        this.getTransform().translate(
            0,
            CELERITY * direccion * deltaTime,
            0
        );
    }

    @Override
    public void onTrigger2DEnter(Collider2D collider2D)
    {
        if (collider2D.getGameObject().getMetadata().getName().equals(actualShape)) return;

        life--;

        if (life < 0)
        {
            System.out.println("GameOver");
        }
    }
}
