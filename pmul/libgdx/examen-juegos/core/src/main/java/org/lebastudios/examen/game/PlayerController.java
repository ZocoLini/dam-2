package org.lebastudios.examen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import org.lebastudios.engine.components.*;
import org.lebastudios.engine.events.IEventMethod;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.examen.ExamenGameAdapter;
import org.lebastudios.examen.GameState;
import org.lebastudios.examen.world.WorldConfig;

import java.util.List;

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

    private void updateShape() {
        PlayerController.this.boxShape.disable();
        PlayerController.this.circleShape.disable();
        PlayerController.this.lineShapes.forEach(LineShape::disable);

        switch (PlayerController.this.actualShape)
        {
            case BoxEnemyController.GO_NAME -> PlayerController.this.boxShape.enable();
            case CircleEnemyController.GO_NAME -> PlayerController.this.circleShape.enable();
            case CrossEnemyController.GO_NAME -> PlayerController.this.lineShapes.forEach(LineShape::enable);
        }
    }

    private int direccion = 0;
    private int sizeIncrementDirection = 1;
    private static final float SIZE_INCREMENT_SPEED = 5;
    private float actualSize;

    private CircleCollider2D collider2D;
    private CircleShape circleShape;
    private BoxShape boxShape;
    private List<LineShape> lineShapes;

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
        boxShape = this.getGameObject().getComponent(BoxShape.class);
        lineShapes = this.getGameObject().getComponents(LineShape.class);

        updateShape();

        updateSize(MIN_RADIUS + 1);

        InputManager.getInstance().addKeyDownListener(onGoDownKeyDown, Input.Keys.S, Input.Keys.DOWN);
        InputManager.getInstance().addKeyDownListener(onGoUpKeyDown, Input.Keys.W, Input.Keys.UP);
        InputManager.getInstance().addKeyUpListener(onGoDownKeyUp, Input.Keys.S, Input.Keys.DOWN);
        InputManager.getInstance().addKeyUpListener(onGoUpKeyUp, Input.Keys.W, Input.Keys.UP);

        InputManager.getInstance().addKeyDownListener(Input.Keys.SPACE, spaceAction);

        InputManager.getInstance().addKeyDownListener(Input.Keys.P,
            () -> ExamenGameAdapter.getInstance().setScene(new PauseScene()));
    }

    @Override
    public void onUpdate(float deltaTime)
    {
        GameState.getInstance().addTime(deltaTime);

        if (actualSize >= MAX_RADIUS || actualSize <= MIN_RADIUS)
        {
            sizeIncrementDirection *= -1;
        }

        updateSize(actualSize + SIZE_INCREMENT_SPEED * deltaTime * sizeIncrementDirection);

        if (this.getTransform().getPosition().y > WorldConfig.WORLD_HEIGHT / 2f && direccion > 0) return;
        if (this.getTransform().getPosition().y < -WorldConfig.WORLD_HEIGHT / 2f + GameScene.INFO_DISPLAY_HEIGTH && direccion < 0) return;

        this.getTransform().translate(
            0,
            CELERITY * direccion * deltaTime,
            0
        );
    }

    private void updateSize(float newSize)
    {
        actualSize = newSize;

        collider2D.setRadius(actualSize / 2f);

        circleShape.setRadius(actualSize / 2f);

        boxShape.setHeight(actualSize);
        boxShape.setWidth(actualSize);

        float half = actualSize / 2f;

        lineShapes.get(0).setStart(new Vector2(-half, 0));
        lineShapes.get(0).setEnd(new Vector2(half, 0));

        lineShapes.get(1).setStart(new Vector2(0, half));
        lineShapes.get(1).setEnd(new Vector2(0, -half));
    }

    @Override
    public void onTrigger2DEnter(Collider2D collider2D)
    {
        if (collider2D.getGameObject().getMetadata().getName().equals(actualShape)) return;

        life--;

        if (life <= 0)
        {
            GameState.getInstance().finishRun();
        }
    }
}
