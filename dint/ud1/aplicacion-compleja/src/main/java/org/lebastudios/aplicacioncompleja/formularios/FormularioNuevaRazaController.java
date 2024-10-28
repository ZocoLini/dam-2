package org.lebastudios.aplicacioncompleja.formularios;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.lebastudios.aplicacioncompleja.controllers.PaneController;

import java.net.URL;

public class FormularioNuevaRazaController extends PaneController
{
    @FXML private TextField razaField;

    @Override
    public URL getFXML()
    {
        return FormularioNuevaRazaController.class.getResource("formulario-nueva-raza.fxml");
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
