package org.lebastudios.aplicacioncompleja.propietarios;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.lebastudios.aplicacioncompleja.apparience.UIEffects;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.database.entities.Propietario;
import org.lebastudios.aplicacioncompleja.database.entities.PropietarioDAO;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class PropietariosStageController extends StageController<PropietariosStageController>
{
    @FXML private ImageView userIcon;
    @FXML private Button cancelButton;
    @FXML private Button removeButton;
    @FXML private Button saveButton;
    @FXML private Button addButton;

    @FXML private TextField dniField;
    @FXML private TextField nameField;
    @FXML private TextField apellido1Field;
    @FXML private TextField apellido2Field;
    @FXML private TextField telefonoField;
    @FXML private TextField emailField;
    @FXML private VBox propietarioDataPane;
    @FXML private TableView<PropietarioTableItem> propietariosTableView;

    private PropietarioTableItem selectedPropietario;

    public static final int ICON_SIZE = 20;

    @Override
    protected void initialize()
    {
        userIcon.setImage(new Image(this.getClass().getResourceAsStream("/icons/user.png")));
        
        removeButton.setGraphic(load(this.getClass().getResourceAsStream("/icons/delete.png")));
        addButton.setGraphic(load(this.getClass().getResourceAsStream("/icons/add.png")));
        saveButton.setGraphic(load(this.getClass().getResourceAsStream("/icons/save.png")));
        cancelButton.setGraphic(load(this.getClass().getResourceAsStream("/icons/exit.png")));

        propietariosTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        propietariosTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue == null || (oldValue != null && oldValue.equals(newValue)))
            {
                return;
            }

            showPropietarioDataPane(newValue, true);
        });

        ((TableColumn<PropietarioTableItem, String>) propietariosTableView.getColumns().getFirst()).setCellValueFactory(
                propietario -> propietario.getValue().nameCompleto);
        ((TableColumn<PropietarioTableItem, String>) propietariosTableView.getColumns().get(1)).setCellValueFactory(
                propietario -> propietario.getValue().dni);
        ((TableColumn<PropietarioTableItem, String>) propietariosTableView.getColumns().get(2)).setCellValueFactory(
                propietario -> propietario.getValue().tel);
        ((TableColumn<PropietarioTableItem, String>) propietariosTableView.getColumns().get(3)).setCellValueFactory(
                propietario -> propietario.getValue().email);

        populatePropietariosTableView();

        hidePropietarioDataPane();
    }

    private static ImageView load(InputStream is)
    {
        var deleteImage = new Image(is);
        var imageView = new ImageView(deleteImage);
        imageView.setFitHeight(ICON_SIZE);
        imageView.setFitWidth(ICON_SIZE);
        return imageView;
    }

    private void populatePropietariosTableView()
    {
        propietariosTableView.getItems().clear();
        propietariosTableView.getItems()
                .addAll(PropietarioTableItem.convertFromPropietarios(Propietario.selectAll("")));
    }

    private void showPropietarioDataPane(PropietarioTableItem propietarioTableItem, boolean deleteButton)
    {
        final var root = (Pane) getRoot();

        if (!root.getChildren().contains(propietarioDataPane))
        {
            root.getChildren().addLast(propietarioDataPane);
        }

        removeButton.setVisible(deleteButton);
        removeButton.setDisable(propietarioTableItem.intoPropietario().hasDogs());
        
        getRoot().getScene().getWindow().sizeToScene();
        getRoot().getScene().getWindow().centerOnScreen();

        selectedPropietario = propietarioTableItem;

        propietarioDataPane.setVisible(true);
        dniField.setEditable(false);

        nameField.setText(propietarioTableItem.name);
        apellido1Field.setText(propietarioTableItem.ape1);
        apellido2Field.setText(propietarioTableItem.ape2);
        dniField.setText(propietarioTableItem.dni.get());
        telefonoField.setText(propietarioTableItem.tel.get());
        emailField.setText(propietarioTableItem.email.get());
    }

    @FXML
    private void hidePropietarioDataPane()
    {
        selectedPropietario = null;
        ((Pane) getRoot()).getChildren().remove(propietarioDataPane);
        propietariosTableView.getSelectionModel().select(null);
        removeButton.setVisible(false);
    }

    @FXML
    private void saveButton()
    {
        if (!validateUserData()) return;

        final Propietario propietarioPreEdicion = selectedPropietario.intoPropietario();
        final Propietario propietarioPostEdicion = getPropietarioData();
        final PropietarioTableItem actualEditiong = selectedPropietario;
        
        boolean result;
        String errorText;
        
        if (propietarioPreEdicion.equals(propietarioPostEdicion)) 
        {
            hidePropietarioDataPane();
            return;
        }
        
        if (!propietarioPreEdicion.getDni().isBlank()) 
        {
            result = PropietarioDAO.actualizarPropietario(dniField.getText(), propietarioPostEdicion);
            errorText = "No se pudo editar al propietario. Vuelva a intentarlo más tarde";
        }
        else
        {
            result = propietarioPostEdicion.insert();
            errorText = "No se puedo añadir al propietario. Revise el dni";
        }

        Notifications noti = Notifications.create().owner(getRoot());
        noti.hideAfter(Duration.seconds(3));

        boolean[] consumed = new boolean[1];
        
        if (result)
        {
            
            final var rollback = new Action(e ->
            {
                if (consumed[0]) return;
                
                PropietarioDAO.actualizarPropietario(propietarioPreEdicion.getDni(), propietarioPreEdicion);
                updatePropitarioTableItem(actualEditiong, propietarioPreEdicion);
                propietariosTableView.sort();
                
                consumed[0] = true;
            });

            rollback.setText("Deshacer");

            noti.text("Se han actualizado los datos con exito")
                    .action(rollback)
                    .showConfirm();
            
            updatePropitarioTableItem(selectedPropietario, new Propietario(dniField.getText(), nameField.getText(),
                    apellido1Field.getText(), apellido2Field.getText(), emailField.getText(), telefonoField.getText()));

            if (!propietariosTableView.getItems().contains(selectedPropietario))
            {
                propietariosTableView.getItems().add(selectedPropietario);
                propietariosTableView.sort();
            }

            hidePropietarioDataPane();
        }
        else
        {
            noti.text(errorText).showError();
        }
    }

    private void updatePropitarioTableItem(PropietarioTableItem tableItem, Propietario propietario)
    {
        tableItem.name = propietario.getNome();
        tableItem.ape1 = propietario.getAp1();
        tableItem.ape2 = propietario.getAp2();
        tableItem.tel.set(propietario.getTlf());
        tableItem.dni.set(propietario.getDni());
        tableItem.email.set(propietario.getEmail());
        tableItem.nameCompleto.set(String.format("%s %s %s", tableItem.name,
                tableItem.ape1, tableItem.ape2));
    }
    
    @FXML
    private void removeButton()
    {
        boolean result = PropietarioDAO.eliminarUnPropietarioDadoSeuDni(dniField.getText());

        Notifications noti = Notifications.create().owner(getRoot());
        noti.hideAfter(Duration.seconds(3));

        final PropietarioTableItem propietarioTableItem = selectedPropietario;
        final Propietario propietrioEliminado = propietarioTableItem.intoPropietario();
        boolean[] consumed = new boolean[1];

        if (result)
        {
            final var rollback = new Action(e ->
            {
                if (consumed[0]) return;
                
                propietrioEliminado.insert();
                propietariosTableView.getItems().add(propietarioTableItem);
                propietariosTableView.sort();
                
                consumed[0] = true;
            });

            rollback.setText("Deshacer");

            noti.text("Propietario borrado exitosamente")
                    .action(rollback)
                    .position(Pos.BOTTOM_RIGHT) // Cambia la posición si lo deseas
                    .showConfirm();

            propietariosTableView.getItems().remove(selectedPropietario);

            hidePropietarioDataPane();
        }
        else
        {
            noti.text("El propietario no puede ser borrado").showInformation();
        }
    }

    @FXML
    private void addButton()
    {
        final var propietarioTableItem = PropietarioTableItem.empty();
        showPropietarioDataPane(propietarioTableItem, false);
        selectedPropietario = propietarioTableItem;
        dniField.setEditable(true);
    }

    private boolean validateUserData()
    {
        if (dniField.getText().isBlank() || !dniField.getText().matches("[0-9]{8}[A-Za-z]"))
        {
            UIEffects.shakeNode(dniField);
            return false;
        }

        dniField.setText(dniField.getText().toUpperCase());

        if (nameField.getText().isBlank())
        {
            UIEffects.shakeNode(nameField);
            return false;
        }

        if (apellido1Field.getText().isBlank())
        {
            UIEffects.shakeNode(apellido1Field);
            return false;
        }

        return true;
    }

    private Propietario getPropietarioData()
    {
        return new Propietario(
                dniField.getText(),
                nameField.getText(),
                apellido1Field.getText(),
                apellido2Field.getText(),
                emailField.getText(),
                telefonoField.getText()
        );
    }

    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder)
    {
        stageBuilder.setResizeable(true).setModality(Modality.APPLICATION_MODAL);
    }

    @Override
    public String getTitle()
    {
        return "Propietarios";
    }

    @Override
    public URL getFXML()
    {
        return PropietariosStageController.class.getResource("propietarios-stage.fxml");
    }

    private static class PropietarioTableItem
    {
        private final StringProperty nameCompleto;
        private String name;
        private String ape1;
        private String ape2;
        private final StringProperty dni;
        private final StringProperty tel;
        private final StringProperty email;

        public PropietarioTableItem(StringProperty nameCompleto, String name, String ape1, String ape2,
                StringProperty dni,
                StringProperty tel, StringProperty email)
        {
            this.nameCompleto = nameCompleto;
            this.name = name;
            this.ape1 = ape1;
            this.ape2 = ape2;
            this.dni = dni;
            this.tel = tel;
            this.email = email;
        }

        public PropietarioTableItem(Propietario propietario)
        {
            this(new SimpleStringProperty(
                            String.format("%s %s %s", propietario.getNome(), propietario.getAp1(), propietario.getAp2())),
                    propietario.getNome(), propietario.getAp1(), propietario.getAp2(),
                    new SimpleStringProperty((propietario.getDni())), new SimpleStringProperty(propietario.getTlf()),
                    new SimpleStringProperty(propietario.getEmail()));
        }

        public static PropietarioTableItem empty()
        {
            return new PropietarioTableItem(new Propietario("", "", "", "", "", ""));
        }

        public Propietario intoPropietario()
        {
            return new Propietario(dni.get(), name, ape1, ape2, email.get(), tel.get());
        }

        public static List<PropietarioTableItem> convertFromPropietarios(Collection<Propietario> propietarios)
        {
            List<PropietarioTableItem> items = new ArrayList<>();

            propietarios.forEach(propietario -> items.add(new PropietarioTableItem(propietario)));

            return items;
        }

        @Override
        public boolean equals(Object o)
        {
            if (!(o instanceof PropietarioTableItem that)) return false;

            return Objects.equals(dni, that.dni);
        }

        @Override
        public int hashCode()
        {
            int result = Objects.hashCode(name);
            result = 31 * result + Objects.hashCode(dni);
            return result;
        }
    }
}
