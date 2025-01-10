package org.lebastudios.desktopapp.dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.lebastudios.desktopapp.Launcher;
import org.lebastudios.desktopapp.controllers.StageController;
import org.lebastudios.desktopapp.locale.LangFileLoader;
import org.lebastudios.desktopapp.ui.StageBuilder;

import java.net.URL;
import java.util.function.Consumer;

public class ConfirmationTextDialogController extends StageController<ConfirmationTextDialogController>
{
    private final String informationText;
    private final Consumer<Boolean> action;
    @FXML private Label textLabel;

    public ConfirmationTextDialogController(String informationText, Consumer<Boolean> action)
    {
        this.informationText = informationText;
        this.action = action;
    }

    @FXML @Override protected void initialize()
    {
        textLabel.setText(informationText);
    }

    @FXML
    private void accept(ActionEvent actionEvent)
    {
        action.accept(true);
        close();
    }

    public void cancel(ActionEvent actionEvent)
    {
        action.accept(false);
        Stage stage = (Stage) textLabel.getScene().getWindow();
        stage.close();
    }

    @Override
    public Class<?> getBundleClass()
    {
        return Launcher.class;
    }

    @Override
    public URL getFXML()
    {
        return ConfirmationTextDialogController.class.getResource("confirmationTextDialog.fxml");
    }

    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder)
    {
        stageBuilder.setModality(Modality.APPLICATION_MODAL);
    }

    @Override
    public String getTitle()
    {
        return LangFileLoader.getTranslation("title.confirmdialog");
    }
}
