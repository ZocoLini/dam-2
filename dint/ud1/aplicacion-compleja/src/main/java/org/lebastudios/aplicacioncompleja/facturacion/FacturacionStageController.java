package org.lebastudios.aplicacioncompleja.facturacion;

import javafx.stage.Modality;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.net.URL;

public class FacturacionStageController extends StageController
{
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
