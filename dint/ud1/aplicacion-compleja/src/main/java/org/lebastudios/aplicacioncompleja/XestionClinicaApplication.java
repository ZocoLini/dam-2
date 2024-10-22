package org.lebastudios.aplicacioncompleja;

import javafx.application.Application;
import javafx.stage.Stage;

public class XestionClinicaApplication extends Application
{
    @Override
    public void start(Stage stage)
    {
        new MainStageController().instantiate();
    }
}