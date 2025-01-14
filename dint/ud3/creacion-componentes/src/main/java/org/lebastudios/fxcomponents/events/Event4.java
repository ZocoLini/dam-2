package org.lebastudios.fxcomponents.events;

import java.util.ArrayList;
import java.util.List;

public class Event4<A, B, C, D> extends EventHandler<IEventMethod4<A, B, C, D>>
{
    public void invoke(A a, B b, C c, D d)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<IEventMethod4<A, B, C, D>> listaAuxiliar = new ArrayList<>(listeners);

        for (var listener : listaAuxiliar)
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
