package org.lebastudios.aplicacioncompleja.perruqueria;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import org.lebastudios.aplicacioncompleja.apparience.UIEffects;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.net.URL;
import java.time.LocalDate;

public class VisorCitasPerruqueriaStageController extends StageController<VisorCitasPerruqueriaStageController>
{
    @FXML private DatePicker fromDate;
    @FXML private DatePicker toDate;
    @FXML private TableView<CitaPerruqueriaTableItem> citasTableView;

    @Override
    protected void initialize()
    {
        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now());

        toDate.valueProperty().addListener((_, oldValue, newValue) ->
        {
            if (newValue.isBefore(fromDate.getValue()))
            {
                toDate.setValue(oldValue);
                UIEffects.shakeNode(toDate);
            }
        });

        ((TableColumn<CitaPerruqueriaTableItem, String>) citasTableView.getColumns().getFirst())
                .setCellValueFactory(cita -> cita.getValue().propietario);
        ((TableColumn<CitaPerruqueriaTableItem, String>) citasTableView.getColumns().get(1))
                .setCellValueFactory(cita -> cita.getValue().perro);
        ((TableColumn<CitaPerruqueriaTableItem, String>) citasTableView.getColumns().get(2))
                .setCellValueFactory(cita -> cita.getValue().fecha);
        ((TableColumn<CitaPerruqueriaTableItem, String>) citasTableView.getColumns().getLast())
                .setCellValueFactory(cita -> cita.getValue().hora);

        populateTableView(LocalDate.now(), LocalDate.now());
    }

    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder)
    {
        stageBuilder.setModality(Modality.APPLICATION_MODAL)
                .setResizeable(true);
    }

    @Override
    public String getTitle()
    {
        return "Visor de citas de perruqueria";
    }

    @Override
    public URL getFXML()
    {
        return VisorCitasPerruqueriaStageController.class.getResource("visor-citas-perruqueria-stage.fxml");
    }

    @FXML
    private void onFindButtonClick(ActionEvent actionEvent)
    {
        populateTableView(fromDate.getValue(), toDate.getValue());
    }

    private void populateTableView(LocalDate from, LocalDate to)
    {
        citasTableView.getItems().clear();
    }

    private record CitaPerruqueriaTableItem(StringProperty propietario,
                                            StringProperty perro,
                                            StringProperty fecha,
                                            StringProperty hora)
    {
        
    }
}
