package org.lebastudios.aplicacioncompleja.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import org.lebastudios.aplicacioncompleja.language.LangBundleLoader;

import java.io.IOException;
import java.net.URL;

public abstract class Controller<T extends Controller<T>>
{
    @FXML private Node root;

    private T controller;

    private Thread loadingRoot;

    public final Node getRoot()
    {
        if (loadingRoot != null)
        {
            try
            {
                loadingRoot.join();
            }
            catch (InterruptedException _) {}
        }

        if (root == null) loadFXML();

        return this.root;
    }

    @FXML protected void initialize() {}
    
    public void loadAsync()
    {
        Thread loadingThread = new Thread(() ->
        {
            loadFXML();
            loadingRoot = null;
        });
        loadingThread.start();

        loadingRoot = loadingThread;
    }

    public final void loadFXML()
    {
        if (root != null) return;

        final var fxmlLoader = getFXMLLoader();
        try
        {
            LangBundleLoader.addLangBundle(fxmlLoader);

            if (!hasControllerDefined()) fxmlLoader.setController(this);

            this.root = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this.controller = fxmlLoader.getController();
    }

    protected boolean hasControllerDefined()
    {
        return true;
    }

    public final Parent getParent()
    {
        return (Parent) getRoot();
    }

    public final <U> U getController()
    {
        return controller == null ? (U) this : (U) controller;
    }
    
    public abstract URL getFXML();

    public FXMLLoader getFXMLLoader()
    {
        return new FXMLLoader(getFXML());
    }
}
