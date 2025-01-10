package org.lebastudios.desktopapp.dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import org.lebastudios.desktopapp.Launcher;
import org.lebastudios.desktopapp.controllers.StageController;
import org.lebastudios.desktopapp.locale.LangFileLoader;
import org.lebastudios.desktopapp.ui.StageBuilder;

import java.net.URL;

public class InformationTextDialogController extends StageController<InformationTextDialogController>
{
    private final String informationText;
    @FXML private Label textLabel;
    
    public InformationTextDialogController(String informationText)
    {
        this.informationText = informationText;
    }

    @FXML @Override protected void initialize()
    {
        textLabel.setText(informationText);
    }

    @FXML
    private void accept(ActionEvent actionEvent)
    {
        close();
    }

    @Override
    public Class<?> getBundleClass()
    {
        return Launcher.class;
    }

    @Override
    public URL getFXML()
    {
        return InformationTextDialogController.class.getResource("informationTextDialog.fxml");
    }

    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder)
    {
        stageBuilder.setModality(Modality.APPLICATION_MODAL);
    }

    @Override
    public String getTitle()
    {
        return LangFileLoader.getTranslation("title.infodialog");
    }
}
