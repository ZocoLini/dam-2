package POJOS;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPREGADO_PROXECTO", schema = "dbo", catalog = "EMPRESAHB2_25")
public class EmpregadoProxecto implements java.io.Serializable {

    //definicion de la clave compuesta
    @EmbeddedId
    private EmpregadoProxectoId id;
    
    //empreado del proyecto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NSSEmpregado", insertable = false, updatable = false)
    private Empregado empregado;
    
    //proxecto que tiene el empleado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NumProxecto", insertable = false, updatable = false)
    private Proxecto proxecto;

    @Column(name = "Horas")
    private Integer horas;

    public EmpregadoProxecto() {
    }

    public EmpregadoProxecto(EmpregadoProxectoId id, Empregado empregado, Proxecto proxecto) {
        this.id = id;
        this.empregado = empregado;
        this.proxecto = proxecto;
    }

    public EmpregadoProxecto(EmpregadoProxectoId id, Empregado empregado, Proxecto proxecto, Integer horas) {
        this.id = id;
        this.empregado = empregado;
        this.proxecto = proxecto;
        this.horas = horas;
    }

    public EmpregadoProxectoId getId() {
        return this.id;
    }

    public void setId(EmpregadoProxectoId id) {
        this.id = id;
    }

    public Empregado getEmpregado() {
        return this.empregado;
    }

    public void setEmpregado(Empregado empregado) {
        this.empregado = empregado;
    }

    public Proxecto getProxecto() {
        return this.proxecto;
    }

    public void setProxecto(Proxecto proxecto) {
        this.proxecto = proxecto;
    }

    public Integer getHoras() {
        return this.horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

}
