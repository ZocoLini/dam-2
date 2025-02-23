package org.lebastudios.aplicacioncompleja.apparience;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

public final class UIEffects
{
    public static void shakeNode(Node node)
    {
        var timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(node.translateXProperty(), 0)),
                new KeyFrame(Duration.seconds(0.05), new KeyValue(node.translateXProperty(), -5)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(node.translateXProperty(), 0)),
                new KeyFrame(Duration.seconds(0.15), new KeyValue(node.translateXProperty(), 5)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(node.translateXProperty(), 0)),
                new KeyFrame(Duration.seconds(0.25), new KeyValue(node.translateXProperty(), -5)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(node.translateXProperty(), 0))
        );

        timeline.play();
    }
}
