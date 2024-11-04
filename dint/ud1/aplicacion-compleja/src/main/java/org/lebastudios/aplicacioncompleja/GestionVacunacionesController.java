package org.lebastudios.aplicacioncompleja;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.lebastudios.aplicacioncompleja.controllers.PaneController;
import org.lebastudios.aplicacioncompleja.database.entities.Perro;
import org.lebastudios.aplicacioncompleja.database.entities.PerroVacuna;
import org.lebastudios.aplicacioncompleja.database.entities.Propietario;
import org.lebastudios.aplicacioncompleja.database.entities.Vacuna;

import java.net.URL;

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

        ((TableColumn<VacunaTableItem, String>) vacunasTable.getColumns().getFirst()).setCellValueFactory(
                vacuna -> vacuna.getValue().vacunaNombre);
        ((TableColumn<VacunaTableItem, Integer>) vacunasTable.getColumns().get(1)).setCellValueFactory(
                vacuna -> vacuna.getValue().numTotalDosis.asObject());
        ((TableColumn<VacunaTableItem, Integer>) vacunasTable.getColumns().get(2)).setCellValueFactory(
                vacuna -> vacuna.getValue().dosisAplicadas.asObject());
        ((TableColumn<VacunaTableItem, String>) vacunasTable.getColumns().get(3)).setCellValueFactory(
                vacuna -> vacuna.getValue().vacunaFecha);
        ((TableColumn<VacunaTableItem, String>) vacunasTable.getColumns().getLast()).setCellValueFactory(
                vacuna -> vacuna.getValue().vacunaObservaciones);

    }

    @Override
    public URL getFXML()
    {
        return GestionVacunacionesController.class.getResource("gestion-vacunaciones.fxml");
    }

    public record VacunaTableItem(StringProperty vacunaNombre, IntegerProperty numTotalDosis,
                                  IntegerProperty dosisAplicadas, StringProperty vacunaFecha,
                                  StringProperty vacunaObservaciones)
    {


        public VacunaTableItem(PerroVacuna vacuna)
        {
            this(
                    new SimpleStringProperty(vacuna.getVacuna().getNomeVacina()), 
                    new SimpleIntegerProperty(vacuna.getVacuna().getNumTotalDoses()),
                    new SimpleIntegerProperty(vacuna.getDose()),
                    new SimpleStringProperty(vacuna.getData().toString()),
                    new SimpleStringProperty(vacuna.getObservaciones())
            );
        }
    }
}
