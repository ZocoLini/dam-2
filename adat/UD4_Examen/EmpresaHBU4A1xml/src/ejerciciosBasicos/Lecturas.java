/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciosBasicos;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utiles.OperacionesHB;

/**
 * @author ofernpast
 */
public class Lecturas {
    public static void main(String[] args) {
        OperacionesHB opHb = new OperacionesHB();
        Session s = opHb.getSession();
        Transaction t = s.beginTransaction();

        try {
            //System.out.println(opHb.getEmpregado("87654321A"));
            //opHb.loadDepartamento(5);
            //opHb.showEmpregado(s, "87654321B");
            //opHb.loadDepartamento(8);

            opHb.viewHorasExtra(s, "12345678A");

            opHb.viewProxectos(s, 2);
            opHb.viewProxectos(s, 3);

            t.commit();
        } catch (HibernateException e) {
            t.rollback();
            System.out.println("Error al leer empleado o departamento");
        }

        opHb.liberarRecursos();
        System.exit(0);
    }
}
