package org.lebastudios.aplicacioncompleja;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.WindowEvent;
import org.lebastudios.aplicacioncompleja.controllers.StageController;
import org.lebastudios.aplicacioncompleja.events.AppLifeCicleEvents;
import org.lebastudios.aplicacioncompleja.facturacion.FacturacionStageController;
import org.lebastudios.aplicacioncompleja.formularios.FormularioCitaPerruceriaController;
import org.lebastudios.aplicacioncompleja.formularios.FormularioNuevoPropietarioController;
import org.lebastudios.aplicacioncompleja.formularios.StageAltaRazaPerroController;
import org.lebastudios.aplicacioncompleja.perruqueria.VisorCitasPerruqueriaStageController;
import org.lebastudios.aplicacioncompleja.propietarios.PropietariosStageController;
import org.lebastudios.aplicacioncompleja.ui.StageBuilder;

import java.net.URL;

public class MainStageController extends StageController<MainStageController>
{
    public void initialize()
    {
        getRoot().addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, _ -> AppLifeCicleEvents.OnAppCloseRequest.invoke());
    }
    
    @Override
    protected void customizeStageBuilder(StageBuilder stageBuilder) 
    {
        stageBuilder.setResizeable(true);
    }

    @Override
    public String getTitle()
    {
        return "Xestión Clínica";
    }

    @Override
    public URL getFXML()
    {
        return MainStageController.class.getResource("main-stage.fxml");
    }

    @FXML private void openMenuPerrosRazas(ActionEvent actionEvent) 
    {
        new StageAltaRazaPerroController().instantiate();
    }

    @FXML private void openMenuPropietarios(ActionEvent actionEvent) 
    {
        new FormularioNuevoPropietarioController().instantiate();
    }
    
    @FXML private void menuSalir(ActionEvent actionEvent) 
    {
        Platform.exit();
    }

    @FXML private void openMenuImpresionFacturas()
    {
        new FacturacionStageController().instantiate();
    }
    
    @FXML private 
    void openFormularioCitaPerruqueria(ActionEvent actionEvent) 
    {
        new FormularioCitaPerruceriaController().instantiate();
    }
    
    @FXML private void openMenuCitaPerruqueria(ActionEvent actionEvent)
    {
        new VisorCitasPerruqueriaStageController().instantiate();
    }
    
    @FXML private void openMenuGestionPropietarios(ActionEvent actionEvent)
    {
        new PropietariosStageController().instantiate();
    }
}
