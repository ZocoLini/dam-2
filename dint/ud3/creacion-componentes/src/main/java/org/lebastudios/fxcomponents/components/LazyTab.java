package org.lebastudios.fxcomponents.components;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import lombok.Setter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class LazyTab extends Tab
{
    private WeakReference<Node> content;

    private final List<Runnable> onSelected = new ArrayList<>();
    private final List<Runnable> onDeselected = new ArrayList<>();

    @Setter private boolean dropNodeOnDeselect = false;
    
    public LazyTab(String text, INodeGenerator generator)
    {
        super(text);

        this.setOnSelectionChanged(e ->
        {
            if (isSelected())
            {
                if (content == null || content.get() == null)
                {
                    new Thread(() ->
                    {
                        final var node = generator.generate();
                        content = new WeakReference<>(node);

                        Platform.runLater(() -> this.setContent(node));
                    }).start();
                }
                else
                {
                    this.setContent(content.get());
                }

                onSelected();
            }
            else
            {
                this.setContent(null);
                
                if (dropNodeOnDeselect) 
                {
                    content = new WeakReference<>(null);
                }
                
                onDeselected();
            }
        });
    }

    public void onSelected()
    {
        for (var action : onSelected)
        {
            action.run();
        }
    }

    public void onDeselected()
    {
        for (var action : onDeselected)
        {
            action.run();
        }
    }

    public void addOnSelected(Runnable r)
    {
        onSelected.add(r);
    }

    public void addOnDeselected(Runnable r)
    {
        onDeselected.add(r);
    }

    public interface INodeGenerator
    {
        Node generate();
    }
}
