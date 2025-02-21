package org.lebastudios.examen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.lebastudios.engine.components.CircleCollider2D;
import org.lebastudios.engine.components.CircleShape;
import org.lebastudios.engine.components.Collider2D;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.events.IEventMethod;
import org.lebastudios.engine.input.InputManager;
import org.lebastudios.examen.world.WorldConfig;

public class PlayerController extends Component
{
    private final IEventMethod onGoUpKeyDown = () -> direccion = 1;
    private final IEventMethod onGoUpKeyUp = () -> direccion = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN) ? -1 : 0;
    private final IEventMethod onGoDownKeyDown = () -> direccion = -1;
    private final IEventMethod onGoDownKeyUp = () -> direccion = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP) ? 1 : 0;

    private final IEventMethod spaceAction = () ->
    {

    };

    private int direccion;

    private CircleCollider2D collider2D;
    private CircleShape circleShape;

    private static final float CELERITY = 75;
    private static final int MAX_RADIUS = 50;
    private static final int MIN_RADIUS = 50;

    @Override
    public void onStart()
    {
        collider2D = this.getGameObject().getComponent(CircleCollider2D.class);
        circleShape = this.getGameObject().getComponent(CircleShape.class);

        // TODO: Remove this listener when is the moment
        InputManager.getInstance().addKeyDownListener(onGoDownKeyDown, Input.Keys.S, Input.Keys.DOWN);
        InputManager.getInstance().addKeyDownListener(onGoDownKeyUp, Input.Keys.W, Input.Keys.UP);
        InputManager.getInstance().addKeyUpListener(onGoUpKeyDown, Input.Keys.S, Input.Keys.DOWN);
        InputManager.getInstance().addKeyUpListener(onGoUpKeyUp, Input.Keys.W, Input.Keys.UP);

        InputManager.getInstance().addKeyDownListener(Input.Keys.SPACE, spaceAction);
    }

    @Override
    public void onUpdate(float deltaTime)
    {


        if (Math.abs(this.getTransform().getPosition().y) > WorldConfig.WORLD_HEIGHT / 2f) return;

        this.getTransform().translate(
            0,
            CELERITY * direccion * deltaTime,
            0
        );
    }

    @Override
    public void onTrigger2DEnter(Collider2D collider2D)
    {
        System.out.println(123);
    }
}
