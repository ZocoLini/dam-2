package org.lebastudios.aplicacioncompleja;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.lebastudios.aplicacioncompleja.apparience.UIEffects;
import org.lebastudios.aplicacioncompleja.controllers.PaneController;
import org.lebastudios.aplicacioncompleja.database.entities.Perro;
import org.lebastudios.aplicacioncompleja.database.entities.PerroVacuna;
import org.lebastudios.aplicacioncompleja.database.entities.Propietario;
import org.lebastudios.aplicacioncompleja.database.entities.Vacuna;
import org.lebastudios.aplicacioncompleja.dialogs.InformationDialogController;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        initVacinasTableView();

        initPerrosChoiceBox();

        initVacunaChoiceBox();

        initPropietarioChoiceBox();
    }

    private void initPerrosChoiceBox()
    {
        perroChoiceBox.setDisable(true);
        perroChoiceBox.getItems().clear();

        perroChoiceBox.setConverter(new StringConverter<>()
        {
            @Override
            public String toString(Perro object)
            {
                if (object == null) return "";

                return object.getChip() + " - " + object.getNome();
            }

            @Override
            public Perro fromString(String string)
            {
                return null;
            }
        });

        perroChoiceBox.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) ->
        {
            if (newValue == null) return;

            chipIdLabel.setText(newValue.getChip());
            reloadPerroVacunas();
        });
    }

    private void initVacinasTableView()
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

    private void initVacunaChoiceBox()
    {
        vacunaChoiceBox.getItems().clear();
        vacunaChoiceBox.getItems().addAll(Vacuna.selectAll("ORDER BY codVacina"));

        vacunaChoiceBox.setConverter(new StringConverter<>()
        {
            @Override
            public String toString(Vacuna object)
            {
                if (object == null) return "";

                return object.getNomeVacina();
            }

            @Override
            public Vacuna fromString(String string)
            {
                return null;
            }
        });
    }

    private void initPropietarioChoiceBox()
    {
        propietarioChoiceBox.getItems().clear();
        propietarioChoiceBox.getItems().addAll(Propietario.selectAll("ORDER BY dni"));

        propietarioChoiceBox.setConverter(new StringConverter<>()
        {
            @Override
            public String toString(Propietario object)
            {
                if (object == null) return "";

                return object.getDni() + " - " + object.getNome() + " " + object.getAp1() + " " + object.getAp2();
            }

            @Override
            public Propietario fromString(String string)
            {
                return null;
            }
        });

        propietarioChoiceBox.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) ->
        {
            if (newValue == null)
            {
                perroChoiceBox.setDisable(true);
                return;
            }

            perroChoiceBox.setDisable(false);
            perroChoiceBox.getItems().clear();
            perroChoiceBox.getItems().addAll(Perro.selectAll("ORDER BY chip", newValue.getDni()));
        });
    }

    private void reloadPerroVacunas()
    {
        Perro selectedDog = perroChoiceBox.getSelectionModel().getSelectedItem();
        
        vacunasTable.getItems().clear();
        
        if (selectedDog == null) return;
        
        vacunasTable.getItems().addAll(
                VacunaTableItem.convertFromVacuna(PerroVacuna.selectAll(selectedDog.getChip()))
        );
    }
    
    @Override
    public URL getFXML()
    {
        return GestionVacunacionesController.class.getResource("gestion-vacunaciones.fxml");
    }

    public void vacunaButtonAction(ActionEvent actionEvent)
    {
        final var perro = perroChoiceBox.getSelectionModel().getSelectedItem();
        if (perro == null)
        {
            UIEffects.shakeNode(perroChoiceBox);
            return;
        }

        final var vacuna = vacunaChoiceBox.getSelectionModel().getSelectedItem();
        if (vacuna == null)
        {
            UIEffects.shakeNode(vacunaChoiceBox);
            return;
        }

        if (vacunaDatePicker.getValue() == null)
        {
            UIEffects.shakeNode(vacunaDatePicker);
            return;
        }

        final var perroVacuna = PerroVacuna.select(perro.getChip(), vacuna.getCodVacina());

        if (perroVacuna == null)
        {
            if (!new PerroVacuna(perro.getChip(), vacuna.getCodVacina(), vacunaDatePicker.getValue(),
                    vacunaObservaciones.getText(), 1).insert())
            {
                new InformationDialogController("No se ha podido registrar la nueva dosis").instantiate();
                return;
            }
        }
        else
        {
            if (!new PerroVacuna(perroVacuna, vacunaObservaciones.getText(), vacunaDatePicker.getValue())
                    .saveIncrementandoDosis())
            {
                new InformationDialogController("No se ha podido incrementar la dosis.").instantiate();
                return;
            }
        }
        
        reloadPerroVacunas();
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

        public static List<VacunaTableItem> convertFromVacuna(Collection<PerroVacuna> vacunas)
        {
            List<VacunaTableItem> items = new ArrayList<>();

            vacunas.forEach(vacuna ->
            {
                vacuna.loadRelations();
                items.add(new VacunaTableItem(vacuna));
            });

            return items;
        }
    }
}
