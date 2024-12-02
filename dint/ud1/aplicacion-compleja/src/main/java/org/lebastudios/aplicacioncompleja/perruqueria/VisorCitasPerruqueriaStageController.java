package org.lebastudios.aplicacioncompleja.perruqueria;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.Modality;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.lebastudios.aplicacioncompleja.apparience.UIEffects;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.database.entities.CitaPerrucaria;
import org.lebastudios.aplicacioncompleja.database.entities.ConsultasSQLPerrucaria;
import org.lebastudios.aplicacioncompleja.database.entities.ListadoPerrucaria;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VisorCitasPerruqueriaStageController extends StageController<VisorCitasPerruqueriaStageController>
{
    @FXML private DatePicker fromDate;
    @FXML private DatePicker toDate;
    @FXML private TableView<CitaPerruqueriaTableItem> citasTableView;

    private LocalDate from;
    private LocalDate to;

    @Override
    protected void initialize()
    {
        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now());

        citasTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        ((TableColumn<CitaPerruqueriaTableItem, String>) citasTableView.getColumns().getFirst())
                .setCellValueFactory(cita -> cita.getValue().propietario);
        ((TableColumn<CitaPerruqueriaTableItem, String>) citasTableView.getColumns().get(1))
                .setCellValueFactory(cita -> cita.getValue().perro);
        ((TableColumn<CitaPerruqueriaTableItem, String>) citasTableView.getColumns().get(2))
                .setCellValueFactory(cita -> cita.getValue().fecha);
        ((TableColumn<CitaPerruqueriaTableItem, String>) citasTableView.getColumns().getLast())
                .setCellValueFactory(cita -> cita.getValue().hora);

        MenuItem menuItem = new MenuItem("Eliminar");

        ContextMenu contextMenu = new ContextMenu(menuItem);
        menuItem.setOnAction(_ -> deleteSelectedRow());

        citasTableView.setContextMenu(
                contextMenu
        );


        populateTableView(LocalDate.now(), LocalDate.now());
    }

    private void deleteSelectedRow()
    {
        var items = List.copyOf(citasTableView.getSelectionModel().getSelectedItems());

        items.forEach(item ->
        {
            ConsultasSQLPerrucaria.eliminarUnhaCitaDadoSeuCodigo(item.cod);
            citasTableView.getItems().remove(item);
        });

        Notifications.create()
                .title("Notificación")
                .text("¡Esto es una notificación no intrusiva!")
                .owner(getStage())
                .action(new Action(_ -> undoEliminations(items)))
                .position(Pos.BOTTOM_RIGHT) // Cambia la posición si lo deseas
                .showInformation(); // Puedes usar showWarning(), showError(), showConfirm(), etc.
    }

    private void undoEliminations(List<CitaPerruqueriaTableItem> itemsRemoved)
    {
        itemsRemoved.forEach(itemRemoved ->
        {
            final var c = new CitaPerrucaria(
                    itemRemoved.codCan,
                    LocalDate.from(DateTimeFormatter.ofPattern("yyy-MM-dd").parse(itemRemoved.fecha.get())),
                    Integer.parseInt(itemRemoved.hora().get().split("\\.")[0])
            );

            ConsultasSQLPerrucaria.inserirCitaPerrucaria(c);
        });

        populateTableView(from, to);
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
        if (fromDate.getValue().isAfter(toDate.getValue()))
        {
            UIEffects.shakeNode(fromDate);
            UIEffects.shakeNode(toDate);
            return;
        }

        populateTableView(fromDate.getValue(), toDate.getValue());
    }

    private void populateTableView(LocalDate from, LocalDate to)
    {
        citasTableView.getItems().clear();

        var dateTimerFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<ListadoPerrucaria> citas = ConsultasSQLPerrucaria.recuperarTodaAsCitasDePerrucariaEntreDuasDatas(
                dateTimerFormatter.format(from),
                dateTimerFormatter.format(to)
        );

        if (citas == null) return;

        this.from = from;
        this.to = to;

        citasTableView.getItems().addAll(citas.stream().map(CitaPerruqueriaTableItem::new).toList());
    }

    private record CitaPerruqueriaTableItem(StringProperty propietario,
                                            StringProperty perro,
                                            StringProperty fecha,
                                            StringProperty hora,
                                            int cod,
                                            String codCan)
    {
        CitaPerruqueriaTableItem(ListadoPerrucaria cita)
        {
            this(
                    new SimpleStringProperty(cita.getPropietario()),
                    new SimpleStringProperty(cita.getCan()),
                    new SimpleStringProperty(cita.getData().toString()),
                    new SimpleStringProperty(cita.getHora()),
                    cita.getCodCita(),
                    cita.getCodCan()
            );
        }
    }
}
