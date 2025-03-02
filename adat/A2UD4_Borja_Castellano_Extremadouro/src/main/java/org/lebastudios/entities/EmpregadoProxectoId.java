package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class EmpregadoProxectoId implements java.io.Serializable
{
    private String nssempregado;
    private int numProxecto;

    public int hashCode()
    {
        int result = 17;

        result = 37 * result + (getNssempregado() == null ? 0 : this.getNssempregado().hashCode());
        result = 37 * result + this.getNumProxecto();
        return result;
    }
}