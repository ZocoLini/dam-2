package org.lebastudios.aplicacioncompleja.formularios;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.lebastudios.aplicacioncompleja.apparience.UIEffects;
import org.lebastudios.aplicacioncompleja.controllers.PaneController;
import org.lebastudios.aplicacioncompleja.database.entities.Perro;
import org.lebastudios.aplicacioncompleja.database.entities.Propietario;
import org.lebastudios.aplicacioncompleja.database.entities.Raza;
import org.lebastudios.aplicacioncompleja.dialogs.InformationDialogController;
import org.lebastudios.aplicacioncompleja.language.LangFileLoader;

import java.net.URL;

public class FormularioNuevoPerroController extends PaneController
{
    @FXML private TextField nameField;
    @FXML private TextField chipField;
    @FXML private ListView<Raza> razasList;
    @FXML private ListView<Propietario> propietariosList;

    @Override
    public URL getFXML()
    {
        return FormularioNuevoPerroController.class.getResource("formulario-nuevo-perro.fxml");
    }

    @Override
    protected void initialize()
    {
        razasList.setCellFactory(_ -> new ListCell<>()
        {
            @Override
            protected void updateItem(Raza item, boolean empty)
            {
                super.updateItem(item, empty);

                if (item == null || empty)
                {
                    setText(null);
                    setGraphic(null);
                }
                else
                {
                    setText(item.getDescripcion());
                }
            }
        });

        razasList.getItems().clear();
        razasList.getItems().addAll(Raza.selectAll("ORDER BY codRaza"));

        propietariosList.setCellFactory(_ -> new ListCell<>()
        {
            @Override
            protected void updateItem(Propietario item, boolean empty)
            {
                super.updateItem(item, empty);

                if (item == null || empty)
                {
                    setText(null);
                    setGraphic(null);
                }
                else
                {
                    setText(item.getDni() + " - " + item.getNome() + " " + item.getAp1() + " " + item.getAp2());
                }
            }
        });

        reloadPropietariosList();
    }

    private void reloadPropietariosList()
    {
        propietariosList.getItems().clear();
        propietariosList.getItems().addAll(Propietario.selectAll("ORDER BY dni"));
    }

    public void buttonCloseAction(ActionEvent actionEvent)
    {
        ((Stage) nameField.getScene().getWindow()).close();
    }

    public void buttonSaveAction(ActionEvent actionEvent)
    {
        if (!validateData()) return;

        final var selectedRaza = razasList.getSelectionModel().getSelectedItems().getFirst();
        final var selectedOwner = propietariosList.getSelectionModel().getSelectedItems().getFirst();

        if (!new Perro(chipField.getText(), nameField.getText(),
                selectedRaza.getCodRaza(), selectedOwner.getDni()).insert())
        {
            new InformationDialogController(LangFileLoader.getTranslation("phrase.inserterror")).instantiate();
            return;
        }

        buttonCloseAction(actionEvent);
    }

    private boolean validateData()
    {
        if (nameField.getText().isBlank())
        {
            UIEffects.shakeNode(nameField);
            return false;
        }

        if (chipField.getText().isBlank())
        {
            UIEffects.shakeNode(chipField);
            return false;
        }

        if (razasList.getSelectionModel().getSelectedItems().isEmpty())
        {
            UIEffects.shakeNode(razasList);
            return false;
        }

        if (propietariosList.getSelectionModel().getSelectedItems().isEmpty())
        {
            UIEffects.shakeNode(propietariosList);
            return false;
        }

        return true;
    }

    @FXML
    private void buttonNewPropietarioAction(ActionEvent actionEvent) 
    {
        new FormularioNuevoPropietarioController().instantiate(true);
        
        reloadPropietariosList();
    }
}
