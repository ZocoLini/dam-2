package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;

public abstract class Controller<T>
{
    @FXML private Node root;
 
    @Getter protected T controller;
    
    public final Node getRoot()
    {
        if (root == null) 
        {
            try
            {
                this.root = getFXMLLoader().load();
            }
            catch (IOException e)
            {
                throw new RuntimeException("Resource " + getFXML() + " not found");
            }

            this.controller = getFXMLLoader().getController();
        }
        
        return this.root;
    }

    public final Parent getParent()
    {
        return (Parent) getRoot();
    }
    
    public abstract URL getFXML();
    
    public FXMLLoader getFXMLLoader()
    {
        return new FXMLLoader(getFXML());
    }
}
