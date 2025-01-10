package org.lebastudios.desktopapp.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import lombok.Getter;
import org.lebastudios.desktopapp.apparience.UIEffects;

import java.math.BigDecimal;

public class BigDecimalField extends HBox
{
    private final TextField textField;
    private final Label label;
    
    private BigDecimal value;
    @Getter private String labelValue;

    public BigDecimalField(String labelValue)
    {
        super();

        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);
        
        this.labelValue = labelValue;

        label = new Label(labelValue);
        textField = new TextField();
        textField.setMaxWidth(Double.MAX_VALUE);
        textField.setAlignment(Pos.CENTER_RIGHT);
        textField.setPromptText("0.00");

        textField.textProperty().addListener((_, oldValue, newValue) -> onValueChange(oldValue, newValue));
        
        getChildren().addAll(textField, label);
    }
    
    public BigDecimalField()
    {
        this("");
    }

    private void onValueChange(String oldValue, String newValue)
    {
        try
        {
            if (newValue.isEmpty()) return;
            
            value = new BigDecimal(newValue);
        }
        catch (NumberFormatException e)
        {
            UIEffects.shakeNode(textField);
            
            textField.setText(oldValue == null ? "" : oldValue);
        }
    }
    
    public void setValue(BigDecimal value)
    {
        this.value = value;
        textField.setText(value.toString());
    }

    public BigDecimal getValue()
    {
        if (textField.getText().isEmpty()) return null;
        
        return new BigDecimal(textField.getText());
    }
    
    public void setLabelValue(String labelValue)
    {
        this.labelValue = labelValue;
        label.setText(labelValue);
    }
}
