package org.lebastudios.desktopapp.ui;

import javafx.scene.control.Button;
import lombok.Getter;

@Getter
public class IconButton extends Button
{
    private int iconSize = 35;

    private String iconName;

    public IconButton()
    {
        this("");
    }

    public IconButton(String iconName)
    {
        super();

        this.iconName = iconName;

        updateIcon();

        this.setStyle("-fx-padding: 0; -fx-border-width: 0; -fx-background-color: transparent; -fx-border-color: " +
                "transparent;");
    }

    private void updateIcon()
    {
        IconView iconView = new IconView(iconName);

        iconView.setFitWidth(iconSize);
        iconView.setFitHeight(iconSize);

        this.setGraphic(iconView);

        this.setPrefSize(iconSize, iconSize);
        this.setMinSize(iconSize, iconSize);
        this.setMaxSize(iconSize, iconSize);
    }

    public void setIconSize(int iconSize)
    {
        this.iconSize = iconSize;
        updateIcon();
    }

    public void setIconName(String iconName)
    {
        this.iconName = iconName;
        updateIcon();
    }
}
