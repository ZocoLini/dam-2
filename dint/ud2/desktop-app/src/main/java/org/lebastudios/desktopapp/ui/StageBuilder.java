package org.lebastudios.desktopapp.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.lebastudios.desktopapp.apparience.ImageLoader;

import java.util.function.Consumer;

public class StageBuilder
{
    private final Scene scene;
    private StageStyle stageStyle = StageStyle.DECORATED;
    private String iconName = "the-round-table-logo.png";
    private String title = "";
    private boolean resizeable = false;
    private Modality modality = Modality.NONE;
    private Consumer<Stage> stageConsumer;

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

    public StageBuilder setIconName(String iconName)
    {
        this.iconName = iconName;
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

    public StageBuilder setStageConsumer(Consumer<Stage> consumer)
    {
        this.stageConsumer = consumer;
        return this;
    }
        
    
    public Stage build()
    {
        Stage stage = new Stage(stageStyle);
        stage.setScene(scene);

        stage.setTitle(title);
        stage.setResizable(resizeable);
        stage.initModality(modality);

        stage.getIcons().add(ImageLoader.getIcon(iconName));

        if (stageConsumer != null) stageConsumer.accept(stage);
        
        return stage;
    }
}
