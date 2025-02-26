package utiles;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import pojos.Empregado;
import pojos.Telefono;

import java.util.HashSet;

public class OperacionesHBtelefono extends OperacionesHB {
    public OperacionesHBtelefono() {
        super();
    }

    public boolean setTelefonosEmpleado(Session s, String nss, HashSet<Telefono> telefonos) {
        boolean flagModificacion = false;

        try {
            Empregado e = (Empregado) s.get(Empregado.class, nss);

            if (e != null) {
                e.setTelefonos(telefonos);
                System.out.println(e);
                flagModificacion = true;
            }
        } catch (HibernateException he) {
            System.out.println("Error al establecer los teléfonos de los empleados");
        }

        return flagModificacion;
    }

    public boolean removeTelefonoEmpleado(Session s, String nss, Telefono telefono) {
        boolean flagModificacion = false;

        try {
            Empregado e = (Empregado) s.get(Empregado.class, nss);
            e.getTelefonos().remove(telefono);
            System.out.println(e);
            flagModificacion = true;
        } catch (HibernateException he) {
            System.out.println("Error al eliminar teléfono de empleado");
        }

        return flagModificacion;
    }
}
