package org.lebastudios.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;
import org.lebastudios.engine.components.Collider2D;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.components.Transform;
import org.lebastudios.engine.util.LazyArrayList;

import java.util.ArrayList;
import java.util.List;

public final class GameObject
{
    @Getter private final GameObjectMetadata metadata;
    private final Array<Component> components = new Array<>(false, 16);
    @Getter private final LazyArrayList<Collider2D> colliders = new LazyArrayList<>();
    @Getter private final Transform transform;
    @Setter @Getter private Scene scene;
    @Getter private boolean enabled = true;

    public GameObject(Transform transform, GameObjectMetadata metadata)
    {
        this.metadata = metadata;
        this.transform = transform;
        addComponent(transform);
    }

    public GameObject(Transform transform)
    {
        this(transform, new GameObjectMetadata());
    }

    public void addComponent(Component component)
    {
        if (component instanceof Collider2D)
        {
            colliders.addLazy((Collider2D) component);
        }

        components.add(component);
        component.setGameObject(this);
    }

    public void removeComponent(Component component)
    {
        if (component instanceof Collider2D)
        {
            colliders.removeLazy((Collider2D) component);
        }

        components.removeValue(component, true);
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

    public <T extends Component> List<T> getComponents(Class<T> type)
    {
        List<T> list = new ArrayList<>();

        for (Component component : components)
        {
            if (type.isInstance(component))
            {
                list.add(type.cast(component));
            }
        }

        return list;
    }

    public void create()
    {
        for (Component component : components)
        {
            component.onStart();

            if (enabled)
            {
                component.enable();
            }
        }
    }

    public void physicsUpdate(float deltaTime)
    {
        if (!enabled) return;

        this.colliders.update();

        for (Component component : components)
        {
            if (!component.isEnabled()) continue;
            component.onPhysicsUpdate(deltaTime);
        }
    }

    public void update(float deltaTime)
    {
        if (!enabled) return;

        for (Component component : components)
        {
            if (!component.isEnabled()) continue;
            component.onUpdate(deltaTime);
        }
    }

    public void render(SpriteBatch batch)
    {
        if (!enabled) return;

        for (Component component : components)
        {
            if (!component.isEnabled()) continue;
            component.onRender(batch);
        }
    }

    public void onTrigger2DEnter(Collider2D collider2D)
    {
        if (!enabled) return;

        for (Component component : components)
        {
            component.onTrigger2DEnter(collider2D);
        }
    }

    public void onTrigger2DExit(Collider2D collider2D)
    {
        if (!enabled) return;

        for (Component component : components)
        {
            component.onTrigger2DExit(collider2D);
        }
    }

    public void onTrigger2DStays(Collider2D collider2D)
    {
        if (!enabled) return;

        for (Component component : components)
        {
            component.onTrigger2DStays(collider2D);
        }
    }

    public void onClicked()
    {
        if (!enabled) return;

        for (Component component : components)
        {
            component.onClicked();
        }
    }

    public void setEnabled(boolean enabled)
    {
        if (this.enabled == enabled) return;

        this.enabled = enabled;

        if (enabled)
        {
            for (Component component : components)
            {
                component.enable();
            }
        }
        else
        {
            for (Component component : components)
            {
                component.disable();
            }
        }
    }

    public void destroy()
    {
        for (Component component : components)
        {
            component.onDestroy();
        }

        this.scene.removeGameObject(this);
    }

    public void instantiate(GameObject gameObject)
    {
        this.scene.addGameObject(gameObject);
    }

    public void dispose()
    {
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
