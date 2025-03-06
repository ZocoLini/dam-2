package org.lebastudios.entities;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class EdicionId implements java.io.Serializable
{
    private int codigo;
    private int numero;

    public int hashCode()
    {
        int result = 17;

        result = 37 * result + this.getCodigo();
        result = 37 * result + this.getNumero();
        return result;
    }

    public boolean equals(Object other)
    {
        if ((this == other)) return true;
        if ((other == null)) return false;
        if (!(other instanceof EdicionId castOther)) return false;

        return (this.getCodigo() == castOther.getCodigo())
                && (this.getNumero() == castOther.getNumero());
    }
}