package org.lebastudios.engine.lebajumpers;

import com.badlogic.gdx.Input;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.input.InputManager;

public class FingerController extends Component
{
    private int direccion = 0;
    private int velocidad = 60;

    @Override
    public void onStart()
    {
        InputManager.getInstance().addKeyDown(Input.Keys.D, () -> direccion = 1);
        InputManager.getInstance().addKeyDown(Input.Keys.RIGHT, () -> direccion = 1);
        InputManager.getInstance().addKeyUp(Input.Keys.D, () -> direccion = 0);
        InputManager.getInstance().addKeyUp(Input.Keys.RIGHT, () -> direccion = 0);
        InputManager.getInstance().addKeyDown(Input.Keys.A, () -> direccion = -1);
        InputManager.getInstance().addKeyDown(Input.Keys.LEFT, () -> direccion = -1);
        InputManager.getInstance().addKeyUp(Input.Keys.A, () -> direccion = 0);
        InputManager.getInstance().addKeyUp(Input.Keys.LEFT, () -> direccion = 0);
    }

    @Override
    public void onUpdate(float deltaTime)
    {
        this.getGameObject().getTransform().translate(
            velocidad * direccion * deltaTime,
            0,
            0
        );
    }
}
