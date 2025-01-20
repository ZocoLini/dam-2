package org.lebastudios.lebajumpers.framework.gameobjects;

import org.lebastudios.lebajumpers.framework.components.Component;
import org.lebastudios.lebajumpers.framework.components.Transform;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject
{
    private final List<Component> components;
    protected final Transform transform;

    public GameObject()
    {
        components = new ArrayList<>();

        this.transform = new Transform();
        components.add(transform);
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

    public void onCreate() {}

    public void onStart() {}

    public void onUpdate() {}

    public void onFixedUpdate() {}

    public void onDestroy() {}

    public void onCollision2DEnter() {}

    public void onCollision2DExit() {}

    public void onTrigger2DEnter() {}

    public void onTrigger2DExit() {}
}
