package com.example.dint.graphiccomponents;

import com.aeat.valida.Validador;

public record Dni(String value) implements Validable
{
    @Override
    public boolean validate()
    {
        return new Validador().checkNif(value) == Validador.NIF_OK;
    }
}
