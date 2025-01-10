package org.lebastudios.desktopapp.events;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is used for the event system. Defines the methods that all AppEvents must have.
 *
 * @param <T> The type of the event method. This is the interface that the event method must implement and
 *         should be created in the AppEvent class.
 */
public abstract class EventHandler<T>
{
    private final List<T> listeners = new CopyOnWriteArrayList<>();
    private final List<WeakReference<T>> weakListeners = new CopyOnWriteArrayList<>();

    public synchronized void addListener(T listener)
    {
        if (listener == null)
        {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        if (!hasListener(listener))
        {
            listeners.add(listener);
        }
    }

    public synchronized void addWeakListener(T listener)
    {
        if (listener == null)
        {
            throw new IllegalArgumentException("Listener cannot be null");
        }

        if (!hasListener(listener))
        {
            weakListeners.add(new WeakReference<>(listener));
        }
    }

    public synchronized void removeListener(T listener)
    {
        if (listener == null) return;

        if (!listeners.remove(listener))
        {
            weakListeners.removeIf(w -> w.get() == listener);
        }
        ;
    }

    public synchronized void clearListeners()
    {
        listeners.clear();
    }

    public boolean hasListener(T listener)
    {
        if (listener == null) return false;

        return listeners.contains(listener) || weakListeners.stream().anyMatch(w -> w.get() == listener);
    }

    public void removeNullListeners()
    {
        listeners.removeIf(Objects::isNull);
        weakListeners.removeIf(w -> w.get() == null);
    }

    protected List<T> getActiveListeners()
    {
        removeNullListeners();

        var list = new ArrayList<>(listeners);
        list.addAll(weakListeners.stream().map(WeakReference::get).toList());

        return list;
    }
}
