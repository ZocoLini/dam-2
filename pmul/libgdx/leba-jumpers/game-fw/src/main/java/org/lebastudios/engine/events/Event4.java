package org.lebastudios.engine.events;

public class Event4<A, B, C, D> extends EventHandler<IEventMethod4<A, B, C, D>>
{
    public void invoke(A a, B b, C c, D d)
    {
        for (var listener : getActiveListeners())
        {
            try
            {
                listener.invoke(a, b, c, d);
            }
            catch (Exception e)
            {
                System.err.println("Error invoking event: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
