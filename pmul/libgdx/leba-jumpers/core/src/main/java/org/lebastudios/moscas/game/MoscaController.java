package org.lebastudios.moscas.game;

import com.badlogic.gdx.Input;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.input.InputManager;

public class MoscaController extends Component
{
    private int direccion = 0;
    private int velocidad = 60;

    @Override
    public void onStart()
    {
        InputManager.getInstance().addKeyDownListener(Input.Keys.D, () -> direccion = 1);
        InputManager.getInstance().addKeyDownListener(Input.Keys.RIGHT, () -> direccion = 1);
        InputManager.getInstance().addKeyUpListener(Input.Keys.D, () -> direccion = 0);
        InputManager.getInstance().addKeyUpListener(Input.Keys.RIGHT, () -> direccion = 0);
        InputManager.getInstance().addKeyDownListener(Input.Keys.A, () -> direccion = -1);
        InputManager.getInstance().addKeyDownListener(Input.Keys.LEFT, () -> direccion = -1);
        InputManager.getInstance().addKeyUpListener(Input.Keys.A, () -> direccion = 0);
        InputManager.getInstance().addKeyUpListener(Input.Keys.LEFT, () -> direccion = 0);
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
