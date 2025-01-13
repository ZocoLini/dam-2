package org.lebastudios.fxcomponents.components;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneBuilder
{
    private final Parent root;

    public SceneBuilder(Parent root)
    {
        this.root = root;
    }

    public Scene build()
    {
        return new Scene(root);
    }
}
