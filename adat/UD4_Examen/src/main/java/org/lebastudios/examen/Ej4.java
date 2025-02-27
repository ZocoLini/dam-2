package org.lebastudios.examen;

import org.lebastudios.examen.database.Database;
import org.lebastudios.examen.models.FotografoDAO;
import org.lebastudios.examen.models.InformacionContacto;
import org.lebastudios.examen.models.Licencia;

import java.util.Map;

public class Ej4
{
    public static void main(String[] args)
    {
        Database.getInstance().init();

        String pseudonimo = "ps4";
        String pseudonimoSinInfluencer = "sinInfluencer4";
        
        // Insercion correcta con influencer
        FotografoDAO.insert(
                "Borja", "Castellano Extremadouro", pseudonimo, 
                new InformacionContacto("Cabadiña algo", "borjacastellano1@gmail.com", "123456789", "123456789"),
                Licencia.defaultLicencia(),
                Map.of("Gallego", "Nativo"),
                "Carlios"
        );

        // Error por usar un pseudonimo existente
        FotografoDAO.insert(
                "Borja", "Castellano Extremadouro", pseudonimo, 
                new InformacionContacto("Cabadiña algo", "borjacastellano1@gmail.com", "123456789", "123456789"),
                Licencia.defaultLicencia(),
                Map.of("Gallego", "Nativo"),
                "Carlios"
        );

        // Insercion correcta sin influencer
        FotografoDAO.insert(
                "Borja", "Castellano Extremadouro", pseudonimoSinInfluencer, 
                new InformacionContacto("Cabadiña algo", "borjacastellano1@gmail.com", "123456789", "123456789"),
                Licencia.defaultLicencia(),
                Map.of("Gallego", "Nativo"),
                null
        );

        // Error por influencer no existente en la base de datos
        FotografoDAO.insert(
                "Borja", "Castellano Extremadouro", "error", 
                new InformacionContacto("Cabadiña algo", "borjacastellano1@gmail.com", "123456789", "123456789"),
                Licencia.defaultLicencia(),
                Map.of("Gallego", "Nativo"),
                "noExisteEsteInfluencer"
        );
        
        Database.getInstance().close();
    }
}
