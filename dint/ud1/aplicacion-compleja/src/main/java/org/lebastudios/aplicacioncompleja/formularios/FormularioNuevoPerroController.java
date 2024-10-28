package org.lebastudios.aplicacioncompleja.formularios;

import javafx.event.ActionEvent;
import org.lebastudios.aplicacioncompleja.controllers.PaneController;

import java.net.URL;

public class FormularioNuevoPerroController extends PaneController
{
    @Override
    public URL getFXML()
    {
        return FormularioNuevoPerroController.class.getResource("formulario-nuevo-perro.fxml");
    }

    public void buttonCloseAction(ActionEvent actionEvent) 
    {
    }

    public void buttonSaveAction(ActionEvent actionEvent) 
    {
        if (!validateData()) return;
        
        buttonCloseAction(actionEvent);
    }
    
    private boolean validateData()
    {
        return true;
    }
}
