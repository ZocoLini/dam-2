package org.lebastudios.desktopapp.apparience;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.VBox;

import java.util.function.Predicate;

public class DrageableNode
{
    public static void makeDrageable(VBox root, Node item, Runnable onNodeRelease, Predicate<Node> shouldDrag)
    {
        addWithDragging(root, item, onNodeRelease, shouldDrag);
    }

    private static void addWithDragging(final VBox root, final Node node, Runnable onNodeRelease,
            Predicate<Node> shouldDrag)
    {
        node.setOnDragDetected(_ ->
        {
            if (!shouldDrag.test(node)) return;

            node.setStyle("-fx-background-color: #5670ec; -fx-background-radius: 5px;");
            node.startFullDrag();
        });

        node.setOnMouseDragOver(event ->
        {
            if (!shouldDrag.test(node)) return;

            if (node.equals(event.getGestureSource())) return;

            int indexOfDraggingNode = root.getChildren().indexOf(event.getGestureSource());
            int indexOfDropTarget = root.getChildren().indexOf(node);

            rotateNodes(root, indexOfDraggingNode, indexOfDropTarget);
        });

        node.setOnMouseDragReleased(event ->
        {
            if (!shouldDrag.test(node)) return;

            node.setStyle("");
            int indexOfDraggingNode = root.getChildren().indexOf(event.getGestureSource());
            int indexOfDropTarget = root.getChildren().indexOf(node);
            rotateNodes(root, indexOfDraggingNode, indexOfDropTarget);

            onNodeRelease.run();

            event.consume();
        });

        node.setOnMouseDragged(event ->
        {
            if (!shouldDrag.test(node)) return;

            // Dispara el evento de mouse drag over
            node.fireEvent(new MouseDragEvent(MouseDragEvent.MOUSE_DRAG_OVER,
                    event.getX(), event.getY(), event.getScreenX(), event.getScreenY(),
                    MouseButton.PRIMARY, 1, false, false, false, false,
                    false, false, false, false, false, null, node));
        });

        node.setOnMouseReleased(event ->
        {
            if (!shouldDrag.test(node)) return;

            // Dispara el evento de mouse drag released
            node.fireEvent(new MouseDragEvent(MouseDragEvent.MOUSE_DRAG_RELEASED,
                    event.getX(), event.getY(), event.getScreenX(), event.getScreenY(),
                    MouseButton.PRIMARY, 1, false, false, false, false,
                    false, false, false, false, false, null, node));

            event.consume();  // Consumir el evento de liberación
        });
    }

    private static void rotateNodes(final VBox root, final int indexOfDraggingNode,
            final int indexOfDropTarget)
    {
        if (indexOfDraggingNode >= 0 && indexOfDropTarget >= 0)
        {
            final Node node = root.getChildren().remove(indexOfDraggingNode);
            root.getChildren().add(indexOfDropTarget, node);
        }
    }
}
