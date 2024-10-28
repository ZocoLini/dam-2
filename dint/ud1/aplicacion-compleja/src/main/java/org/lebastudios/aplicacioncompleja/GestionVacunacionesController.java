package org.lebastudios.aplicacioncompleja;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.lebastudios.aplicacioncompleja.controllers.PaneController;
import org.lebastudios.aplicacioncompleja.database.entities.Perro;
import org.lebastudios.aplicacioncompleja.database.entities.PerroVacuna;
import org.lebastudios.aplicacioncompleja.database.entities.Propietario;
import org.lebastudios.aplicacioncompleja.database.entities.Vacuna;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestionVacunacionesController extends PaneController
{
    @FXML private Label chipIdLabel;
    @FXML private ChoiceBox<Perro> perroChoiceBox;
    @FXML private ChoiceBox<Propietario> propietarioChoiceBox;
    @FXML private TableView<VacunaTableItem> vacunasTable;
    @FXML private ChoiceBox<Vacuna> vacunaChoiceBox;
    @FXML private DatePicker vacunaDatePicker;
    @FXML private TextArea vacunaObservaciones;

    @SuppressWarnings("unchecked")
    public void initialize()
    {
        vacunasTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        
        TableColumn<VacunaTableItem, String> nombreCol = (TableColumn<VacunaTableItem, String>) vacunasTable.getColumns().get(0);
        TableColumn<VacunaTableItem, Integer> totalDosisCol = (TableColumn<VacunaTableItem, Integer>) vacunasTable.getColumns().get(1);
        TableColumn<VacunaTableItem, Integer> dosisAplicadasCol = (TableColumn<VacunaTableItem, Integer>) vacunasTable.getColumns().get(2);
        TableColumn<VacunaTableItem, String> fechaCol = (TableColumn<VacunaTableItem, String>) vacunasTable.getColumns().get(3);
        TableColumn<VacunaTableItem, String> observacionesCol = (TableColumn<VacunaTableItem, String>) vacunasTable.getColumns().get(4);

        nombreCol.setCellValueFactory(vacuna -> vacuna.getValue().vacunaNombre);
        totalDosisCol.setCellValueFactory(vacuna -> vacuna.getValue().numTotalDosis.asObject());
        dosisAplicadasCol.setCellValueFactory(vacuna -> vacuna.getValue().dosisAplicadas.asObject());
        fechaCol.setCellValueFactory(vacuna -> vacuna.getValue().vacunaFecha);
        observacionesCol.setCellValueFactory(vacuna -> vacuna.getValue().vacunaObservaciones);

        vacunasTable.getItems().add(new VacunaTableItem(null));
    }

    @Override
    public URL getFXML()
    {
        return GestionVacunacionesController.class.getResource("gestion-vacunaciones.fxml");
    }

    public static class VacunaTableItem
    {
        private final StringProperty vacunaNombre;
        private final IntegerProperty numTotalDosis;
        private final IntegerProperty dosisAplicadas;
        private final StringProperty vacunaFecha;
        private final StringProperty vacunaObservaciones;

        public VacunaTableItem(PerroVacuna vacuna)
        {
            this.vacunaNombre = new SimpleStringProperty("Prueba");
            this.numTotalDosis = new SimpleIntegerProperty(1);
            this.dosisAplicadas = new SimpleIntegerProperty(1);
            this.vacunaFecha = new SimpleStringProperty("Prueba");
            this.vacunaObservaciones = new SimpleStringProperty("Prueba");
        }
    }
}
