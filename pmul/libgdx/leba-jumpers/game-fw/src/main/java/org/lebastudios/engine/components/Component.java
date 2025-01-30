package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;
import org.lebastudios.engine.GameObject;

public abstract class Component
{
    @Getter private boolean enabled = true;
    @Setter private GameObject gameObject;

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

    public final GameObject getGameObject()
    {
        return gameObject;
    }
    public final Transform getTransform()
    {
        return getGameObject().getTransform();
    }

    public void onAwake() {}
    public void onStart() {}
    public void onPhysicsUpdate(float deltaTime) {}
    public void onUpdate(float deltaTime) {}
    public void onRender(SpriteBatch batch) {}

    public void onDestroy() {}
    public void onDisable() {}

    public void onEnable() {}

    public void onDispose() {}
}
