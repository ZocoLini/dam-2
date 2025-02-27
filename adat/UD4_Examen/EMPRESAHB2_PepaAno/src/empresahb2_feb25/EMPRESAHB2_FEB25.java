/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empresahb2_feb25;

import org.hibernate.Session;

import java.text.SimpleDateFormat;
/**
 * @author usuario
 */
public class EMPRESAHB2_FEB25 {
    /**
     * @param args the command line arguments
     */
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        Operaciones.conectarHibernate();

        Session s = Operaciones.getSession();
        Operaciones.visualizarEmpregados(s, "Fixo");


        System.exit(0);
    }
}
