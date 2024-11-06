package org.lebastudios.aplicacioncompleja.formularios;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import org.lebastudios.aplicacioncompleja.apparience.UIEffects;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.database.entities.Perro;
import org.lebastudios.aplicacioncompleja.database.entities.Propietario;
import org.lebastudios.aplicacioncompleja.dialogs.InformationDialogController;
import org.lebastudios.aplicacioncompleja.language.LangFileLoader;
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

        if (!new Propietario(dniField.getText(), nameField.getText(),
                apellido1Field.getText(), apellido2Field.getText(),
                emailField.getText(), telefonoField.getText()).insert())
        {
            new InformationDialogController(LangFileLoader.getTranslation("phrase.inserterror")).instantiate();
            return;
        }
        
        buttonCloseAction(actionEvent);
    }

    @FXML
    private void buttonCloseAction(ActionEvent actionEvent)
    {
        close();
    }

    private boolean validateData()
    {
        return validateField(dniField) && validateField(nameField) && validateField(apellido1Field)
                && validateField(apellido2Field) && validateField(telefonoField) && validateField(emailField) ;
    }

    private boolean validateField(TextField textField)
    {
        if (textField.getText().isBlank()) 
        {
            UIEffects.shakeNode(textField);
            return false;
        }
        
        return true;
    }
}
