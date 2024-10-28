package org.lebastudios.aplicacioncompleja;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.lebastudios.aplicacioncompleja.controllers.PaneController;

import java.net.URL;

public class GestionVacunacionesController extends PaneController
{
    @FXML private Label chipIdLabel;
    @FXML private ChoiceBox perroChoiceBox;
    @FXML private ChoiceBox propietarioChoiceBox;
    @FXML private TableView vacunasTable;
    @FXML private ChoiceBox vacunaChoiceBox;
    @FXML private DatePicker vacunaDatePicker;
    @FXML private TextArea vacunaObservaciones;

    @Override
    public URL getFXML()
    {
        return GestionVacunacionesController.class.getResource("gestion-vacunaciones.fxml");
    }
}
