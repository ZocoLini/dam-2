/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package empresahb2_feb25;

import java.text.SimpleDateFormat;
import org.hibernate.Session;

public class ConsultasHB {
    static Session sesion = null;
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void iniciarSesion() {
        sesion = HibernateUtil.getSessionFactory().openSession();
    }

    public static void cerrarSesion() {
        sesion.close();
    }

   
    
}
