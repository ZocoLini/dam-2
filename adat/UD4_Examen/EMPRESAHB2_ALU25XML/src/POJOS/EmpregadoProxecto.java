package POJOS;

public class EmpregadoProxecto implements java.io.Serializable {
    //definicion de la clave compuesta
    private EmpregadoProxectoId id;
    private Integer horas;
    private Empregado empregado;
    private Proxecto proxecto;

    public EmpregadoProxecto() {}

    public EmpregadoProxectoId getId() {
        return this.id;
    }

    public void setId(EmpregadoProxectoId id) {
        this.id = id;
    }

    public Integer getHoras() {
        return this.horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Empregado getEmpregado() {
        return empregado;
    }

    public void setEmpregado(Empregado empregado) {
        this.empregado = empregado;
    }

    public Proxecto getProxecto() {
        return proxecto;
    }

    public void setProxecto(Proxecto proxecto) {
        this.proxecto = proxecto;
    }
}
