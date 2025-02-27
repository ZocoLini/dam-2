package ejerciciosBasicos;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojos.Telefono;
import utiles.OperacionesHBtelefono;

import java.time.LocalDate;
import java.util.HashSet;

public class Modificaciones {
    public static void main(String[] args) {
        OperacionesHBtelefono opHb = new OperacionesHBtelefono();
        Session s = opHb.getSession();
        Transaction t = s.beginTransaction();

        String nssEmpregado = "12345678A";

        HashSet<Telefono> telefonos = new HashSet<>();
        telefonos.add(new Telefono("123456789"));
        telefonos.add(new Telefono("123456788"));
        try {
            //System.out.println(opHb.modificarSalarioEmpleado("12345678B", 2000));

            //opHb.setTelefonosEmpleado(s, nssEmpregado, telefonos);
            //opHb.removeTelefonoEmpleado(s, nssEmpregado, new Telefono("123456788"));

            // opHb.addAficion(s, nssEmpregado, "Futbol");
            //System.out.println("Aficion añadida");

            //opHb.addLugar(s, 1, "Coruña");
            //System.out.println("Lugar añadido");

            // opHb.addHoraExtra(s, nssEmpregado, LocalDate.of(2025, 2, 6), 1.5);
            //System.out.println("Horas extra añadidas correctamente.");

            if (opHb.asignarProxectoToEmpregado(s, nssEmpregado, 1)) {
                System.out.println("Proxecto 1 asignado correctamente ao empregado " + nssEmpregado);
            } else {
                System.err.println("Error al asignar el proxecto.");
            }

            t.commit();
        } catch (HibernateException e) {
            t.rollback();
            System.out.println("Error al modificar empleado");
        }

        opHb.liberarRecursos();
        System.exit(0);
    }
}
