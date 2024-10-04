package com.example.dint.graphiccomponents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Empresa
{
    private static Empresa instance;
    
    public static Empresa getInstance()
    {
        if (instance == null) instance = new Empresa();
        
        return instance;
    }
    
    private final ObservableList<Traballador> traballadors = FXCollections.observableList(new ArrayList<>());
    
    private Empresa() {}
    
    public ObservableList<Traballador> getTraballadors()
    {
        return traballadors;
    }
}
