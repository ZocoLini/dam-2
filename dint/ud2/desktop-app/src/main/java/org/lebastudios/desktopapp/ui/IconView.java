package org.lebastudios.desktopapp.ui;

import javafx.scene.image.ImageView;
import lombok.Getter;
import org.lebastudios.desktopapp.apparience.ImageLoader;

@Getter
public class IconView extends ImageView
{
    private static final int ICON_SIZE = 32;

    private String iconName;
    private int iconSize = ICON_SIZE;

    public IconView(String iconName)
    {
        super(ImageLoader.getIcon(iconName));
        setSize();
        
        this.iconName = iconName;
    }

    public IconView()
    {
        this("");
    }

    private void setSize()
    {
        this.setFitHeight(iconSize);
        this.setFitWidth(iconSize);

        this.prefHeight(iconSize);
        this.prefWidth(iconSize);

        this.maxHeight(iconSize);
        this.maxWidth(iconSize);

        this.minHeight(iconSize);
        this.minWidth(iconSize);

        this.setPreserveRatio(true);
    }

    public void setIconName(String iconName)
    {
        this.iconName = iconName;
        this.setImage(ImageLoader.getIcon(iconName));
    }

    public void setIconSize(int iconSize)
    {
        this.iconSize = iconSize;
        this.setSize();
    }
}
