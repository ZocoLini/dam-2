package org.lebastudios.desktopapp.events;

public class Event3<A, B, C> extends EventHandler<IEventMethod3<A, B, C>>
{
    public void invoke(A styleSpans, B text, C filExtension)
    {
        for (var listener : getActiveListeners())
        {
            try
            {
                listener.invoke(styleSpans, text, filExtension);
            }
            catch (Exception e)
            {
                System.err.println("Error invoking event: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
