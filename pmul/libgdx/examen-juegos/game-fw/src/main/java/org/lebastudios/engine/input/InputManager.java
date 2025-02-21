package org.lebastudios.engine.input;

import com.badlogic.gdx.InputProcessor;
import lombok.Getter;
import org.lebastudios.engine.events.Event;
import org.lebastudios.engine.events.Event4;
import org.lebastudios.engine.events.IEventMethod;
import org.lebastudios.engine.events.IEventMethod4;
import org.lebastudios.engine.util.LazyArrayList;

import java.util.HashMap;

public class InputManager implements InputProcessor
{
    private static InputManager instance;

    public static InputManager getInstance()
    {
        if (instance == null) instance = new InputManager();

        return instance;
    }

    private final HashMap<Integer, Event> onKeyDown = new HashMap<>();
    private final HashMap<Integer, Event> onKeyUp = new HashMap<>();
    private final Event4<Integer, Integer, Integer, Integer> onTouchDown = new Event4<>();
    @Getter private final LazyArrayList<IEventMethod> onAnyKeyDown = new LazyArrayList<>();

    private InputManager() {}

    public void addKeyDownListener(int keycode, IEventMethod runnable)
    {
        if (!onKeyDown.containsKey(keycode))
        {
            Event event = new Event();
            event.addListener(runnable);
            onKeyDown.put(keycode, event);
        }
        else
        {
            onKeyDown.get(keycode).addListener(runnable);
        }
    }

    public void removeKeyDownListener(int keycode, IEventMethod runnable)
    {
        onKeyDown.get(keycode).removeListener(runnable);
    }

    public void addKeyDownListener(IEventMethod runnable, int... keycodes)
    {
        for (int keycode : keycodes)
        {
            addKeyDownListener(keycode, runnable);
        }
    }

    public void clearKeyDownListeners()
    {
        onKeyDown.clear();
    }

    public void removeKeyDownListener(IEventMethod runnable, int... keycodes)
    {
        for (int keycode : keycodes)
        {
            onKeyDown.get(keycode).removeListener(runnable);
        }
    }

    public void addKeyUpListener(int keycode, IEventMethod runnable)
    {
        if (!onKeyUp.containsKey(keycode))
        {
            Event event = new Event();
            event.addListener(runnable);
            onKeyUp.put(keycode, event);
        }
        else
        {
            onKeyUp.get(keycode).addListener(runnable);
        }
    }

    public void removeKeyUpListener(int keycode, IEventMethod runnable)
    {
        onKeyUp.get(keycode).removeListener(runnable);
    }

    public void addKeyUpListener(IEventMethod runnable, int... keycodes)
    {
        for (int keycode : keycodes)
        {
            addKeyUpListener(keycode, runnable);
        }
    }

    public void addTouchDownListener(IEventMethod4<Integer, Integer, Integer, Integer> touchDownListener)
    {
        onTouchDown.addListener(touchDownListener);
    }

    public void removeTouchDownListener(IEventMethod4<Integer, Integer, Integer, Integer> touchDownListener)
    {
        onTouchDown.removeListener(touchDownListener);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        onAnyKeyDown.update();
        onAnyKeyDown.forEach(IEventMethod::invoke);

        if (onKeyDown.containsKey(keycode))
        {
            onKeyDown.get(keycode).invoke();

            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        if (onKeyUp.containsKey(keycode))
        {
            onKeyUp.get(keycode).invoke();

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
        onTouchDown.invoke(screenX, screenY, pointer, button);

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
}
