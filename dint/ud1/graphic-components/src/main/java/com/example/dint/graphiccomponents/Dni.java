package com.example.dint.graphiccomponents;

import com.aeat.valida.Validador;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;

public record Dni(String value) implements Validable
{
    @Override
    public boolean validate()
    {
        return new Validador().checkNif(value) == Validador.NIF_OK;
    }
}
