/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicios;

import utiles.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author ofernpast
 */
public class CrearSesion {
    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        
        if (s == null) {
            System.out.println("ERROR. Sesión no establecida");
            return;
        }
        
        System.out.println("Sesión establecida");
        s.close();
        System.exit(0);
    }
}
