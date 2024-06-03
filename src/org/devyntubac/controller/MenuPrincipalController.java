package org.devyntubac.controller;

import java.net.URL;
import org.devyntubac.system.Main;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

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
    @FXML
    MenuItem btnEmpleado;
    @FXML
    MenuItem btnDetalleFactura;
    @FXML
    MenuItem btnFactura;
    @FXML
    Button btnUbicaciones;
    @FXML
    Button btnHorario;
    @FXML
    MenuItem btnCerrarSesion;

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
        if (event.getSource() == btnEmpleado) {
            escenarioPrincipal.menuEmpleados();
        }
        if (event.getSource() == btnDetalleFactura) {
            escenarioPrincipal.menuDetalleFactura();
        }
        if (event.getSource() == btnFactura) {
            escenarioPrincipal.menuFactura();
        }
        if (event.getSource() == btnUbicaciones) {
            escenarioPrincipal.menuUbicaciones();
        }
        if (event.getSource() == btnHorario) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/devyntubac/view/ViewHorarios.fxml"));
                Parent root = loader.load();
                MenuHorariosController controlador = loader.getController();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("KinalXpress");
                stage.getIcons().add(new Image("/org/devyntubac/images/logoXpress.png"));
                stage.setScene(scene);
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (event.getSource() == btnCerrarSesion) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/devyntubac/view/ViewCerrarSesion.fxml"));
                Parent root = loader.load();
                MenuCerrarSesionController controlador = loader.getController();
                controlador.setEscenarioPrincipal(escenarioPrincipal);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Cerrar Sesion");
                stage.getIcons().add(new Image("/org/devyntubac/images/logoXpress.png"));
                stage.setScene(scene);
                stage.showAndWait();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
