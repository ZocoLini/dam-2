package POJOS;

import java.util.ArrayList;
import java.util.Collection;

public class Proxecto implements java.io.Serializable {
    private int numProxecto;
    private String nomeProxecto;
    private String lugar;
    private Collection<EmpregadoProxecto> empregados = new ArrayList<>();

    public Proxecto() {}

    public int getNumProxecto() {
        return this.numProxecto;
    }

    public void setNumProxecto(int numProxecto) {
        this.numProxecto = numProxecto;
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

    public Collection<EmpregadoProxecto> getEmpregados() {
        return empregados;
    }

    public void setEmpregados(Collection<EmpregadoProxecto> empregados) {
        this.empregados = empregados;
    }
}
