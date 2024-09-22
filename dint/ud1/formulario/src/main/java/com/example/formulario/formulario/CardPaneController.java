package com.example.formulario.formulario;

import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CardPaneController
{
    public Pane centralPane;
    public HBox buttonsPane;
    public HBox checkboxPane;
    public HBox comboPane;

    public void showButtonsPane(ActionEvent actionEvent) {
        centralPane.getChildren().clear();
        centralPane.getChildren().add(buttonsPane);
    }

    public void showCombosPane(ActionEvent actionEvent) {
        centralPane.getChildren().clear();
        centralPane.getChildren().add(comboPane);
    }

    public void showCheckboxes(ActionEvent actionEvent) {
        centralPane.getChildren().clear();
        centralPane.getChildren().add(checkboxPane);
    }
}
