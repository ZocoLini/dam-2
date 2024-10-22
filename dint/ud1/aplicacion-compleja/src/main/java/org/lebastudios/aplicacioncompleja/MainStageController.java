package org.lebastudios.aplicacioncompleja;

import controllers.StageController;

import java.net.URL;

public class MainStageController extends StageController<MainStageController>
{
    @Override
    public String getTitle()
    {
        return "Xestión Clínica";
    }

    @Override
    public URL getFXML()
    {
        return MainStageController.class.getResource("main-stage.fxml");
    }
}
