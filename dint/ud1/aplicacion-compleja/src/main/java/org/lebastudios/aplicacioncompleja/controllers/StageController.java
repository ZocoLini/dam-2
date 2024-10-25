package org.lebastudios.aplicacioncompleja.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.util.function.Consumer;

public abstract class StageController<T extends Controller<T>> extends Controller<T>
{
    public final void instantiate(Consumer<T> acceptController)
    {
        StageBuilder stageBuilder = getDefaultStageBuilder();

        acceptController.accept(this.controller);
        customizeStageBuilder(stageBuilder);

        stageBuilder.build().show();
    }

    public final void instantiate()
    {
        instantiate(_ -> {});
    }

    private StageBuilder getDefaultStageBuilder()
    {
        Scene scene = new Scene(getParent());

        return new StageBuilder(scene)
                .setTitle(getTitle());
    }

    protected abstract void customizeStageBuilder(StageBuilder stageBuilder);

    public abstract String getTitle();

    protected final void close()
    {
        ((Stage) getRoot().getScene().getWindow()).close();
    }
}
