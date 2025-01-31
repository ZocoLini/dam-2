package org.lebastudios.engine.input;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InputManager implements InputProcessor
{
    private static InputManager instance;

    public static InputManager getInstance()
    {
        if (instance == null) instance = new InputManager();

        return instance;
    }

    private final HashMap<Integer, List<Runnable>> onKeyDown = new HashMap<>();
    private final HashMap<Integer, List<Runnable>> onKeyUp = new HashMap<>();
    private final List<OnTouchDownListener> onTouchDown = new ArrayList<>();

    private InputManager() {}

    public void addKeyDownListener(int keycode, Runnable runnable)
    {
        if (!onKeyDown.containsKey(keycode))
        {
            onKeyDown.put(keycode, new ArrayList<>(List.of(runnable)));
        }
        else
        {
            onKeyDown.get(keycode).add(runnable);
        }
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
            onKeyUp.put(keycode, new ArrayList<>(List.of(runnable)));
        }
        else
        {
            onKeyUp.get(keycode).add(runnable);
        }
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
