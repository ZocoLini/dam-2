package POJOS;
// Generated 09-feb-2023 8:57:23 by Hibernate Tools 3.6.0

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "EMPREGADO", schema = "dbo", catalog = "EMPRESAHB2_25")
@Inheritance(strategy = InheritanceType.JOINED)
public class Empregado implements java.io.Serializable {

    @Id
    @Column(name = "NSS", length = 15)
    private String nss;

    @Column(name = "Nome", length = 25, nullable = false)
    private String nome;

    @Column(name = "Apelido1", length = 25, nullable = false)
    private String apelido1;

    @Column(name = "Apelido2", length = 25)
    private String apelido2;

    @Column(name = "DataNacemento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNacemento;

    @Column(name = "Sexo", length = 1)
    private Character sexo;
    //Guardamos o enderezo como un componente
    @Embedded
    private Enderezo enderezo;

    //se guardamos el superivisor de un empleao
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NSSSupervisa")
    private Empregado supervisor;

    //mapeo de los supervisados que supervisa un empleado supervisor
    @OneToMany(mappedBy = "supervisor", fetch = FetchType.LAZY)
    private Set<Empregado> supervisados = new HashSet(0);

    //mapear familiares como colección List de componentes
    @ElementCollection
    @CollectionTable(name = "FAMILIAR", joinColumns = @JoinColumn(name = "NSS_empregado"))
    @OrderColumn(name = "Numero")
    private List<Familiar> familiares = new ArrayList();

    //mapear componente los telefonos
    @ElementCollection
    @CollectionTable(name = "TELEFONO", joinColumns = @JoinColumn(name = "NSS"))
    @MapKeyColumn(name = "Telefono")
    @Column(name = "Informacion")
    private Map<String, String> telefonos = new HashMap();

    //mapeo del departamento que pertenece un empleado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NumDepartamentoPertenece")
    private Departamento departamento;

    //mapeo de los empreados proyectos 
    @OneToMany(mappedBy = "empregado", fetch = FetchType.LAZY)
    private Set<EmpregadoProxecto> empregadoProxectos = new HashSet();

//    //las ediciones de cursa un alumno
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "EDICIONCURSO_EMPREGADO",
            joinColumns = @JoinColumn(name = "NSS", referencedColumnName = "NSS"),
            inverseJoinColumns = {
                @JoinColumn(name = "Codigo", referencedColumnName = "Codigo"),
                @JoinColumn(name = "Numero", referencedColumnName = "Numero")
            }
    )
    private Set<Edicion> ediciones = new HashSet<>();
 

    //mapeo del vehiculo del empleado
    @OneToOne
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "NSS")
    private Vehiculo vehiculo;

    public Empregado() {
    }

    public Empregado(String nss) {
        this.nss = nss;
    }

    public Empregado(String nss, String nome, String apelido1) {
        this.nss = nss;
        this.nome = nome;
        this.apelido1 = apelido1;
    }

    public String getNss() {
        return this.nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public Empregado getSupervisor() {
        return this.supervisor;
    }

    public Set<Empregado> getSupervisados() {
        return supervisados;
    }

    public void setSupervisados(Set<Empregado> supervisados) {
        this.supervisados = supervisados;
    }

    public void setSupervisor(Empregado supervisor) {
        this.supervisor = supervisor;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido1() {
        return this.apelido1;
    }

    public void setApelido1(String apelido1) {
        this.apelido1 = apelido1;
    }

    public String getApelido2() {
        return this.apelido2;
    }

    public void setApelido2(String apelido2) {
        this.apelido2 = apelido2;
    }

    public Date getDataNacemento() {
        return this.dataNacemento;
    }

    public void setDataNacemento(Date dataNacemento) {
        this.dataNacemento = dataNacemento;
    }

    public Character getSexo() {
        return this.sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Map<String, String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(Map<String, String> telefonos) {
        this.telefonos = telefonos;
    }

    public Set<EmpregadoProxecto> getEmpregadoProxectos() {
        return empregadoProxectos;
    }

    public void setEmpregadoProxectos(Set<EmpregadoProxecto> empregadoProxectos) {
        this.empregadoProxectos = empregadoProxectos;
    }

    public Set<Edicion> getEdiciones() {
        return ediciones;
    }

    public void setEdiciones(Set<Edicion> ediciones) {
        this.ediciones = ediciones;
    }
    public Vehiculo getVehiculo() {
        return this.vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Enderezo getEnderezo() {
        return enderezo;
    }

    public void setEnderezo(Enderezo enderezo) {
        this.enderezo = enderezo;
    }

    public List<Familiar> getFamiliares() {
        return familiares;
    }

    public void setFamiliares(List<Familiar> familiares) {
        this.familiares = familiares;
    }

    public Object getSupervisa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
