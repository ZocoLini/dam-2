/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicios;

import org.hibernate.HibernateException;
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
        Session s = opHb.openSession();
        Transaction t = s.beginTransaction();

        try {
            opHb.removeEmpregado(s,"12345678Z");
            opHb.removeDepartamento(s,2);
            t.commit();
        } catch (HibernateException e) {
            System.out.println("Error al borrar: " + e.getMessage());
            t.rollback();
        }

        opHb.liberarRecursos();
        System.exit(0);
    }
}
