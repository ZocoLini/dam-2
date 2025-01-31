package org.lebastudios.engine.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class InputManager implements InputProcessor
{
    private static InputManager instance;

    public static InputManager getInstance()
    {
        if (instance == null) instance = new InputManager();

        return instance;
    }

    private final HashMap<Integer, Array<Runnable>> onKeyDown = new HashMap<>();
    private final HashMap<Integer, Array<Runnable>> onKeyUp = new HashMap<>();
    private final Array<OnTouchDownListener> onTouchDown = new Array<>(false, 1);

    private InputManager() {}

    public void addKeyDownListener(int keycode, Runnable runnable)
    {
        if (!onKeyDown.containsKey(keycode))
        {
            final var runnables = new Array<>(new Runnable[]{runnable});
            runnables.ordered = false;
            onKeyDown.put(keycode, runnables);
        }
        else
        {
            onKeyDown.get(keycode).add(runnable);
        }
    }

    public void removeKeyDownListener(int keycode, Runnable runnable)
    {
        onKeyDown.get(keycode).removeValue(runnable, true);
    }

    public void addKeyDownListener(Runnable runnable, int... keycodes)
    {
        for (int keycode : keycodes)
        {
            addKeyDownListener(keycode, runnable);
        }
    }

    public void addKeyUpListener(int keycode, Runnable runnable)
    {
        if (!onKeyUp.containsKey(keycode))
        {
            final var runnables = new Array<>(new Runnable[]{runnable});
            runnables.ordered = false;
            onKeyUp.put(keycode, runnables);
        }
        else
        {
            onKeyUp.get(keycode).add(runnable);
        }
    }

    public void removeKeyUpListener(int keycode, Runnable runnable)
    {
        onKeyUp.get(keycode).removeValue(runnable, true);
    }

    public void addKeyUpListener(Runnable runnable, int... keycodes)
    {
        for (int keycode : keycodes)
        {
            addKeyUpListener(keycode, runnable);
        }
    }

    public void addTouchDownListener(OnTouchDownListener touchDownListener)
    {
        onTouchDown.add(touchDownListener);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if (onKeyDown.containsKey(keycode))
        {
            for (Runnable runnable : onKeyDown.get(keycode))
            {
                runnable.run();
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        if (onKeyUp.containsKey(keycode))
        {
            for (Runnable runnable : onKeyUp.get(keycode))
            {
                runnable.run();
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        for (var touchDowm : onTouchDown)
        {
            touchDowm.onTouchDown(screenX, screenY, pointer, button);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        return false;
    }

    public interface OnTouchDownListener
    {
        void onTouchDown(int screenX, int screenY, int pointer, int button);
    }
}
