package org.lebastudios.desktopapp;

import javafx.application.Application;
import javafx.stage.Stage;

public class DesktopApplication extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        new MainStageController().instantiate();
    }
}
