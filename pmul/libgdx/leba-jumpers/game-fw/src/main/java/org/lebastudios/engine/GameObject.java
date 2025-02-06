package org.lebastudios.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;
import org.lebastudios.engine.components.Collider2D;
import org.lebastudios.engine.components.Component;
import org.lebastudios.engine.components.Transform;

public final class GameObject
{
    @Getter private final GameObjectMetadata metadata;
    private final Array<Component> components = new Array<>();
    @Getter private final Transform transform;
    @Setter @Getter private Scene scene;

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
        components.add(component);
        component.setGameObject(this);
    }

    public void removeComponent(Component component)
    {
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

    public void onTrigger2DEnter(Collider2D collider2D)
    {
        for (Component component : components)
        {
            component.onTrigger2DEnter(collider2D);
        }
    }
    public void onTrigger2DExit(Collider2D collider2D) {
        for (Component component : components)
        {
            component.onTrigger2DExit(collider2D);
        }
    }
    public void onTrigger2DStays(Collider2D collider2D) {
        for (Component component : components)
        {
            component.onTrigger2DStays(collider2D);
        }
    }
    public void onClicked() {
        for (Component component : components)
        {
            component.onClicked();
        }
    }

    public void destroy()
    {
        for (Component component : components)
        {
            component.onDestroy();
        }

        this.scene.removeSceneObject(this);
    }

    public void instantiate(GameObject gameObject)
    {
        this.scene.addGameObject(gameObject);
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
