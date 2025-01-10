package org.lebastudios.desktopapp;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.lebastudios.desktopapp.controllers.StageController;
import org.lebastudios.desktopapp.ui.StageBuilder;

public class MainStageController extends StageController<MainStageController>
{
    @Override
    public Class<?> getBundleClass()
    {
        return Launcher.class;
    }

    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder)
    {
        stageBuilder.setResizeable(true);
    }

    @Override
    public boolean hasFXMLControllerDefined()
    {
        return true;
    }

    @Override
    public String getTitle()
    {
        return "Desktop App";
    }

    public void showNotification(String message, Action action)
    {
        Notifications.create()
                .text(message)
                .owner(getRoot())
                .action(action)
                .show();
    }
}