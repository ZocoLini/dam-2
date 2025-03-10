package org.lebastudios.engine.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;
import org.lebastudios.engine.GameObject;
import org.lebastudios.engine.coroutine.IEnumerator;

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

    public final void startCoroutine(IEnumerator coroutine)
    {
        getGameObject().getScene().startCoroutine(coroutine);
    }

    public void onAwake() {}
    public void onStart() {}
    public void onPhysicsUpdate(float deltaTime) {}
    public void onUpdate(float deltaTime) {}
    public void onRender(SpriteBatch batch) {}

    public void onDestroy() {}

    public void onDisable() {}
    public void onEnable() {}

    public void onTrigger2DEnter(Collider2D collider2D) {}
    public void onTrigger2DExit(Collider2D collider2D) {}
    public void onTrigger2DStays(Collider2D collider2D) {}
    public void onClicked() {}

    public void onDispose() {}
}
