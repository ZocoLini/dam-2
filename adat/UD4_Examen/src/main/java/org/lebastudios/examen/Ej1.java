package org.lebastudios.examen;

import org.lebastudios.examen.database.Database;
import org.lebastudios.examen.models.FotografoDAO;
import org.lebastudios.examen.models.FotografoSimplificado;

import java.util.List;

public class Ej1
{
    public static void main(String[] args)
    {
        Database.getInstance().init();
        
        Database.getInstance().connectQuery(session ->
        {
            List<FotografoSimplificado> fotografos = FotografoDAO.queryFotografoSimplicado(session);
            
            fotografos.forEach(fotografo ->
            {
                System.out.printf("%d\t%-30s\t%d\n", fotografo.getCod(), fotografo.getNombreCompleto(), fotografo.getNumFotografias());
            });
        });
        
        Database.getInstance().close();
    }
}
