/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciosBasicos;

import org.hibernate.Session;
import org.hibernate.Transaction;
import utiles.OperacionesHB;

/**
 *
 * @author ofernpast
 */
public class Borrados {
    public static void main(String[] args) {
        OperacionesHB opHb = new OperacionesHB();
        Session s = opHb.getSession();
        Transaction t = s.beginTransaction();

        try {
            // Borrado de un empleado
            opHb.removeEmpregado(s, "12345678B");
            // Borrado de un departamento
            //opHb.removeDepartamento(2);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            System.out.println("Error al borrar empleado o departamento");
        }

        opHb.liberarRecursos();
        System.exit(0);
    }
}
