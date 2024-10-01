package com.example.dint.graphiccomponents;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FormularioController
{
    @FXML private ListView<String> profesionListView;
    @FXML private TextField nameField;
    @FXML private TextField dniField;
    @FXML private TextField apelido1Field;
    @FXML private TextField apelido2Field;
    @FXML private ComboBox<String> provinciaSeleccionada;
    @FXML private TextField provincia2Add;
    @FXML private TextField profesionField;
    @FXML private TableView<Traballador> traballadoresDisponiblesTable;
    @FXML private TextArea detallesTrabajadorArea;

    public void initialize()
    {
        traballadoresDisponiblesTable.setItems(Empresa.getInstance().getTraballadors());
    }

    @FXML
    private void eliminarProvincia(ActionEvent actionEvent)
    {
        provinciaSeleccionada.getItems().remove(provinciaSeleccionada.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void addProvincia(ActionEvent actionEvent)
    {
        provinciaSeleccionada.getItems().add(provincia2Add.getText());
    }

    @FXML
    private void eliminarProfesion(ActionEvent actionEvent)
    {
        profesionListView.getItems().remove(profesionListView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void engadirProfesion(ActionEvent actionEvent)
    {
        profesionListView.getItems().add(profesionField.getText());
    }

    @FXML
    private void engadirTraballador(ActionEvent actionEvent)
    {
        Traballador traballador = new Traballador(
                dniField.getText(),
                nameField.getText(),
                apelido1Field.getText(),
                apelido2Field.getText(),
                provinciaSeleccionada.getSelectionModel().getSelectedItem(),
                profesionListView.getSelectionModel().getSelectedItem(),
                detallesTrabajadorArea.getText()
        );

        Empresa.getInstance().getTraballadors().add(traballador);
    }

    @FXML
    private void eliminarTraballador(ActionEvent actionEvent) 
    {
        Empresa.getInstance().getTraballadors().remove(traballadoresDisponiblesTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void pecharAplicacion(ActionEvent actionEvent)
    {
        Platform.exit();
    }
}
