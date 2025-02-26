package empresahb2_feb25;

import POJOS.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConsultasHBpepa {
    static Session sesion;
        static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void iniciarSesion() {
        sesion = HibernateUtil.getSessionFactory().openSession();
    }

    public static void cerrarSesion() {
        sesion.close();
    }


    public static void listarEmpregadosOpcionA() {
        String tipo;

        iniciarSesion();

        Query query = sesion.createQuery("from Empregado "
                + "order by apelido1,apelido2,nome");
        List<Empregado> empregados = query.list();

        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-8s%-25s%-20s%-20s%-20s%n",
                " NSS", "Nombre Completo", "Departamento", "Tipo empleado", "num. de teléfonos");
        System.out.println("-------------------------------------------------------------------------");

        for (Empregado empregado : empregados) {
            if (empregado instanceof Empregadofixo) {
                tipo = "fijo";
            } else {
                tipo = "temporal";
            }
            System.out.printf("%-8s %-30s %-20s %-20s %-20s\n",
                    empregado.getNss(), empregado.getApelido1() + " "
                            + empregado.getApelido2() + ", " + empregado.getNome(),
                    empregado.getDepartamento().getNomeDepartamento(),
                    tipo, !empregado.getTelefonos().isEmpty()
                            ? empregado.getTelefonos().size() : "ninguno");
//        System.out.println( empregado.getNss()+" "+ empregado.getNome()+ empregado.getApelido1();
        }
        cerrarSesion();
    }

    public static void mostrarEmpregados() {
        iniciarSesion();
        Query query = sesion.createQuery(
                "SELECT e.nss, " +
                        "     concat(e.apelido1, ' ', e.apelido2, ', ', e.nome) as nome_completo, " +
                        "     e.departamento.nomeDepartamento, " +
                        "     CASE TYPE(e) " +
                        "         WHEN Empregadofixo THEN 'fijo' " +
                        "         WHEN Empregadotemporal THEN 'temporal' " +
                        "         ELSE 'desconocido' " +
                        "     END as tipo_empregado, " +
                        "     e.telefonos.size " + // Mantenemos el tamaño de los teléfonos
                        "FROM Empregado e " +
                        "ORDER BY e.apelido1, e.apelido2, e.nome"
        );

        List<Object[]> results = query.list();

        // Encabezado con alineación
        for (Object[] campos : results) {
            System.out.printf("%-10s %-30s %-15s %-10s %-5s%n", campos[0], campos[1], campos[2], campos[3], campos[4]);
        }
        System.out.println("-------------------------------------------------------------------------");
        cerrarSesion();
    }

    public static void listarEmpregadosOpcionB() {
        iniciarSesion();
        Query query = sesion.createQuery(
                "SELECT e.nss, e.apelido1, e.apelido2, e.nome, e.departamento.nomeDepartamento, "
                        + "     CASE TYPE(e) "
                        + "         WHEN Empregadofixo THEN 'fijo' "
                        + "         WHEN Empregadotemporal THEN 'temporal' "
                        + "     END, "
                        + "     SIZE(e.telefonos) "
                        + "FROM Empregado e "
                        + "ORDER BY e.apelido1, e.apelido2, e.nome"
        );
        List<Object[]> empregados = query.list();

        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-8s %-25s %-20s %-20s %-20s\n", " NSS", "Nombre Completo",
                "Departamento", "Tipo empleado", "Num. de Teléfonos");
        System.out.println("-------------------------------------------------------------------------");

        for (Object[] empregado : empregados) {
            String nss = (String) empregado[0];
            String apelido1 = (String) empregado[1];
            String apelido2 = (String) empregado[2];
            String nome = (String) empregado[3];
            String nomeDepartamento = (String) empregado[4];
            String tipo = (String) empregado[5];
            int numTelefonos = (Integer) empregado[6];

            System.out.printf("%-8s %-30s %-20s %-20s %-20s\n",
                    nss, apelido1 + " " + apelido2 + ", " + nome,
                    nomeDepartamento, tipo, numTelefonos > 0 ? numTelefonos : "ninguno");
        }

        cerrarSesion();
    }


    public static void listarEmpregados(String tipoEmpleado) {
        String tipo;
        String tipoE = tipoEmpleado.equalsIgnoreCase("fixo") ? "Empregadofixo" : "Empregadotemporal";

        iniciarSesion();
        String consulta = "from Empregado e where e.class=" + tipoE
                + " order by apelido1,apelido2,nome";
        Query query = sesion.createQuery(consulta);
        List<Empregado> empregados = query.list();
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-8s %-25s %-20s %-20s %-20s\n", " NSS", "Nombre Completo",
                "Departamento", "Tipo empleado", "num. de teléfonos");
        System.out.println("-------------------------------------------------------------------------");

        for (Empregado empregado : empregados) {
            if (empregado instanceof Empregadotemporal) {
                tipo = "temporal";
            } else {
                tipo = "fixo";
            }
            System.out.printf("%-8s %-30s %-20s %-20s %-20s\n", empregado.getNss(),
                    empregado.getApelido1() + " " + empregado.getApelido2() + ", " + empregado.getNome(),
                    empregado.getDepartamento().getNomeDepartamento(), tipo, !empregado.getTelefonos().isEmpty() ?
                            empregado.getTelefonos().size() : "ninguno");

//        System.out.println( empregado.getNss()+" "+ empregado.getNome()+ empregado.getApelido1();
        }

        cerrarSesion();
    }

    public static void listarEmpregados2(int anho) {
        iniciarSesion();
        String consulta = "Select nss,apelido1||' '||"
                + "isNull(apelido2,'')||"
                + "'||'||nome,departamento.nomeDepartamento, "
                + "dataNacemento from Empregado "
                + "where year(dataNacemento)>:anho "
                + "order by apelido1,apelido2,nome";
        Query query = sesion.createQuery(consulta);
        query.setInteger("anho", anho);
        List<Object[]> empregados = query.list();
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-8s %-25s %-20s %-20s \n", " NSS", "Nombre Completo", "Departamento",
                "Fecha nacimiento");
        System.out.println("-------------------------------------------------------------------------");

        for (Object[] empregado : empregados) {
            System.out.printf("%-8s %-30s %-20s %-20s \n",
                    empregado[0], empregado[1], empregado[2],
                    sdf.format(empregado[3]));
        }
        cerrarSesion();
    }

    public static void listarDepartamentos() {
        iniciarSesion();

        Query query = sesion.createQuery("SELECT numDepartamento,"
                + " nomeDepartamento,"
                + " size(empregados) FROM Departamento "
                + "GROUP BY numDepartamento, nomeDepartamento ORDER BY 3 DESC");

        System.out.printf("%-15s %-15s %-10s\n", "Numero", "Nombre", "num de empleados");

        for (Iterator it = query.iterate(); it.hasNext(); ) {
            Object[] obj = (Object[]) it.next();
            System.out.printf("%-12s %-20s %-10s", obj[0], obj[1], obj[2]);
            System.out.println("");
        }

        cerrarSesion();
    }

    public static void listarDepartamentos2() {
        iniciarSesion();

        Query query = sesion.createQuery("SELECT "
                + "new map (numDepartamento, nomeDepartamento, "
                + "size(empregados)) FROM Departamento "
                + "GROUP BY numDepartamento, nomeDepartamento "
                + "ORDER BY 3 DESC");

        System.out.printf("%-15s %-15s %-10s\n", "Numero", "Nombre", "num de empleados");

        List<Map> lista = query.list();
        for (Map map : lista) {
            Object[] obj = new Object[map.size()];
            int i = map.size() - 1;

            for (Object o : map.keySet()) {
                obj[i] = map.get(o);
                i--;
            }

            System.out.printf("%-13s %-25s %-10s", obj[0], obj[1], obj[2]);
            System.out.println();
        }

        cerrarSesion();
    }

    public static void listarEmpregadosProxecto() {
        iniciarSesion();

        Query query = sesion.createQuery("from Empregado e "
                + "left join e.empregadoProxectos");

        List<Object[]> empregados = query.list();

        for (Object[] empregado : empregados) {
            if (empregado[1] == null) {
                System.out.println("NSS: "
                        + ((Empregado) empregado[0]).getNss() + " no tiene proxectos.");
            } else {
                System.out.println("NSS: "
                        + ((Empregado) empregado[0]).getNss() + " "
                        + ((EmpregadoProxecto) empregado[1]).getProxecto().getNomeProxecto());
            }
        }

        cerrarSesion();
    }

    public static void listarEmpregadosProxecto2() {
        iniciarSesion();

        Query query = sesion.createQuery("Select e.nss, ep "
                + " from Empregado e left join e.empregadoProxectos ep");

        List<Object[]> empregados = query.list();

        for (Object[] empregado : empregados) {
            if (empregado[1] == null) {
                System.out.println("NSS: " + empregado[0] + " no tiene proxectos.");
            } else {
                System.out.println("NSS: " + empregado[0] + " "
                        + ((EmpregadoProxecto) empregado[1]).getProxecto().getNomeProxecto());
            }
        }

        cerrarSesion();
    }

    public static void listarEmpregadosProxectoMayor10() {
        iniciarSesion();

        Query query = sesion.createQuery("Select e.nss,"
                + "ep.proxecto.nomeProxecto "
                + "from Empregado e inner join "
                + "e.empregadoProxectos ep"
                + " where size(ep)>1");

        List<Object[]> empregados = query.list();

        for (Object[] empregado : empregados) {
            System.out.println("NSS: " + empregado[0] + " " + empregado[1]);
        }

        cerrarSesion();
    }

    public static void listarEmpregadosSenTelefono() {
        iniciarSesion();

        Query query = sesion.createQuery("Select nss, apelido1|| ' ' || isNull(apelido2, ' ')"
                + "||','|| nome from Empregado e "
                + "where size(e.telefonos) =0");

        List<Object[]> empregados = query.list();

        for (Object[] empregado : empregados) {
            System.out.println("NSS: " + empregado[0] + " Nombre:" + empregado[1]);
        }

        cerrarSesion();
    }

    public static void ConsultaProxectosDepart(int numdept) {
        iniciarSesion();

        Departamento dept = (Departamento) sesion.get(Departamento.class, numdept);
        if (dept != null) {
            Collection<Proxecto> proxectos = dept.getProxectos();
            for (Proxecto i : proxectos) {
                System.out.println("numero de proxecto:" + i.getNumProxecto()
                        + " nome:" + i.getNomeProxecto());
            }
        } else {
            System.out.println("Erro.Non existe o departamento " + numdept);
        }

        cerrarSesion();
    }

    public static void ConsultaProxectosDepart(String nombre) {
        iniciarSesion();
        Query consulta = sesion.createQuery("from Departamento d where nomeDepartamento=?");
        consulta.setString(0, nombre);
        Departamento dept = (Departamento) consulta.uniqueResult();
        if (dept != null) {
            Collection<Proxecto> proxectos = dept.getProxectos();
            for (Proxecto i : proxectos) {
                System.out.println("numero de proxecto:" + i.getNumProxecto()
                        + " nome:" + i.getNomeProxecto());
            }
        } else {
            System.out.println("Erro.Non existe o departamento " + nombre);
        }

        cerrarSesion();
    }


    public static void ConsultaProxectosDepart2(String nombre) {
        iniciarSesion();
        Query consulta = sesion.createQuery("Select proxectos "
                + "from Departamento d where nomeDepartamento=?");
        consulta.setString(0, nombre);
        Collection<Proxecto> proxectos = consulta.list();

        for (Proxecto i : proxectos) {
            System.out.println("numero de proxecto:" + i.getNumProxecto()
                    + " nome:" + i.getNomeProxecto());
        }
        cerrarSesion();
    }

    public static void EmpleadosSalario(String nombre) {
        iniciarSesion();
        Query consulta = sesion.createQuery("Select proxectos from Departamento d where nomeDepartamento=?");
        consulta.setString(0, nombre);
        Collection<Proxecto> proxectos = consulta.list();

        for (Proxecto i : proxectos) {
            System.out.println("numero de proxecto:" + i.getNumProxecto()
                    + " nome:" + i.getNomeProxecto());
        }

        cerrarSesion();
    }


    /* TODO: SALARIO ARREGLAR
    public static void inforDepartanumempSalario(Double sueldo) {
        iniciarSesion();

        Query consulta = sesion.createQuery("select d.numDepartamento,"
                + "d.nomeDepartamento, size(e),sum(e.salario) "
                + " from Departamento d inner join d.empregados e "
                + "where c.class=Empregadofixo "
                + "group by d.numDepartamento, d.nomeDepartamento "
                + " having sum(e.salario)>salario"
                + " order by 4 desc");

        consulta.setParameter("salario", sueldo);
        System.out.printf("%5s %14s %28s %15s\n", "Numero", "Nombre", "num de empleados fijos",
                "total sueldo empleados fijos");
        for (Iterator it = consulta.iterate(); it.hasNext(); ) {
            Object[] fila = (Object[]) it.next();
            System.out.printf("%-12s %-30s %d %9s %2f\n", fila[0], fila[1], fila[2], "", fila[3]);
        }
        cerrarSesion();
    }

    public static Double inforSalarioDirectores() {
        iniciarSesion();

        Query consulta = sesion.createQuery("select sum(e.salario) "
                + "from Empregado e "
                + "inner join e.deptodirector");
        Double tsueldodirectores = (Double) consulta.uniqueResult();

        cerrarSesion();
        return tsueldodirectores;
    }

    public static void infoTSalarioDirectoresDepart() {
        iniciarSesion();

        Query consulta = sesion.createQuery("Select e.nss, e.apelido1|| "
                + "'' || isNull(e.apelido2, ' ') || ',' || e.nome, "
                + "isNull(sup.apelido1|| ' '|| isNull(sup.apelido2,' ')"
                + "|| ',' || sup.nome,' '),"
                + "e.salario, dp.nomeDepartamento "
                + " from Empregado e inner join e.deptodirector dp "
                + "left join e.supervisor sup order by 4,1");

        System.out.printf("%5s %14s %32s %24s %19s \n", "NSSDirector", "Nombre", "Jefe", "Salario", "Departamento");
        System.out.println("---");
        for (Iterator it = consulta.iterate(); it.hasNext(); ) {
            Object[] fila = (Object[]) it.next();
            System.out.printf("%-12s %-30s %-30s %8.2f%8s %-30s\n", fila[0], fila[1], fila[2], fila[3], "", fila[4]);
        }
        cerrarSesion();
    }


    public static void Departamentosmayornumempleados() {
        iniciarSesion();

        Query consulta = sesion.createQuery("select d.nomeDepartamento,"
                + " size(d.empregados), "
                + "d.director.apelido1|| ' ' || isNull(d.director.apelido2, ' ') || "
                + " ' || d.director.nome ' "
                + " from Departamento d "
                + "group by d.nomeDepartamento,d.director.apelido1,"
                + "d.director.apelido2,d.director.nome \n "
                + "having count(*) >=ALL "
                + "(Select size(d.empregados) from Departamento d\n "
                + "group by d.numDepartamento )");

        System.out.printf("%5s %14s %15s \n", "Departamento", "NumeroEmpleados", "Director");
        System.out.println("---");
        for (Iterator it = consulta.iterate(); it.hasNext(); ) {
            Object[] fila = (Object[]) it.next();
            System.out.printf("%-12s %-20d %-30s \n", fila[0], fila[1], fila[2]);
        }
        cerrarSesion();
    }

    public static void mostrarDirectores() {
        iniciarSesion();
        Query q = sesion.createQuery("select dir.nss, dir.apelido1,"
                + " isNull(dir.apelido2, ' '),"
                + " dir.nome, isNull(sup.apelido1, ' '),"
                + " isNull(sup.apelido2,"
                + " ' '), isNull(sup.nome, ' '), "
                + " dir.salario, d.nomeDepartamento\n"
                + " from Departamento d inner join d.director dir"
                + " left join dir.supervisor sup "
                + " ORDER BY dir.salario, dir.nss ASC");
        System.out.println("---");
        System.out.printf("%-15s %-30s %-25s %-10s %-20s\n", "NSSDirector", "Nombre", "Jefe", "Salario", "Departamento");
        System.out.println("---");
        for (Iterator it = q.iterate(); it.hasNext(); ) {
            Object[] obj = (Object[]) it.next();
            String nombreCompletedDirector = obj[1] + " " + obj[2] + ", " + obj[3];
            String nombreCompletedSupervisor = obj[4] + " " + obj[5] + ", " + obj[6];
            System.out.printf("%-15s %-30s %-25s %-10s %-20s\n", obj[0], nombreCompletedDirector, nombreCompletedSupervisor, obj[7], obj[8]);
        }
        cerrarSesion();
    }

    public static void mostrarDepartDirectorEdition() {
        iniciarSesion();
        Query q = sesion.createQuery("Select d.numDepartamento,"
                + "d.nomeDepartamento, size(d.lugares)"
                + " from Departamento d "
                + "where d.director.nss in "
                + "(select distinct Empregadofixo.nss from Edicion)"
                + "Group by d.numDepartamento,d.nomeDepartamento");
        System.out.println("---");
        System.out.printf("%-20s %-16s %-28s \n", "NumDepartamento", "Nombre", "numero de lugares");
        System.out.println("---");
        for (Iterator it = q.iterate(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            System.out.printf("%-15s %-30s %-25s \n", obj[0], obj[1], obj[2]);
        }
        cerrarSesion();
    }

    public static void mostrarEmpleadoFijoNumCursos() {
        iniciarSesion();
        Query q = sesion.createQuery("select e.nss,e.apelido1|| "
                + " || isNull(e.apelido2, ' ')|| ', ' || e.nome, "
                + "size(e.edicionprofesor)"
                + " from Empregado e "
                + " where c.class=Empregadofixo \n"
                + "group by e.nss,e.apelido1,apelido2,nome");
        System.out.println("---");
        System.out.printf("%-20s %-16s %-28s \n", "NSSEmpleadoFixo", "Nombre", "numero de cursos impartidos");
        System.out.println("---");
        for (Iterator it = q.iterate(); it.hasNext(); ) {
            Object[] obj = (Object[]) it.next();
            System.out.printf("%-15s %-30s %-25s \n", obj[0], obj[1], obj[2]);
        }
        cerrarSesion();
    }

    public static void mostrarEmpleadoFijoNumCursos2() {
        iniciarSesion();
        Query q = sesion.createQuery("select e.nss,e.apelido1|| "
                + " isNull(e.apelido2,' ')|| ' ' || e.nome, size(ed)"
                + " from Empregado e left join e.edicionprofesor ed "
                + " where e.class=Empregadofixo "
                + "group by e.nss,e.apelido1,e.apelido2,e.nome");
        System.out.println("---");
        System.out.printf("%-20s %-16s %-28s \n", "NSSEmpleadoFixo", "Nombre", "numero de cursos impartidos");
        System.out.println("---");
        for (Iterator it = q.iterate(); it.hasNext(); ) {
            Object[] obj = (Object[]) it.next();
            System.out.printf("%-15s %-30s %-25s \n", obj[0], obj[1], obj[2]);
        }
        cerrarSesion();
    }

    public static void mostrarEmpleadoFijoNumCursos3() {
        iniciarSesion();
        Query q = sesion.createQuery("select e.nss,e.apelido1|| ' ' || isNull(e.apelido2,' ')|| ', ' || e.nome,"
                + " case when size(ed)=0 then 'ninguno' "
                + "else cast (size(ed) as string) end,"
                + " case when e.nss in (select director.nss"
                + " from Departamento) then 'Director' "
                + "else 'No director' end "
                + "from Empregado e left join e.edicionprofesor ed "
                + " where e.class=Empregadofixo "
                + "group by e.nss,e.apelido1,e.apelido2,e.nome");
        System.out.println("---");
        System.out.printf("%-20s %-16s %-28s %-10s \n", "NSSEmpleadoFixo", "Nombre", "numero de cursos impartidos", "Es Director departamento");
        System.out.println("---");
        for (Iterator it = q.iterate(); it.hasNext(); ) {
            Object[] obj = (Object[]) it.next();
            System.out.printf("%-15s %-30s %-25s %-10s \n", obj[0], obj[1], obj[2], obj[3]);
        }
        cerrarSesion();
    }

    public static void empleadosMAXsalario() {
        iniciarSesion();
        Query q = sesion.createQuery("select e.nss,e.apelido1|| ' ' || isNull(e.apelido2,' ')|| ', ' || e.nome,"
                + " e.departamento.nomeDepartamento from Empregado e"
                + " where e.salario=(select Max(salario) from Empregado)");
        System.out.println("---");
        System.out.printf("%-20s %-22s %-28s \n", "NSSEmpleado", "Nombre", "departamento");
        System.out.println("---");
        for (Iterator it = q.iterate(); it.hasNext(); ) {
            Object[] obj = (Object[]) it.next();
            System.out.printf("%-15s %-30s %-25s \n", obj[0], obj[1], obj[2]);
        }
        cerrarSesion();
    }

    public static void empleadosMAXsalarioDirector() {
        iniciarSesion();
        Query q = sesion.createQuery("select e.nss,e.apelido1|| ' ' ||"
                + " isNull(e.apelido2,' ')|| ' ' || e.nome,"
                + " e.departamento.nomeDepartamento,e.salario "
                + "from Empregado e"
                + " where e.salario>(select Max(dir.director.salario) "
                + "from Empregado e inner join e.deptodirector dir)");

        System.out.println("---");
        System.out.printf("%-20s %-22s %-28s %-15s \n", "NSSEmpleado", "Nombre", "Departamento", "Salario");
        System.out.println("---");
        for (Iterator it = q.iterate(); it.hasNext(); ) {
            Object[] obj = (Object[]) it.next();
            System.out.printf("%-15s %-30s %-25s %-15s\n", obj[0], obj[1], obj[2], obj[3]);
        }
        cerrarSesion();
    }

    public static void EmpleadosSalarioLimite(double salario1, double salario2) {
        iniciarSesion();

        Query consulta = sesion.createQuery("Select distinct e "
                + "from Empregado e inner join e.supervisor s "
                + "where e.salario "
                + "between :salario1 and :salario2 "
                + "and e.sexo='F' and e.class=Empregadofixo");
        consulta.setDouble("salario1", salario1);
        consulta.setDouble("salario2", salario2);
        List<Empregado> emp = consulta.list();
        if (emp != null) {
            for (Empregado i : emp) {
                System.out.println("nss:" + i.getNss() + " nome:" + i.getNome() + " " + i.getApelido1() + " " + i.getApelido2() + " salario:" +
                        ((Empregadofixo) i).getSalario()
                        + " numero supervisados:" + i.getSupervisados().size());
            }
        } else {
            System.out.println("no existe ningún empleado con sueldo ");
        }
        cerrarSesion();
    }

    public static void SubirSalarioMaxProyectos(double subida) {
        iniciarSesion();
        Transaction tx = sesion.beginTransaction();

        Query consulta = sesion.createQuery("update Empregado e "
                + "set e.salario=salario+:subida "
                + "where e.nss in "
                + "(select e.nss from Empregado e "
                + "inner join e.empregadoProxectos "
                + "group by e.nss "
                + "having count(*)>=all("
                + "select count(*) from Empregado e "
                + "inner join e.empregadoProxectos "
                + "group by e.nss))");

        consulta.setDouble("subida", subida);
        int r = consulta.executeUpdate();
        System.out.println(" filas " + r);
        tx.commit();
        cerrarSesion();
    }*/
}
