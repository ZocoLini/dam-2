package org.lebastudios.finger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.engine.input.InputManager;

public class FingerController extends Component
{
    private int direccion = 0;
    private float velocidad = 30;

    private Transform transform;

    @Override
    public void onStart()
    {
        transform = this.getTransform();

        InputManager.getInstance().addKeyDownListener(() ->
        {
            direccion = 1;
        }, Input.Keys.W, Input.Keys.UP);
        InputManager.getInstance().addKeyUpListener(() ->
        {
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            {
                direccion = -1;
            }
            else
            {
                direccion = 0;
            }

        }, Input.Keys.W, Input.Keys.UP);
        InputManager.getInstance().addKeyDownListener(() ->
        {
            direccion = -1;
        }, Input.Keys.S, Input.Keys.DOWN);
        InputManager.getInstance().addKeyUpListener(() ->
        {
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            {
                direccion = 1;
            }
            else
            {
                direccion = 0;
            }
        }, Input.Keys.S, Input.Keys.DOWN);
    }

    @Override
    public void onUpdate(float deltaTime)
    {
        System.out.println(123);

        transform.translate(
            0,
            velocidad * direccion * deltaTime,
            0
        );
    }
}
