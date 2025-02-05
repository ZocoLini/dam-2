package org.lebastudios.engine.events;

public class Event1<T> extends EventHandler<IEventMethod1<T>>
{
    public void invoke(T t)
    {
        for (var listener : getActiveListeners())
        {
            try
            {
                listener.invoke(t);
            }
            catch (Exception e)
            {
                System.err.println("Error invoking event: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
