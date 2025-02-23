package org.lebastudios.aplicacioncompleja.formularios;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.util.StringConverter;
import org.lebastudios.aplicacioncompleja.apparience.UIEffects;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.database.entities.CitaPerrucaria;
import org.lebastudios.aplicacioncompleja.database.entities.ConsultasSQLPerrucaria;
import org.lebastudios.aplicacioncompleja.database.entities.Perro;
import org.lebastudios.aplicacioncompleja.database.entities.Propietario;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.net.URL;

public class FormularioCitaPerruceriaController extends StageController
{
    @FXML private Button reservarButton;
    @FXML private ChoiceBox<Propietario> propietarioChoiceBox;
    @FXML private ChoiceBox<Perro> perroChoiceBox;
    @FXML private DatePicker fechaDatePicker;
    @FXML private RadioButton hora10;
    @FXML private RadioButton hora11;
    @FXML private RadioButton hora12;
    @FXML private RadioButton hora16;
    @FXML private RadioButton hora17;

    private final ToggleGroup horasGroup = new ToggleGroup();

    @Override
    protected void initialize()
    {
        horasGroup.getToggles().addAll(hora10, hora11, hora12, hora16, hora17);

        fechaDatePicker.setDisable(true);
        
        initPerrosChoiceBox();

        initPropietarioChoiceBox();

        horasGroup.getToggles().forEach(toggle -> ((RadioButton) toggle).setDisable(true));

        reservarButton.setDisable(true);

        horasGroup.selectedToggleProperty().addListener(
                (_, _, newValue) -> reservarButton.setDisable(newValue == null)
        );

        
        fechaDatePicker.valueProperty().addListener((_, _, newValue) ->
        {
            horasGroup.getToggles().forEach(toggle ->
            {
                final var radioButton = (RadioButton) toggle;

                radioButton.setDisable(false);
            });

            if (newValue != null)
            {
                ConsultasSQLPerrucaria.recuperarTodaAsCitasDunhaData(newValue).forEach(cita ->
                {
                    if (cita.getHora() == 10) hora10.setDisable(true);
                    else if (cita.getHora() == 11) hora11.setDisable(true);
                    else if (cita.getHora() == 12) hora12.setDisable(true);
                    else if (cita.getHora() == 16) hora16.setDisable(true);
                    else if (cita.getHora() == 17) hora17.setDisable(true);
                });
            }
            

            horasGroup.getToggles().forEach(toggle ->
            {
                final var radioButton = (RadioButton) toggle;

                radioButton.setStyle(radioButton.isDisable() ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
            });
        });
    }

    @Override
    public URL getFXML()
    {
        return FormularioCitaPerruceriaController.class.getResource("formulario-cita-perruqueria.fxml");
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
                fechaDatePicker.setDisable(newValue == null)
        );
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

    @FXML
    private void reservarButtonAction(ActionEvent actionEvent)
    {
        final var perro = perroChoiceBox.getSelectionModel().getSelectedItem();

        if (perro == null)
        {
            UIEffects.shakeNode(perroChoiceBox);
            return;
        }

        if (fechaDatePicker.getValue() == null)
        {
            UIEffects.shakeNode(fechaDatePicker);
            return;
        }

        if (horasGroup.getSelectedToggle() == null) 
        {
            horasGroup.getToggles().forEach(toggle -> UIEffects.shakeNode((ToggleButton) toggle));
            return;
        }

        final var citaPerrucaria = getCitaPerrucaria(perro);

        ConsultasSQLPerrucaria.inserirCitaPerrucaria(citaPerrucaria);

        cerrarButtonAction(actionEvent);
    }

    private CitaPerrucaria getCitaPerrucaria(Perro perro)
    {
        int hora = 0;

        ToggleButton toggleButton = (ToggleButton) horasGroup.getSelectedToggle();

        if (toggleButton == hora10) {
            hora = 10;
        } else if (toggleButton == hora11) {
            hora = 11;
        } else if (toggleButton == hora12) {
            hora = 12;       
        } else if (toggleButton == hora16) {
            hora = 16;
        } else if (toggleButton == hora17) {
            hora = 17;
        }

        return new CitaPerrucaria(perro.getChip(), fechaDatePicker.getValue(), hora);
    }

    @FXML
    private void cerrarButtonAction(ActionEvent actionEvent)
    {
        close();
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
        return "Nova cita perruquería";
    }
}
