package POJOS;
// Generated 09-feb-2023 8:57:23 by Hibernate Tools 3.6.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.SortComparator;

@Entity
@Table(name = "EMPREGADOFIXO", schema = "dbo", catalog = "EMPRESAHB2_25")
@PrimaryKeyJoinColumn(name = "NSS") // Se enlaza con la clave primaria de EMPREGADO
public class Empregadofixo extends Empregado implements java.io.Serializable {

   @Column(name = "Salario")
    private Double salario;

    @Column(name = "DataAlta")
    private Date dataAlta;

    @Column(name = "Categoria", length = 20)
    private String categoria;
    
    // horas extras de un empleado fijo
    @ElementCollection
    @CollectionTable(name = "HORASEXTRAS", joinColumns = @JoinColumn(name = "NSS"))
    @MapKeyColumn(name = "Data")
    @Column(name = "HorasExtras")
    @SortComparator(OrdeData.class)
    private SortedMap<Date, Double> horasextras = new TreeMap();
    
    // las ediciones que imparte un profesor
    @OneToMany(mappedBy = "profesor", fetch = FetchType.LAZY)
    private Set<Edicion> edicionprofesor =  new HashSet<>();
    
     //departamentos que pueden ser director    
    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
    private Set<Departamento> deptodirector =  new HashSet<>();

    public Empregadofixo() {
    }
  public Empregadofixo(String nss) {
        super(nss);
    }

    public Empregadofixo(String nss, String nome, String apelido1) {
        super(nss, nome, apelido1);
    }

    public Empregadofixo(Double salario, Date dataAlta, String categoria, String nss, String nome, String apelido1) {
        super(nss, nome, apelido1);
        this.salario = salario;
        this.dataAlta = dataAlta;
        this.categoria = categoria;
    }



    public Double getSalario() {
        return this.salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Date getDataAlta() {
        return this.dataAlta;
    }

    public void setDataAlta(Date dataAlta) {
        this.dataAlta = dataAlta;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Set<Edicion> getEdicionprofesor() {
        return edicionprofesor;
    }

    public void setEdicionprofesor(Set<Edicion> edicionprofesor) {
        this.edicionprofesor = edicionprofesor;
    }

    public SortedMap<Date, Double> getHorasextras() {
        return horasextras;
    }

    public void setHorasextras(SortedMap<Date, Double> horasextras) {
        this.horasextras = horasextras;
    }

    public Set<Departamento> getDeptodirector() {
        return deptodirector;
    }

    public void setDeptodirector(Set<Departamento> deptodirector) {
        this.deptodirector = deptodirector;
    }

}
