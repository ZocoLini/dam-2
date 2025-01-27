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

    public GameObject(Transform transform, GameObjectMetadata metadata)
    {
        this.metadata = metadata;
        addComponent(transform);
    }

    public GameObject(Transform transform)
    {
        this(transform, new GameObjectMetadata());
    }

    public void addComponent(Component component)
    {
        components.add(component);

        component.onStart();
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

    public void update(float deltaTime)
    {
        for (Component component : components)
        {
            component.onUpdate(deltaTime);
        }
    }

    public void render(SpriteBatch batch) {
        for (Component component : components)
        {
            component.onRender(batch);
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
