package org.lebastudios.desktopapp.events;

public class Event2<T, P> extends EventHandler<IEventMethod2<T, P>>
{
    public void invoke(T t, P p)
    {
        for (var listener : getActiveListeners())
        {
            try
            {
                listener.invoke(t, p);
            }
            catch (Exception e)
            {
                System.err.println("Error invoking event: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
