package org.lebastudios.desktopapp.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class TitleBuilder
{
    private String iconName;
    private String text;
    private String weight = "bold";
    private int textSize = 18;
    private int iconSize = 50;
    private int iconGap = 10;

    public TitleBuilder(String text, String iconName)
    {
        this.text = text;
        this.iconName = iconName;
    }

    public TitleBuilder(String text)
    {
        this(text, "");
    }

    public TitleBuilder()
    {
        this("", "");
    }

    public TitleBuilder setText(String text)
    {
        this.text = text;
        return this;
    }

    public TitleBuilder setIconName(String iconName)
    {
        this.iconName = iconName;
        return this;
    }

    public TitleBuilder setWeight(String weight)
    {
        this.weight = weight;
        return this;
    }

    public TitleBuilder setTextSize(int textSize)
    {
        this.textSize = textSize;
        return this;
    }

    public TitleBuilder setIconSize(int iconSize)
    {
        this.iconSize = iconSize;
        return this;
    }

    public TitleBuilder setIconGap(int iconGap)
    {
        this.iconGap = iconGap;
        return this;
    }

    public Node build()
    {
        IconView icon = new IconView(iconName);
        icon.setIconSize(iconSize);

        Label label = new Label(text, icon);
        label.graphicTextGapProperty().set(iconGap);
        label.setStyle(String.format("-fx-font-weight: %s; -fx-font-size: %d", weight, textSize));
        label.setPadding(new Insets(10, 0, 10, 0));
        return label;
    }
}
