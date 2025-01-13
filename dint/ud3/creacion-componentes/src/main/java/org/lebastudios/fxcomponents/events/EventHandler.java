package org.lebastudios.fxcomponents.events;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for the event system. Defines the methods that all AppEvents must have.
 *
 * @param <T> The type of the event method. This is the interface that the event method must implement and
 *         should be created in the AppEvent class.
 */
public abstract class EventHandler<T>
{
    protected final List<T> listeners = new ArrayList<>();

    public void addListener(T listener)
    {
        listeners.add(listener);
    }

    public void removeListener(T listener)
    {
        listeners.remove(listener);
    }

    public void clearListeners()
    {
        listeners.clear();
    }

    public List<T> getListeners()
    {
        return listeners;
    }

    public boolean hasListener(T listener)
    {
        return listeners.contains(listener);
    }
}
