package org.lebastudios.aplicacioncompleja.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.net.URL;

public class InformationDialogController extends StageController<InformationDialogController>
{
    @FXML private Label infoLabel;
    
    private final String text;
    
    public InformationDialogController(String text)
    {
        this.text = text;
    }

    @Override
    protected void initialize()
    {
        infoLabel.setText(text);
    }

    @FXML private void onAcceptAction()
    {
        close();
    }
    
    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder)
    {
        stageBuilder.setModality(Modality.APPLICATION_MODAL);
    }

    @Override
    protected boolean hasControllerDefined()
    {
        return false;
    }

    @Override
    public String getTitle()
    {
        return "Informacion";
    }

    @Override
    public URL getFXML()
    {
        return InformationDialogController.class.getResource("information-dialog.fxml");
    }
}
