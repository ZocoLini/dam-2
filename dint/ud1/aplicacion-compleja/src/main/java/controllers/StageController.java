package controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.function.Consumer;

public abstract class StageController<T> extends Controller<T>
{
    public final void instantiate(Consumer<T> acceptController)
    {
        getStage(acceptController).show();
    }
    
    public final void instantiate()
    {
        instantiate(_ -> {});
    }
    
    public final Stage getStage(Consumer<T> acceptController)
    {
        Stage stage = new Stage();
        stage.setTitle(getTitle());
        stage.setScene(new Scene(getParent()));
        
        acceptController.accept(this.controller);
        
        return stage;
    }
    
    public abstract String getTitle();
}
