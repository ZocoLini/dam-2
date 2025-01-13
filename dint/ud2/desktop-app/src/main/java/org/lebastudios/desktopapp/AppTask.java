package org.lebastudios.desktopapp;

import javafx.concurrent.Task;
import lombok.Getter;

public abstract class AppTask extends Task<Void>
{
    @Getter private final String iconName;

    public AppTask()
    {
        this("task.png");
    }

    public AppTask(String iconName)
    {
        this.iconName = iconName;
    }
}
