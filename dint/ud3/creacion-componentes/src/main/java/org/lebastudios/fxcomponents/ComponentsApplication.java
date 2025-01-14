package org.lebastudios.fxcomponents;

import javafx.application.Application;
import javafx.stage.Stage;

public class ComponentsApplication extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        new MainStageController().instantiate();
    }
}
