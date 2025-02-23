package org.lebastudios.aplicacioncompleja.facturacion;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.net.URL;

public class FacturacionStageController extends StageController
{
    @FXML private HBox dateFilterBox;
    @FXML private HBox facturaIdFilterBox;
    @FXML private HBox clientNameFilterBox;
    @FXML private DatePicker fromDate;
    @FXML private DatePicker untilDate;
    @FXML private TextField facturaId;
    @FXML private TextField clientName;

    String estiloDisable = "-fx-opacity: 0.5;";

    @Override
    protected void initialize()
    {
        dateFilterBox.setStyle(estiloDisable);
        facturaIdFilterBox.setStyle(estiloDisable);
        clientNameFilterBox.setStyle(estiloDisable);
        untilDate.setStyle(estiloDisable);

        fromDate.focusedProperty().addListener((_, _, newValue) ->
        {
            if (newValue)
            {
                dateFilterBox.setStyle("");
            }
            else
            {
                updateStyles();
            }
        });

        untilDate.focusedProperty().addListener((_, _, newValue) ->
        {
            if (newValue)
            {
                untilDate.setStyle("");
            }
            else
            {
                updateStyles();
            }
        });

        facturaId.focusedProperty().addListener((_, _, newValue) ->
        {
            if (newValue)
            {
                facturaIdFilterBox.setStyle("");
            }
            else
            {
                updateStyles();
            }
        });

        clientName.focusedProperty().addListener((_, _, newValue) ->
        {
            if (newValue)
            {
                clientNameFilterBox.setStyle("");
            }
            else
            {
                updateStyles();
            }
        });
    }

    private void updateStyles()
    {
        untilDate.setStyle("");
        clientNameFilterBox.setStyle("");
        facturaIdFilterBox.setStyle("");
        clientNameFilterBox.setStyle("");
        
        clientName.setText(clientName.getText().trim());
        facturaId.setText(facturaId.getText().trim());

        if (!facturaId.getText().isBlank())
        {
            clientNameFilterBox.setStyle(estiloDisable);
            dateFilterBox.setStyle(estiloDisable);
        }
        else
        {
            facturaIdFilterBox.setStyle(estiloDisable);
            
            if (clientName.getText().isBlank())
            {
                clientNameFilterBox.setStyle(estiloDisable);
            }
            
            if (fromDate.getValue() == null) 
            {
                dateFilterBox.setStyle(estiloDisable);
            }
            
            if (untilDate.getValue() == null) 
            {
                untilDate.setStyle(estiloDisable);
            }
        }
    }

    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder)
    {
        stageBuilder.setModality(Modality.APPLICATION_MODAL);
    }

    @Override
    public String getTitle()
    {
        return "Facturación";
    }

    @Override
    public URL getFXML()
    {
        return FacturacionStageController.class.getResource("facturacion-stage.fxml");
    }
}
