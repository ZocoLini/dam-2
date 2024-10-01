package com.example.dint.graphiccomponents;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

public class Empresa
{
    private static Empresa instance;
    
    public static Empresa getInstance()
    {
        if (instance == null) instance = new Empresa();
        
        return instance;
    }
    
    private final ObservableList<Traballador> traballadors = new SimpleListProperty<>();
    
    private Empresa() {}
    
    public ObservableList<Traballador> getTraballadors()
    {
        return traballadors;
    }
}
