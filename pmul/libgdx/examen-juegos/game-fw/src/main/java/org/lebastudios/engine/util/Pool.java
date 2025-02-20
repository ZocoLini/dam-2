package org.lebastudios.engine.util;

import org.lebastudios.engine.GameAdapter;

import java.util.HashMap;
import java.util.function.Supplier;

public class Pool<T>
{
    private final Supplier<T> supplier;
    private final HashMap<T, Boolean> objects = new HashMap<>();

    public Pool(Supplier<T> supplier, T... objects)
    {
        this.supplier = supplier;

        for (T object : objects)
        {
            this.objects.put(object, false);
        }
    }

    public Pool(Supplier<T> supplier)
    {
        this(supplier, (T) null);
    }

    public T requestIfAvailable()
    {
        for (var entry : objects.entrySet())
        {
            if (!entry.getValue())
            {
                objects.put(entry.getKey(), true);
                return entry.getKey();
            }
        }

        return null;
    }

    public T request()
    {
        var reqObject = requestIfAvailable();

        if (reqObject != null) return reqObject;


        if (GameAdapter.DEBUG)
        {
            System.out.printf("[DEBUG] Pool %s is empty, creating new object\n", this);
        }

        final var newObject = supplier.get();
        objects.put(newObject, true);
        return newObject;
    }

    public void release(T object)
    {
        objects.put(object, false);
    }
}
