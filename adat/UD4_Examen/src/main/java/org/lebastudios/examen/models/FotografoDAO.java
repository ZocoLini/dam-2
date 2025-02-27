package org.lebastudios.examen.models;

import org.hibernate.HibernateException;
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

    public static boolean existeFotografoPorPseudonimo(String pseudonimo, Session session)
    {
        long count = (Long) session.createQuery("select count(*) from Fotografo f where f.seudonimo = :pseudonimo")
                .setString("pseudonimo", pseudonimo)
                .list().get(0);
        
        return count != 0;
    }
    
    public static List<FotografoSimplificado> queryFotografoSimplicado(Session session)
    {
        return (List<FotografoSimplificado>) session.createQuery(
                        "select new org.lebastudios.examen.models.FotografoSimplificado(" +
                                "f.id, (f.nombre||' '||f.apellidos) as nombre, f.fotografias.size as numFotografias" +
                                ") " +
                                "from Fotografo f " +
                                "order by numFotografias, nombre")
                .list();
    }

    public static void eliminarMaterial(int idFotografo, String numeroSerie, Session session)
    {
        Fotografo fotografo = (Fotografo) session.get(Fotografo.class, idFotografo);

        if (fotografo == null)
        {
            System.err.println("No se ha encontrado fotografo con id: " + idFotografo);
            return;
        }

        // Usamos un iterador para recorrer los materiales del fotografo. Si encontramos un material con el numero de 
        // de serie dado, lo eliminamos usando it.remove() y dejamos de recorrer la lista
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
            System.out.printf("El equipo con número de serie %s no existe\n", numeroSerie);
        }
    }

    public static void insert(
            String nombre, String apellidos, String pseudonimo, InformacionContacto infoContacto,
            Licencia licencia, Map<String, String> idiomas, String pseudonimoInfluecer)
    {
        Session session = Database.getInstance().getSession();

        // Comporbamos que no exista ya un fotografo con ese pseudonimo
        if (FotografoDAO.existeFotografoPorPseudonimo(pseudonimo, session))
        {
            System.out.printf("Ya existe un fotografo con pseudonimo %s\n", pseudonimo);
            return;
        }

        Fotografo influencer = null;

        if (pseudonimoInfluecer != null)
        {
            influencer = FotografoDAO.getByPseudonimo(pseudonimoInfluecer, session);

            if (influencer == null)
            {
                System.out.printf("No existe ningun influencer con pseudonimo %s\n", pseudonimoInfluecer);
                return;
            }
        }

        try
        {
            session.getTransaction().begin();

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

            session.getTransaction().commit();
            
            System.out.println("Se ha insertado el nuevo fotografo en la base de datos");
        }
        catch (HibernateException exception)
        {
            System.err.println("Se ha hecho rollback de la transaccion");
            session.getTransaction().rollback();
        }
    }
}
