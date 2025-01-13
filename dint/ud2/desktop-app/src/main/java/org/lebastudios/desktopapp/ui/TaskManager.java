package org.lebastudios.desktopapp.ui;

import javafx.collections.ListChangeListener;
import lombok.Getter;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.TaskProgressView;
import org.lebastudios.desktopapp.AppTask;
import org.lebastudios.desktopapp.locale.LangFileLoader;

public class TaskManager extends IconButton
{
    @Getter private static TaskManager instance;

    private final TaskProgressView<AppTask> taskProgressView;

    public TaskManager()
    {
        super("task.png");
        this.setDisable(true);
        taskProgressView = new TaskProgressView<>();
        taskProgressView.setMaxHeight(150);
        taskProgressView.getTasks().addListener(this::onChanged);
        taskProgressView.setGraphicFactory(task -> new IconView(task.getIconName()));

        this.setOnAction(event -> onClick());

        instance = this;
    }

    private void onChanged(ListChangeListener.Change<? extends AppTask> c)
    {
        this.setDisable(taskProgressView.getTasks().isEmpty());
    }

    private void onClick()
    {
        var pop = new PopOver(taskProgressView);

        pop.setTitle(LangFileLoader.getTranslation("button.tasks"));
        pop.setDetachable(false);

        pop.show(this);
    }

    public void startNewTaskWithProgressBar(AppTask task)
    {
        startNewTaskWithProgressBar(task, true);
    }

    public void startNewTaskWithProgressBar(AppTask task, boolean daemon)
    {
        taskProgressView.getTasks().add(task);

        var thread = new Thread(task);
        thread.setDaemon(daemon);
        thread.start();
    }
}
