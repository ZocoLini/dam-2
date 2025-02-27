package ejercicios;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojos.Familiar;
import pojos.Telefono;
import utiles.OperacionesHBtelefono;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;

public class Modificaciones {
    public static void main(String[] args) {
        OperacionesHBtelefono opHb = new OperacionesHBtelefono();
        Session s = opHb.openSession();
        Transaction t = s.beginTransaction();

        try {
            // SALARIO
            System.out.println(opHb.modificarSalarioEmpleado(s,"12345678A", 2000));

            // TELEFONO
            HashSet<String> telefonos = new HashSet<>();
            telefonos.add("666666666");
//            telefonos.add(new Telefono("666666667"));
            opHb.setTelefonosEmpleado(s, "12345678A", telefonos);
            opHb.removeTelefonoEmpleado(s, "12345678A", new Telefono("666666666"));

            // FAMILIAR
            Familiar f = new Familiar("12345678V", "Cristina", "Pastoriza", "Otero", new Date(1967, 12, 27), "Tia", 'M');
            //opHb.addFamiliar(s, "87654321A", f);
            System.out.println("Familiar insertado correctamente.");

            // AFICION
            //opHb.addAficion(s, "87654321A", "Cine");
            System.out.println("Aficion insertada correctamente");

            // LUGAR
            //opHb.addLugar(s, 1, "Pontevedra");
            System.out.println("Lugar agregado correctamente");

            // HORASEXTRA
            opHb.addHorasExtra(s, "12345678A", Date.valueOf(LocalDate.of(2025, 2, 8)), 4.5);
            System.out.println("Horas extra añadidas correctamente");
            opHb.viewHorasExtra(s, "12345678A");

            // Commit
            t.commit();
        } catch (HibernateException e) {
            System.out.println("Error al modificar: " + e.getMessage());
            t.rollback();
        }

        opHb.liberarRecursos();
        System.exit(0);
    }
}
