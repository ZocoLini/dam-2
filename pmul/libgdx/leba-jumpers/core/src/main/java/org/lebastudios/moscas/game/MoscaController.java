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
