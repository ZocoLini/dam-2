package POJOS;
// Generated 09-feb-2023 8:57:23 by Hibernate Tools 3.6.0

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PROXECTO", schema = "dbo", catalog = "EMPRESAHB2_25")
public class Proxecto implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "NumProxecto")
    private int numProxecto;

    @Column(name = "NomeProxecto", unique = true, nullable = false, length = 25)
    private String nomeProxecto;

    @Column(name = "Lugar", nullable = false, length = 25)
    private String lugar;

//Mapeo de del departamento que controla el proyecto
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NumDepartControla", nullable = false)
    private Departamento departamento;

    //los empleados de un proyecto
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proxecto")
    private Collection<EmpregadoProxecto> empregadoProxectos = new ArrayList();

    public Proxecto() {
    }

    public Proxecto(int numProxecto, Departamento departamento, String nomeProxecto, String lugar) {
        this.numProxecto = numProxecto;
        this.departamento = departamento;
        this.nomeProxecto = nomeProxecto;
        this.lugar = lugar;
    }

    public Proxecto(int numProxecto, Departamento departamento, String nomeProxecto) {
        this.numProxecto = numProxecto;
        this.departamento = departamento;
        this.nomeProxecto = nomeProxecto;
    }

    public int getNumProxecto() {
        return this.numProxecto;
    }

    public void setNumProxecto(int numProxecto) {
        this.numProxecto = numProxecto;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getNomeProxecto() {
        return this.nomeProxecto;
    }

    public void setNomeProxecto(String nomeProxecto) {
        this.nomeProxecto = nomeProxecto;
    }

    public String getLugar() {
        return this.lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Collection<EmpregadoProxecto> getEmpregadoProxectos() {
        return empregadoProxectos;
    }

    public void setEmpregadoProxectos(Collection<EmpregadoProxecto> empregadoProxectos) {
        this.empregadoProxectos = empregadoProxectos;
    }

}
