package POJOS;
// Generated 09-feb-2023 8:57:23 by Hibernate Tools 3.6.0

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTAMENTO", schema = "dbo", catalog = "EMPRESAHB2_25")
public class Departamento implements java.io.Serializable {

    @Id  // Marca o campo como a chave primaria da entidade.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Especifica como se xera o valor para a chave primaria. Neste caso, utilízase unha identidade autoincrementada.
    @Column(name = "NumDepartamento")  // Especifica o nome da columna na táboa da base de datos.
    private int numDepartamento;
    
    @Column(name = "NomeDepartamento", length = 25, nullable = false, unique = true)  // Define as propiedades da columna na táboa da base de datos.
    private String nomeDepartamento;
    
   // el empleado fijo que es director    
    @ManyToOne(fetch = FetchType.LAZY)  // Define unha relación moitos-a-un entre Departamento e Empregadofixo. FetchType.LAZY significa que os datos só se cargan cando se necesitan.
    @JoinColumn(name = "NSSDirector", nullable = false)  // Especifica a columna de unión na táboa da base de datos.
    private Empregadofixo director;
    
    // empregados que pertenecen a un departamento 
    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)  // Define unha relación un-a-moitos entre Departamento e Empregado. 'mappedBy' indica o campo na entidade Empregado que mantén a relación.
    private Set<Empregado> empregados = new HashSet(0);
    
    //mapeo de los lugares con coleccion Idbag
    @ElementCollection  // Define unha colección de elementos que son componentes.
    @CollectionTable(name = "LUGAR", joinColumns = @JoinColumn(name = "Num_departamento"))  // Especifica o nome da táboa e a columna de unión para a colección de elementos.
    @Column(name = "Lugar")  // Especifica o nome da columna para os elementos na táboa.
    @OrderColumn(name = "ID")  // Especifica a columna que se utilizará para manter a orde da lista na base de datos.
    private Collection<String> lugares = new ArrayList();
    
    //mapeo de los proyectosque controla un departamento
    @OneToMany(mappedBy = "departamento", fetch = FetchType.LAZY)  // Define unha relación un-a-moitos entre Departamento e Proxecto.
    private Collection<Proxecto> proxectos = new ArrayList();

    public Departamento() {
    }

    public int getNumDepartamento() {
        return numDepartamento;
    }

    public void setNumDepartamento(int numDepartamento) {
        this.numDepartamento = numDepartamento;
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }

    public Empregadofixo getDirector() {
        return director;
    }

    public void setDirector(Empregadofixo director) {
        this.director = director;
    }

    public Set<Empregado> getEmpregados() {
        return empregados;
    }

    public void setEmpregados(Set<Empregado> empregados) {
        this.empregados = empregados;
    }

    public Collection<String> getLugares() {
        return lugares;
    }

    public void setLugares(Collection<String> lugares) {
        this.lugares = lugares;
    }

    public Collection<Proxecto> getProxectos() {
        return proxectos;
    }

    public void setProxectos(Collection<Proxecto> proxectos) {
        this.proxectos = proxectos;
    }

}
