package org.lebastudios.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FamiliarId
{
    private String nssEmpregado;
    private int numero;

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;

        FamiliarId that = (FamiliarId) o;
        return numero == that.numero && Objects.equals(nssEmpregado, that.nssEmpregado);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hashCode(nssEmpregado);
        result = 31 * result + numero;
        return result;
    }
}
