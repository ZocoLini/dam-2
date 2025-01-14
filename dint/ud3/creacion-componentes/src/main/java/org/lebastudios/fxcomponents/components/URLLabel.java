package org.lebastudios.fxcomponents.components;

import javafx.scene.Cursor;
import javafx.scene.control.Label;

import java.io.IOException;

public class URLLabel extends Label
{
    public URLLabel()
    {
        this.setStyle("-fx-text-fill: blue; -fx-underline: true;");
        this.setCursor(Cursor.HAND);
        
        this.setOnMouseClicked(e ->
        {
            if (e.isConsumed()) return;
            e.consume();

            try
            {
                ProcessBuilder builder = new ProcessBuilder();
                builder.command("firefox", getText());
                builder.start();
            }
            catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        });
    }
}
