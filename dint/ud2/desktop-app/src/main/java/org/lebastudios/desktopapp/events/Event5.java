package org.lebastudios.desktopapp.events;

public class Event5<A, B, C, D, E> extends EventHandler<IEventMethod5<A, B, C, D, E>>
{
    public void invoke(A a, B b, C c, D d, E e)
    {
        for (var listener : getActiveListeners())
        {
            try
            {
                listener.invoke(a, b, c, d, e);
            }
            catch (Exception exc)
            {
                System.err.println("Error invoking event: " + exc.getMessage());
                exc.printStackTrace();
            }
        }
    }
}
