package org.lebastudios.aplicacioncompleja.events;

import java.util.ArrayList;
import java.util.List;

public class Event5<A, B, C, D, E> extends EventHandler<IEventMethod5<A, B, C, D, E>>
{
    public void invoke(A a, B b, C c, D d, E e)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<IEventMethod5<A, B, C, D, E>> listaAuxiliar = new ArrayList<>(listeners);

        for (var listener : listaAuxiliar)
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
