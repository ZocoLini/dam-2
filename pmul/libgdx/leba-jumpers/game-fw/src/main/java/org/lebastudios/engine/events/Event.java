package org.lebastudios.engine.events;

/**
 * Abstract class for the event system. This class is used to create events that can be invoked without parameters.
 */
public class Event extends EventHandler<IEventMethod>
{
    public void invoke()
    {
        for (var listener : getActiveListeners())
        {
            try
            {
                listener.invoke();
            }
            catch (Exception e)
            {
                System.err.println("Error invoking event: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
