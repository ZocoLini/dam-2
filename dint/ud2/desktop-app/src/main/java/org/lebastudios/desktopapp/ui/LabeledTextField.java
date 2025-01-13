package org.lebastudios.desktopapp.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;

public class LabeledTextField extends HBox
{
    private final Label label = new Label();
    private final TextField textField = new TextField();
    @Getter private String labelText;
    @Getter private float textFieldPrefSize;

    public LabeledTextField()
    {
        this("");
    }

    public LabeledTextField(String labelText)
    {
        this.labelText = labelText;

        this.getChildren().addAll(label, textField);
        HBox.setHgrow(label, Priority.ALWAYS);
        this.setSpacing(5);
        this.setAlignment(Pos.CENTER_LEFT);

        updateNode();
    }

    private void updateNode()
    {
        label.setText(labelText);
    }

    public void setLabelText(String text)
    {
        labelText = text;
        updateNode();
    }
    
    public void setTextFieldPrefSize(float size)
    {
        textFieldPrefSize = size;
        textField.setPrefWidth(size);
    }
    
    public String getText()
    {
        return textField.getText();
    }
}
