package org.lebastudios.engine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LazyArrayList<T> extends ArrayList<T>
{
    private final List<T> toAdd = new ArrayList<>();
    private final List<T> toRemove = new ArrayList<>();

    public void update()
    {
        update(_ -> {}, _ -> {});
    }

    public void update(Consumer<T> addConsumer, Consumer<T> removeConsumer)
    {
        if (toAdd.isEmpty() && toRemove.isEmpty()) return;


        for (T item : toRemove)
        {
            if (this.remove(item))
            {
                removeConsumer.accept(item);
            }
        }

        for (T item : toAdd)
        {
            this.add(item);
            addConsumer.accept(item);
        }

        toAdd.clear();
        toRemove.clear();
    }

    public void addLazy(T t)
    {
        toAdd.add(t);
    }

    public void removeLazy(T t)
    {
        toRemove.add(t);
    }
}
