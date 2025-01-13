package org.lebastudios.fxcomponents.events;

import java.util.ArrayList;
import java.util.List;

public class Event2<T, P> extends EventHandler<IEventMethod2<T, P>>
{
    public void invoke(T t, P p)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<IEventMethod2<T, P>> listaAuxiliar = new ArrayList<>(listeners);

        for (IEventMethod2<T, P> listener : listaAuxiliar)
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
