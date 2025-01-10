package org.lebastudios.desktopapp.ui;

import javafx.scene.control.Button;
import lombok.Getter;

@Getter
public class IconTextButton extends Button
{
    private int iconSize = 20;

    private String iconName;

    public IconTextButton()
    {
        this("");
    }

    public IconTextButton(String iconName)
    {
        super();

        this.iconName = iconName;

        updateIcon();
    }

    private void updateIcon()
    {
        if (iconName == null || iconName.isEmpty()) 
        {
            this.setGraphic(null);
            return;
        }
        
        IconView iconView = new IconView(iconName);

        iconView.setFitWidth(iconSize);
        iconView.setFitHeight(iconSize);

        this.setGraphic(iconView);
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

