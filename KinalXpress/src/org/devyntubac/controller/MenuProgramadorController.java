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
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 24/04/2024
 */
public class MenuProgramadorController implements Initializable {

    private Main escenarioPrincipal;
    @FXML Button btnRegresarP;

    /**
     * Getter y Setter.
     */
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Este metodo realiza la función para cada boton.
     * @param event recibe este parametro para realizar acción. 
     */
    @FXML
    public void handleButtonAction(ActionEvent event){
        /**
         * Si el evento es igual al boton regresar muestra el menu principal.
         */
        if(event.getSource() == btnRegresarP){
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
