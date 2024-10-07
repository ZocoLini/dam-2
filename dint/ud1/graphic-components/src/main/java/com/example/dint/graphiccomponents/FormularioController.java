package com.example.dint.graphiccomponents;

import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class FormularioController implements Validable
{
    public VBox prueba;
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
        configurarColumnas(traballadoresDisponiblesTable);

        traballadoresDisponiblesTable.setRowFactory(param -> new TableRow<>() {
            @Override
            protected void updateItem(Traballador traballador, boolean empty)
            {
                super.updateItem(traballador, empty);
                if (empty || traballador == null)
                {
                    setOnMouseClicked(null);
                }
                else
                {
                    setOnMouseClicked(event -> {
                        if (event.getClickCount() != 1) return;

                        detallesTrabajadorArea.setText(traballador.generateDetails());
                    });
                }
            }
        });
        
        Empresa.getInstance().getTraballadors().addListener((ListChangeListener<? super Traballador>) c ->
        {
            traballadoresDisponiblesTable.setItems(Empresa.getInstance().getTraballadors());
        });
        
        
    }

    public static void configurarColumnas(TableView<Traballador> table)
    {
        TableColumn<Traballador, String> columnaA = new TableColumn<>("Nome e apelidos");
        columnaA.setCellValueFactory(cellData -> new StringBinding()
        {
            @Override
            protected String computeValue()
            {
                return cellData.getValue().nome() + " " + cellData.getValue().apelido1() + " " + cellData.getValue().apelido2();
            }
        });
        columnaA.setCellFactory(getTableColumnTableCellCallback());

        TableColumn<Traballador, String> columnaB = new TableColumn<>("Provincia");
        columnaB.setCellValueFactory(cellData -> new StringBinding()
        {
            @Override
            protected String computeValue()
            {
                return cellData.getValue().provincia();
            }
        });
        columnaB.setCellFactory(getTableColumnTableCellCallback());

        TableColumn<Traballador, String> columnaC = new TableColumn<>("Profesión");
        columnaC.setCellValueFactory(cellData -> new StringBinding()
        {
            @Override
            protected String computeValue()
            {
                return cellData.getValue().profesion();
            }
        });
        columnaC.setCellFactory(getTableColumnTableCellCallback());

        // Agregar las columnas a la tabla
        table.getColumns().addAll(columnaA, columnaB, columnaC);
    }

    private static Callback<TableColumn<Traballador, String>, TableCell<Traballador, String>> getTableColumnTableCellCallback()
    {
        return _ -> new TableCell<>()
        {
            @Override
            protected void updateItem(String nome, boolean empty)
            {
                super.updateItem(nome, empty);
                if (empty || nome == null)
                {
                    setText(null);
                }
                else
                {
                    setText(nome);
                }
            }
        };
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
                profesionListView.getSelectionModel().getSelectedItem()
        );
        
        if (validate()) Empresa.getInstance().getTraballadors().add(traballador);
    }

    @FXML
    private void eliminarTraballador(ActionEvent actionEvent)
    {
        Empresa.getInstance().getTraballadors()
                .remove(traballadoresDisponiblesTable.getSelectionModel().getSelectedItem());
        detallesTrabajadorArea.setText("");
    }

    @FXML
    private void pecharAplicacion(ActionEvent actionEvent)
    {
        Platform.exit();
    }

    @Override
    public boolean validate()
    {
        String errorStyle = "-fx-border-color: red";
        boolean valid = true;
        
        if (!new Dni(dniField.getText()).validate()) 
        {
            dniField.setStyle(errorStyle);
            valid = false;
        }
        else {
            dniField.setStyle("");
        }
        
        if (nameField.getText().isEmpty()) 
        {
            nameField.setStyle(errorStyle);
            valid = false;
        }else {
            nameField.setStyle("");
        }
        
        if (apelido1Field.getText().isBlank()) 
        {
            apelido1Field.setStyle(errorStyle);
            valid = false;
        } else {
            apelido1Field.setStyle("");
        }
        
        return valid;
    }
}
