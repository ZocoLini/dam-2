package org.lebastudios.aplicacioncompleja.formularios;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import net.sf.jasperreports.engine.JRException;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.database.Database;
import org.lebastudios.aplicacioncompleja.database.entities.Propietario;
import org.lebastudios.aplicacioncompleja.reports.PropietariosCansReport;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class FormularioReportPropietariosController extends StageController<FormularioReportPropietariosController>
{
    @FXML private TableView<PropietarioTableItem> propietarioTableView;
    
    private TableColumn<PropietarioTableItem, String> nameColumn;
    private TableColumn<PropietarioTableItem, String> dniColumn;
    
    @Override
    protected void initialize()
    {
        propietarioTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);

        nameColumn = (TableColumn<PropietarioTableItem, String>) propietarioTableView.getColumns().getFirst();
        nameColumn.setCellValueFactory(propietario -> propietario.getValue().nameCompleto);
        
        dniColumn = (TableColumn<PropietarioTableItem, String>) propietarioTableView.getColumns().get(1);
        dniColumn.setCellValueFactory(propietario -> propietario.getValue().dni);
        
        propietarioTableView.getItems().clear();
        propietarioTableView.getItems().addAll(
                PropietarioTableItem.convertFromPropietarios(Propietario.selectAll(""))
        );
    }

    private String convertTableSortIntoOrderBy()
    {
        ObservableList<TableColumn<PropietarioTableItem, ?>> sortOrder = propietarioTableView.getSortOrder();
        
        if (sortOrder.isEmpty()) 
        {
            return "ORDER BY dni, cans.nome";
        }
        
        String orderBy = "ORDER BY";
        
        if (sortOrder.contains(nameColumn)) 
        {
            orderBy += " propietarios.nome";

            switch (sortOrder.getFirst().getSortType())
            {
                case DESCENDING -> orderBy += " desc";
                case ASCENDING -> orderBy += " asc";
            }
        }
        
        if (sortOrder.contains(dniColumn)) 
        {
            orderBy += " dni";

            switch (sortOrder.getFirst().getSortType())
            {
                case DESCENDING -> orderBy += " desc";
                case ASCENDING -> orderBy += " asc";
            }
        }

        orderBy += ", cans.nome";
        
        System.out.println(orderBy);
        
        return orderBy;
    }
    
    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder)
    {
        stageBuilder.setModality(Modality.APPLICATION_MODAL);
    }

    @Override
    public String getTitle()
    {
        return "Mantenimiento";
    }

    @Override
    public URL getFXML()
    {
        return FormularioReportPropietariosController.class.getResource("formulario-report-propietarios.fxml");
    }

    @FXML private void showReport() 
    {
        PropietariosCansReport report = new PropietariosCansReport();
        Database.getInstance().connect(conn ->
        {
            try
            {
                report.showReport(convertTableSortIntoOrderBy(), conn);
            }
            catch (JRException e)
            {
                e.printStackTrace();
            }
        });
    }

    private static class PropietarioTableItem
    {
        private final StringProperty nameCompleto;
        private final StringProperty dni;

        public PropietarioTableItem(StringProperty nameCompleto, StringProperty dni)
        {
            this.nameCompleto = nameCompleto;
            this.dni = dni;
        }

        public PropietarioTableItem(Propietario propietario)
        {
            this(new SimpleStringProperty(
                            String.format("%s %s %s", propietario.getNome(), propietario.getAp1(), propietario.getAp2())
                    ),
                    new SimpleStringProperty((propietario.getDni()))
            );
        }

        public static List<PropietarioTableItem> convertFromPropietarios(
                Collection<Propietario> propietarios)
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
            int result = Objects.hashCode(nameCompleto);
            result = 31 * result + Objects.hashCode(dni);
            return result;
        }
    }
}
