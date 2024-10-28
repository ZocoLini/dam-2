package org.lebastudios.aplicacioncompleja.formularios;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import org.lebastudios.aplicacioncompleja.apparience.UIEffects;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.net.URL;

public class FormularioNuevoPropietarioController extends StageController
{
    @FXML private TextField apellido2Field;
    @FXML private TextField apellido1Field;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField telefonoField;
    @FXML private TextField dniField;

    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder)
    {
        stageBuilder.setModality(Modality.APPLICATION_MODAL);
    }

    @Override
    public String getTitle()
    {
        return "Novo propietario";
    }

    @Override
    public URL getFXML()
    {
        return FormularioNuevoPropietarioController.class.getResource("formulario-nuevo-propietario.fxml");
    }

    @FXML
    private void buttonSaveAction(ActionEvent actionEvent)
    {
        if (!validateData()) return;
        
        buttonCloseAction(actionEvent);
    }

    @FXML
    private void buttonCloseAction(ActionEvent actionEvent)
    {
        close();
    }

    private boolean validateData()
    {
        if (nameField.getText().isBlank()) 
        {
            UIEffects.shakeNode(nameField);
            return false;
        }
        
        if (apellido1Field.getText().isBlank()) 
        {
            UIEffects.shakeNode(apellido1Field);
            return false;
        }
        
        if (dniField.getText().isBlank()) 
        {
            UIEffects.shakeNode(dniField);
            return false;
        }
        
        if (telefonoField.getText().isBlank()) 
        {
            UIEffects.shakeNode(telefonoField);
            return false;
        }
        
        return true;
    }
}
