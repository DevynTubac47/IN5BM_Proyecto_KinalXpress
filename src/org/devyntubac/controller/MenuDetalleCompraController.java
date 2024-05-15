/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.devyntubac.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.devyntubac.system.Main;

/**
 * FXML Controller class
 *
 * @author devyn
 */
public class MenuDetalleCompraController implements Initializable {

     private Main escenarioPrincipal;
    @FXML
    Button btnRegresar;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        /**
         * Si el evento es igual al boton regresar muestra el menu principal.
         */
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuCompras();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
