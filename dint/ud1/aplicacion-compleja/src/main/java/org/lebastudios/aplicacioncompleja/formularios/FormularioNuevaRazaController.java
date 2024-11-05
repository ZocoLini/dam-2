package org.lebastudios.aplicacioncompleja.formularios;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.lebastudios.aplicacioncompleja.apparience.UIEffects;
import org.lebastudios.aplicacioncompleja.controllers.PaneController;
import org.lebastudios.aplicacioncompleja.database.entities.Raza;
import org.lebastudios.aplicacioncompleja.dialogs.InformationDialogController;
import org.lebastudios.aplicacioncompleja.language.LangFileLoader;

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
        ((Stage) razaField.getScene().getWindow()).close();
    }

    public void buttonSaveAction(ActionEvent actionEvent)
    {
        if (!validateData()) return;
        
        if (!new Raza(razaField.getText()).insert()) 
        {
            new InformationDialogController(LangFileLoader.getTranslation("phrase.inserterror")).instantiate();
            return;
        }

        buttonCloseAction(actionEvent);
    }

    private boolean validateData()
    {
        if (razaField.getText().isBlank()) 
        {
            UIEffects.shakeNode(razaField);
            return false;
        }
        
        return true;
    }
}
