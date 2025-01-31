package org.lebastudios.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.components.Transform;

import java.util.ArrayList;
import java.util.List;

public final class GameObject
{
    private final GameObjectMetadata metadata;
    private final List<Component> components = new ArrayList<>();
    @Getter private final Transform transform;
    @Getter private final Scene scene;

    public GameObject(Transform transform, GameObjectMetadata metadata, Scene scene)
    {
        this.metadata = metadata;
        this.transform = transform;
        this.scene = scene;
        addComponent(transform);
    }

    public GameObject(Transform transform, Scene scene)
    {
        this(transform, new GameObjectMetadata(), scene);
    }

    public void addComponent(Component component)
    {
        components.add(component);
        component.setGameObject(this);
    }

    public void removeComponent(Component component)
    {
        components.remove(component);
    }

    public <T extends Component> T getComponent(Class<T> type)
    {
        for (Component component : components)
        {
            if (type.isInstance(component))
            {
                return type.cast(component);
            }
        }

        return null;
    }

    public void create() {
        for (Component component : components)
        {
            component.onStart();
        }
    }

    public void physicsUpdate(float deltaTime)
    {
        for (Component component : components)
        {
            if (!component.isEnabled()) continue;
            component.onPhysicsUpdate(deltaTime);
        }
    }

    public void update(float deltaTime)
    {
        for (Component component : components)
        {
            if (!component.isEnabled()) continue;
            component.onUpdate(deltaTime);
        }
    }

    public void render(SpriteBatch batch) {
        for (Component component : components)
        {
            if (!component.isEnabled()) continue;
            component.onRender(batch);
        }
    }

    public void onTrigger2DEnter()
    {
        for (Component component : components)
        {
            component.onTrigger2DEnter();
        }
    }
    public void onTrigger2DExit() {
        for (Component component : components)
        {
            component.onTrigger2DExit();
        }
    }
    public void onTrigger2DStays() {
        for (Component component : components)
        {
            component.onTrigger2DStays();
        }
    }
    public void onClicked() {
        for (Component component : components)
        {
            component.onClicked();
        }
    }

    public void dispose() {
        for (Component component : components)
        {
            component.onDispose();
        }
    }

    @Getter
    @Setter
    public static class GameObjectMetadata
    {
        private String name;
        private String tag;
    }
}
