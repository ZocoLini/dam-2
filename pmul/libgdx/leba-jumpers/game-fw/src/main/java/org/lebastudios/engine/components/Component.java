package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Component
{
    private boolean enabled = true;

    public Component()
    {
        onAwake();
    }

    public final void disable()
    {
        if (enabled)
        {
            enabled = false;
            onDisable();
        }
    }

    public final void enable()
    {
        if (!enabled)
        {
            enabled = true;
            onEnable();
        }
    }

    public final void destroy()
    {
        onDestroy();
    }

    public void onAwake() {}
    public void onStart() {}
    public void onUpdate(float deltaTime) {}
    public void onRender(SpriteBatch batch) {}

    public void onDestroy() {}

    public void onDisable() {}
    public void onEnable() {}

    public void onDispose()
    {

    }
}
