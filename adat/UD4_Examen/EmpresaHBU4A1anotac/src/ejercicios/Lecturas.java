/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicios;

import org.hibernate.Session;
import org.hibernate.Transaction;
import utiles.OperacionesHB;

/**
 * @author ofernpast
 */
public class Lecturas {
    public static void main(String[] args) {
        OperacionesHB opHb = new OperacionesHB();
        Session s = opHb.openSession();
        Transaction t = s.beginTransaction();

        try {
            System.out.println(opHb.getEmpregado(s,"87654321A"));
            opHb.loadDepartamento(s,5);
            System.out.println(opHb.getEmpregado(s,"87654321B"));
            opHb.loadDepartamento(s,8);
            t.commit();
        } catch (Exception e) {
            System.out.println("Error al leer: " + e.getMessage());
            t.rollback();
        }

        opHb.liberarRecursos();
        System.exit(0);
    }
}
