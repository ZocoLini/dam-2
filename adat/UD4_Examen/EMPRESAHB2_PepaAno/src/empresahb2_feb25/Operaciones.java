/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empresahb2_feb25;

import POJOS.Empregado;
import org.hibernate.Session;

import java.util.List;

/**
 * @author usuario
 */
public class Operaciones {
    public static void conectarHibernate() {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        if (sesion != null) {
            System.out.println("Conexión realizada con éxito");
            sesion.close();
        } else {
            System.out.println("ERROR");
        }
    }

    public static void visualizarEmpregados(Session s, String tipoEmpregado) {
        List<Empregado> empregados = s.createQuery("from Empregado where tipo = '" + tipoEmpregado + "'").list();
        for (Empregado empregado : empregados) {
            System.out.println(empregado.getNome() + " " + empregado.getApelido1() + " " + empregado.getApelido2());
        }
    }

    public static Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}