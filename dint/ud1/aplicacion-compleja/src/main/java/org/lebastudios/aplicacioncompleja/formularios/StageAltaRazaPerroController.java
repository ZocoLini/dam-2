package org.lebastudios.aplicacioncompleja.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import org.lebastudios.aplicacioncompleja.controllers.PaneController;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.net.URL;

public class StageAltaRazaPerroController extends StageController
{
    @FXML private HBox opcionPaneContainer;
    @FXML private ChoiceBox<Modo> opcionAltaChoiceBox;

    public enum Modo
    {
        RAZAS,
        PERROS
    }
    
    public void initialize()
    {
        opcionAltaChoiceBox.getItems().clear();
        opcionAltaChoiceBox.getItems().addAll(Modo.values());
        
        opcionAltaChoiceBox.selectionModelProperty().subscribe(() -> {
            loadSelectedPane();
            System.out.println(123);
        });
        
        opcionAltaChoiceBox.getSelectionModel().select(Modo.RAZAS);
    }
    
    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder)
    {
        stageBuilder.setModality(Modality.APPLICATION_MODAL);
    }

    @Override
    public String getTitle()
    {
        return "Realizar alta";
    }

    @Override
    public URL getFXML()
    {
        return StageAltaRazaPerroController.class.getResource("stage-alta-raza-perro.fxml");
    }
    
    private void loadSelectedPane()
    {
        PaneController<?> opcion = switch (opcionAltaChoiceBox.getSelectionModel().getSelectedItem()) 
        {
            case RAZAS -> new FormularioNuevaRazaController();
            case PERROS -> new FormularioNuevoPerroController();
        };
        
        opcionPaneContainer.getChildren().clear();
        opcionPaneContainer.getChildren().add(opcion.getRoot());
    }
}
