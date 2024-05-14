package org.devyntubac.controller;

import java.net.URL;
import org.devyntubac.system.Main;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 24/04/2024
 */
public class MenuPrincipalController implements Initializable {

    private Main escenarioPrincipal;

    /**
     * ID de los botones utilizados en la interfaz.
     */
    @FXML
    MenuItem btnMenuClientes;
    @FXML
    MenuItem btnMenuProgramador;
    @FXML
    MenuItem btnMenuProveedores;
    @FXML
    MenuItem btnCargoEmpleado;
    @FXML
    MenuItem btnMenuCompra;
    @FXML
    MenuItem btnTipoProducto;
    @FXML
    MenuItem btnProductos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Getter y Setter del Escenario Principal.
     */
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    /**
     * Este método maneja la acción de los botones en la interfaz.
     *
     * @param event El evento que recibe la acción del botón
     */
    @FXML
    public void handleButtonAction(ActionEvent event) {
        /**
         * Muestra la interfaz de Clientes.
         */
        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClientesView();
        }
        /**
         * Muestra la interfaz de Programador.
         */
        if (event.getSource() == btnMenuProgramador) {
            escenarioPrincipal.menuProgramadorView();
        }
        /**
         * Muestra la interfaz de Proveedor.
         */
        if (event.getSource() == btnMenuProveedores) {
            escenarioPrincipal.menuProveedorView();
        }
        /**
         * Muestra la interfaz de Cargo Empleado.
         */
        if (event.getSource() == btnCargoEmpleado) {
            escenarioPrincipal.menuCargoEmpleado();
        }
        /**
         * Muestra la interfaz de Compra.
         */
        if (event.getSource() == btnMenuCompra) {
            escenarioPrincipal.menuCompras();
        }
        /**
         * Muestra la interfaz de Tipo Producto.
         */
        if (event.getSource() == btnTipoProducto) {
            escenarioPrincipal.menuTipoProducto();
        }
        /**
         * Muestra la interfaz de Producto.
         */
        if (event.getSource() == btnProductos) {
            escenarioPrincipal.menuProducto();
        }
    }

}
