package org.lebastudios.lebajumpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputManager extends InputAdapter
{
    private static InputManager instance;

    public static InputManager getInstance()
    {
        if (instance == null) instance = new InputManager();

        return instance;
    }

    private InputManager() {}

    public Runnable onDPress = () -> {};
    public Runnable onAPress = () -> {};
    public Runnable onDReleased = () -> {};
    public Runnable onAReleased = () -> {};

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.D: {
                onDPress.run();
                return true;
            }
            case Input.Keys.A: {
                onAPress.run();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.D: {
                onDReleased.run();
                return true;
            }
            case Input.Keys.A: {
                onAReleased.run();
                return true;
            }
        }

        return false;
    }
}
