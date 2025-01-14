package org.lebastudios.fxcomponents;

import org.lebastudios.fxcomponents.controllers.StageController;
import org.lebastudios.fxcomponents.components.StageBuilder;

import java.net.URL;

public class MainStageController extends StageController<MainStageController>
{
    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder) 
    {
        stageBuilder.setResizeable(true);
    }

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
