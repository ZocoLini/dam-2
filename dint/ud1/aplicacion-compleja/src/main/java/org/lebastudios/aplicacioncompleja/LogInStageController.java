package org.lebastudios.aplicacioncompleja;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.net.URL;

public class LogInStageController extends StageController
{
    @FXML private TextField textLabelPassword;
    @FXML private TextField textLabelUsuario;
    @FXML private TextField textLabelIPServidor;
    @FXML private TextField textLabelPuertoServidor;

    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder) {}

    @Override
    public String getTitle()
    {
        return "Validación de Usuario";
    }

    @Override
    public URL getFXML()
    {
        return LogInStageController.class.getResource("login-stage.fxml");
    }

    @FXML
    private void botonConectar(ActionEvent actionEvent)
    {
        if (!validarConexion()) return;
        
        new MainStageController().instantiate();
    }

    private boolean validarConexion()
    {
        return true;
    }

    @FXML
    private void botonCerrar(ActionEvent actionEvent)
    {
        Platform.exit();
    }
}
