package org.lebastudios.lebajumpers.framework;

import com.badlogic.gdx.utils.Array;
import org.lebastudios.lebajumpers.framework.components.Component;
import org.lebastudios.lebajumpers.framework.components.Transform;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject
{
    private final List<Component> components;

    public GameObject()
    {
        components = new ArrayList<>();

        components.add(new Transform());
    }

    public final boolean addComponent(Component component)
    {
        return components.add(component);
    }

    public final boolean removeComponent(Component component)
    {
        return components.remove(component);
    }

    public final <T extends Component> T getComponent(Class<T> component)
    {
        for (var comp : components)
        {
            if (comp.getClass().equals(component)) return (T) comp;
        }

        return null;
    }

    public abstract void onCreate();

    public abstract void onUpdate();

    public abstract void onFixedUpdate();

    public abstract void onDestroy();

    public abstract void onCollision2DEnter();

    public abstract void onCollision2DExit();

    public abstract void onTrigger2DEnter();

    public abstract void onTrigger2DExit();
}
