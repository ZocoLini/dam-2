/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package empresahb2_feb25;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

import POJOS.Empregado;
import POJOS.Empregadofixo;
import POJOS.Empregadotemporal;
import POJOS.Proxecto;
import org.hibernate.Query;
import org.hibernate.Session;

public class ConsultasHB {
    static Session s;
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        Session s = getSesion();
        //mostrarEmpleados(s);
        //mostrarEmpleadosTipo(s, Empregadofixo.class);
        //mostrarEmpleadosAnho(s, 1900);
        //mostrarNumEmpregadosDepartamentos(s);
        //visualizarProxectosEmpregados(s);
        //s.close();


        // 8
        //visualizarProfesores();
        //visualizarEmpregadosFixosCursos();
        visualizarEmpregadosFixosCursosDirector();



        System.exit(0);
    }

    public static Session getSesion() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public static void abrirSesion() {
        s = getSesion();
    }

    public static void cerrarSesion() {
        s.close();
    }

    /**
     * 1a
     */
    public static void mostrarEmpleados(Session s) {
        String formato = "%-10s%-40s%-20s%-15s%-20s";
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf((formato) + "%n", "NSS", "Nombre Completo", "Departamento", "Tipo empleado", "num de teléfonos");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        Query query = s.createQuery("from Empregado e order by e.apelido1, e.apelido2, e.nome");
        List<Empregado> listaEmpregados = query.list();
        for (Empregado e : listaEmpregados) {
            String tipo = "";
            if (e instanceof Empregadofixo) {
                tipo = "fijo";
            } else if (e instanceof Empregadotemporal) {
                tipo = "temporal";
            }
            System.out.printf(formato + "%n", e.getNss(), e.getNome() + " " + e.getApelido1() + " " + e.getApelido2(),
                    e.getDepartamento().getNomeDepartamento(), tipo, e.getTelefonos().isEmpty() ? "ninguno" : e.getTelefonos().size());
        }
    }

    /**
     * 1b
     */
    public static void mostrarEmpleadosTipo(Session s, Type tipo) {
        String formato = "%-10s%-40s%-20s%-15s%-20s%n";
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf((formato) + "%n", "NSS", "Nombre Completo", "Departamento", "Tipo empleado", "num de teléfonos");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        Query query = s.createQuery("from Empregado e where type(e)=:tipo order by e.apelido1, e.apelido2, e.nome");
        query.setParameter("tipo", tipo);
        List<Empregado> listaEmpregados = query.list();
        for (Empregado e : listaEmpregados) {
            System.out.printf(formato, e.getNss(), e.getNome() + " " + e.getApelido1() + " " + e.getApelido2(),
                    e.getDepartamento().getNomeDepartamento(), tipo == Empregadofixo.class ? "fixo" : "temporal",
                    e.getTelefonos().isEmpty() ? "ninguno" : e.getTelefonos().size());
        }
    }

    /**
     * 1c
     */
    public static void mostrarEmpleadosAnho(Session s, int anhoNacimiento) {
        String formato = "%-10s%-40s%-20s%-20s";
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf((formato) + "%n", "NSS", "Nombre Completo", "Departamento", "fecha nacimiento");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        String hql =
                "select e.nss, concat(e.nome, ' ', e.apelido1, ' ', e.apelido2) as nomeCompleto, e.departamento.nomeDepartamento, e.dataNacemento " +
                        "from Empregado e where year(e.dataNacemento) >= :anhoNacimiento order by e.apelido1, e.apelido2, e.nome";
        Query query = s.createQuery(hql);
        query.setParameter("anhoNacimiento", anhoNacimiento);
        List<Object[]> results = query.list();

        for (Object[] row : results) {
            String nss = (String) row[0];
            String nomeCompleto = (String) row[1];
            String nomeDepartamento = (String) row[2];
            String dataNacemento = sdf.format(row[3]);
            System.out.printf("%-10s%-40s%-20s%-20s%n", nss, nomeCompleto, nomeDepartamento, dataNacemento);
        }
    }

    /** 2a */
    public static void mostrarNumEmpregadosDepartamentos(Session s) {
        String formato = "%-10s%-20s%-20s";
        System.out.println("--------------------------------------------------");
        System.out.printf((formato) + "%n", "Numero", "Departamento", "num de empleados");
        System.out.println("--------------------------------------------------");
        Query query = s.createQuery(
                "select d.numDepartamento, d.nomeDepartamento, count(e) from Empregado e join e.departamento d group by d.numDepartamento, d.nomeDepartamento");
        List<Object[]> results = query.list();
        for (Object[] row : results) {
            Integer numDepartamento = (Integer) row[0];
            String nomeDepartamento = (String) row[1];
            Long numEmpregados = (Long) row[2];
            System.out.printf("%-10s%-20s%-20d%n", numDepartamento, nomeDepartamento, numEmpregados);
        }
    }

    /** 3a */
    public static void visualizarProxectosEmpregados(Session s) {
        String hql = "select e.nss, p.nomeProxecto from Empregado e left join e.empregadoProxectos ep left join ep.proxecto p";
        Query query = s.createQuery(hql);
        List<Object[]> results = query.list();
        for (Object[] row : results) {
            String nss = (String) row[0];
            String nomeProxecto = (String) row[1];
            System.out.println("NSS:" + nss + " " + (nomeProxecto == null ? "no tiene proxectos" : nomeProxecto));
        }
    }

    /** 3b */
    public static void visualizarProxectosConEmpregadosEnVariosProxectos(Session s) {
        String hql = "select p.nomeProxecto, count(p) from Empregado e join e.empregadoProxectos ep join ep.proxecto p group by p.nomeProxecto having count(p) > 1";
        Query query = s.createQuery(hql);
        List<Object[]> results = query.list();
        for (Object[] row : results) {
            String nomeProxecto = (String) row[0];
            Long numEmpregados = (Long) row[1];
            System.out.println("Proxecto:" + nomeProxecto + " " + numEmpregados + " empregados");
        }
    }

    /** 4 */
    public static void visualizarEmpregadosSinTelefono(Session s) {
        String hql = "FROM Empregado e WHERE size(e.telefonos) < 1";
        Query query = s.createQuery(hql);
        List<Empregado> empregados = query.list();
        for (Empregado e : empregados) {
            System.out.println("NSS:" + e.getNss() + " " + e.getNome() + " " + e.getApelido1() + " " + e.getApelido2());
        }
    }

    /** 5 */
    public static void consultaProxectosDepart(int numdept) {
        abrirSesion();
        Query query = s.createQuery("from Proxecto where departamento.numDepartamento = :numdept");
        query.setParameter("numdept", numdept);
        List<Proxecto> proxectos = query.list();
        for (Proxecto p : proxectos) {
            System.out.println(p.getNomeProxecto());
        }

        cerrarSesion();
    }

    public static void consultaProxectosDepart(String nombre) {
        abrirSesion();

        Query query = s.createQuery("from Proxecto where departamento.nomeDepartamento = :nombre");
        query.setParameter("nombre", nombre);
        List<Proxecto> proxectos = query.list();
        for (Proxecto p : proxectos) {
            System.out.println(p.getNomeProxecto());
        }

        cerrarSesion();
    }

    /** 6 */ // TODO: Hacerlo

    /** 7 */ // TODO: Hacerlo

    /** 8a */
    public static void visualizarProfesores() {
        abrirSesion();

        Query query = s.createQuery(
        "select e.empregadofixo.nss, " +
            "concat(e.empregadofixo.nome, ' ', e.empregadofixo.apelido1, ' ', coalesce(e.empregadofixo.apelido2,'')) as nomeCompleto, " +
            "count(e.curso) as numcursos FROM Edicion e GROUP BY e.empregadofixo.nss, e.empregadofixo.nome, e.empregadofixo.apelido1, e.empregadofixo.apelido2"
        );
        List<Object[]> resultados = query.list();
        recorrerResultadosEmpregadosCursos(resultados);

        cerrarSesion();
    }

    /** 8b */
    public static void visualizarEmpregadosFixosCursos() {
        abrirSesion();

        Query query = s.createQuery(
        "select p.nss, concat(p.nome,' ',p.apelido1,' ',coalesce(p.apelido2,'')) as nomeCompleto, " +
            "count(c) FROM Empregadofixo p LEFT JOIN p.edicionprofesor e LEFT JOIN e.curso c GROUP BY p.nss, p.nome, p.apelido1, p.apelido2"
        );
        List<Object[]> resultados = query.list();
        recorrerResultadosEmpregadosCursos(resultados);

        cerrarSesion();
    }

    private static void recorrerResultadosEmpregadosCursos(List<Object[]> resultados) {
        String formato = "%-16s%-40s%-40s%n";
        System.out.println("NSSEmpregadofixo\tNombre\t\tnumero de cursos impartidos");
        for (Object[] row : resultados) {
            String nss = (String) row[0];
            String nomeCompleto = (String) row[1];
            Long numCursos = (Long) row[2];
            System.out.printf(formato, nss, nomeCompleto, numCursos);
        }
    }

    /** 8c */
    public static void visualizarEmpregadosFixosCursosDirector() {
        abrirSesion();

        String formato = "%-16s%-40s%-30s%-30s%n";

        Query query = s.createQuery(
        """
            SELECT p.nss, concat(p.nome,' ',p.apelido1,' ',coalesce(p.apelido2,'')) as nomeCompleto,
            CASE count(c)
                WHEN 0 THEN 'ninguno'
                ELSE cast(count(c) as string)
            END, CASE WHEN p.nss IN (
                SELECT d.director.nss FROM Departamento d
            ) THEN 'Director' ELSE 'No director' END
            FROM Empregadofixo p LEFT JOIN p.edicionprofesor e LEFT JOIN e.curso c GROUP BY p.nss, p.nome, p.apelido1, p.apelido2
        """
        );

        List<Object[]> resultados = query.list();
        System.out.printf(formato, "NSSEmpleadoFixo", "Nombre", "numero de cursos impartidos", "es director departamento");
        for (Object[] fila : resultados) {
            System.out.printf(formato, fila[0], fila[1], fila[2], fila[3]);
        }

        cerrarSesion();
    }

    /** 9 */ // TODO: Hacerlo
}
