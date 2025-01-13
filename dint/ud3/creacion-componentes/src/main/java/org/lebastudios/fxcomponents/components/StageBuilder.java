package org.lebastudios.fxcomponents.components;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageBuilder
{
    private final Scene scene;
    private StageStyle stageStyle = StageStyle.DECORATED;
    private String title = "";
    private boolean resizeable = false;
    private Modality modality = Modality.NONE;

    public StageBuilder(Scene scene)
    {
        this.scene = scene;
    }

    public StageBuilder(Parent root)
    {
        this.scene = new SceneBuilder(root).build();
    }

    public StageBuilder setStageStyle(StageStyle style)
    {
        this.stageStyle = style;
        return this;
    }

    public StageBuilder setResizeable(boolean resizeable)
    {
        this.resizeable = resizeable;
        return this;
    }

    public StageBuilder setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public StageBuilder setModality(Modality modality)
    {
        this.modality = modality;
        return this;
    }

    public Stage build()
    {
        Stage stage = new Stage(stageStyle);
        stage.setScene(scene);

        stage.setTitle(title);
        stage.setResizable(resizeable);
        stage.initModality(modality);

        return stage;
    }
}
