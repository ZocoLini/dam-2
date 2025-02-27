package org.lebastudios.examen.models;

import org.hibernate.Session;
import org.lebastudios.examen.database.Database;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class FotografoDAO
{
    public static Fotografo getByPseudonimo(String pseudonimo, Session session)
    {
        return (Fotografo) session.createQuery("from Fotografo f where f.seudonimo = :pseudonimo")
                .setString("pseudonimo", pseudonimo)
                .uniqueResult();
    }
    
    public static List<FotografoSimplificado> queryFotografoSimplicado(Session session)
    {
        // TODO: El size de la coleccion viene mal
        return (List<FotografoSimplificado>) session.createQuery(
                        "select f.id, f.nombre||' '||f.apellidos as nombreCompleto, f.fotografias.size " +
                                "from Fotografo f " +
                                "order by f.id desc, nombreCompleto")
                .list().stream().map(a ->
                {
                    Object[] row = (Object[]) a;

                    return new FotografoSimplificado(
                            (Integer) row[0],
                            (String) row[1],
                            (Integer) row[2]
                    );
                }).collect(Collectors.toList());
    }
    
    public static void eliminarMaterial(int idFotografo, String numeroSerie, Session session) {
        Fotografo fotografo = (Fotografo) session.get(Fotografo.class, idFotografo);
        
        // TODO: Error de clave duplicada al intentar eliminar??
        if (fotografo == null) 
        {
            System.err.println("No se ha encontrado fotografo con id: " + idFotografo);
            return;
        }
        
        boolean equipoEncontrao = false;
        
        Iterator<Material> it = fotografo.getMateriales().iterator();
        while (it.hasNext())
        {
            Material material = it.next();
            
            if (!material.getNumeroSerie().equals(numeroSerie)) continue;

            System.out.printf("Fotógrafo %d - %s %s\n", idFotografo, fotografo.getNombre(), fotografo.getApellidos());
            System.out.printf(
                    "Equipo a eliminar: %s - %s %s %s\n",
                    numeroSerie, material.getMaterial(), material.getMarca(), material.getModelo()
            );
            it.remove();
            equipoEncontrao = true;
            break;
        }
        
        if (!equipoEncontrao) 
        {
            System.out.printf("El equipo con número de serie %s no existe", numeroSerie);
        }
    }
    
    public static void insert(
            String nombre, String apellidos, String pseudonimo,InformacionContacto infoContacto,
            Licencia licencia, Map<String, String> idiomas, String pseudonimoInfluecer)
    {
        Database.getInstance().connectTransaction(session ->
        {
            Fotografo fotografo = FotografoDAO.getByPseudonimo(pseudonimo, session);
            
            if (fotografo != null) 
            {
                System.out.printf("Ya existe un fotografo con pseudonimo %s\n", pseudonimo);
                session.getTransaction().rollback();
                return;
            }
            
            Fotografo influencer = null;
            
            if (pseudonimoInfluecer != null) 
            {
                influencer = FotografoDAO.getByPseudonimo(pseudonimoInfluecer, session);

                if (influencer == null)
                {
                    System.out.printf("No existe ningun influencer con pseudonimo %s\n", pseudonimoInfluecer);
                    session.getTransaction().rollback();
                    return;
                }
            }
            
            Fotografo newFotografo = new Fotografo();
            newFotografo.setNombre(nombre);
            newFotografo.setApellidos(apellidos);
            newFotografo.setSeudonimo(pseudonimo);
            newFotografo.setInfoContacto(infoContacto);
            newFotografo.setLicencia(licencia);
            newFotografo.setIdiomas(idiomas);
            newFotografo.setInfluenciadoPor(influencer);
            
            licencia.setFotografo(newFotografo);
            session.persist(newFotografo);
            System.out.println("Se ha insertado el nuevo fotografo en la base de datos");
        });
    }
}
