package org.lebastudios.aplicacioncompleja.events;

import java.util.ArrayList;
import java.util.List;

public class Event3<A, B, C> extends EventHandler<IEventMethod3<A, B, C>>
{
    public void invoke(A styleSpans, B text, C filExtension)
    {
        // Esta copia permite llamar a todos los listeners aunque se modifique la lista durante una llamada
        List<IEventMethod3<A, B, C>> listaAuxiliar = new ArrayList<>(listeners);

        for (var listener : listaAuxiliar)
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
