package org.devyntubac.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.devyntubac.controller.MenuCargoEmpleadoController;
import org.devyntubac.controller.MenuClientesController;
import org.devyntubac.controller.MenuComprasController;
import org.devyntubac.controller.MenuDetalleCompraController;
import org.devyntubac.controller.MenuDetalleFacturaController;
import org.devyntubac.controller.MenuEmailProveedorController;
import org.devyntubac.controller.MenuEmpleadosController;
import org.devyntubac.controller.MenuFacturaController;
import org.devyntubac.controller.MenuPrincipalController;
import org.devyntubac.controller.MenuProductosController;
import org.devyntubac.controller.MenuProgramadorController;
import org.devyntubac.controller.MenuProveedoresController;
import org.devyntubac.controller.MenuTelefonoProveedorController;
import org.devyntubac.controller.MenuTipoProductoController;

/**
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 24/04/2024
 */
public class Main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    @FXML
    Button button;

    /**
     * Metodo que ejecuta y muestra las interfaces.
     *
     * @param escenarioPrincipal Llama al interfaz del escenario principal.
     * @throws IOException Reanuda la ejecución de un generador al lanzar un
     * error.
     */
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("KinalXpress");
        escenarioPrincipal.getIcons().add(new Image("/org/devyntubac/images/logoXpress.png"));
        menuPrincipalView();
        //Parent root = FXMLLoader.load(getClass().getResource("/org/devyntubac/view/MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    /**
     * Este metodo es el encargado de cambiar las escenas en la ejecucion del
     * programa.
     *
     * @param fxmlname Nombre del archivo fxml de la interfaz a mostrar.
     * @param width recibe el ancho de la interfaz.
     * @param heigth recibe la altura de la interfaz.
     * @return resultado retorna el archivo fxml.
     * @throws IOException Reanuda la ejecución de un generador al lanzar un
     * error.
     */
    public Initializable cambiarEscena(String fxmlname, int width, int heigth) throws IOException {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream("/org/devyntubac/view/" + fxmlname);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/org/devyntubac/view/" + fxmlname));

        escena = new Scene((AnchorPane) loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        escenarioPrincipal.centerOnScreen();

        resultado = (Initializable) loader.getController();

        return resultado;
    }

    /**
     * Este metodo carga la interfaz del menu principal.
     */
    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 802, 600);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Este metodo carga la interfaz de menu clientes.
     */
    public void menuClientesView() {
        try {
            MenuClientesController menuClientesView = (MenuClientesController) cambiarEscena("MenuClientes.fxml", 1020, 675);
            menuClientesView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Este metodo carga la interfaz menu programador.
     */
    public void menuProgramadorView() {
        try {
            MenuProgramadorController menuProgramadorView = (MenuProgramadorController) cambiarEscena("viewPresentacion.fxml", 566, 534);
            menuProgramadorView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Este metodo carga la interfaz menu proveedor.
     */
    public void menuProveedorView() {
        try {
            MenuProveedoresController menuProveedor = (MenuProveedoresController) cambiarEscena("ViewProveedores.fxml", 1070, 689);
            menuProveedor.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Este metodo carga la interfaz menu Cargo Empleado.
     */
    public void menuCargoEmpleado() {
        try {
            MenuCargoEmpleadoController menuCargoEmpleado = (MenuCargoEmpleadoController) cambiarEscena("ViewCargoEmpleado.fxml", 1000, 680);
            menuCargoEmpleado.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Este metodo carga la interfaz menu compras.
     */
    public void menuCompras() {
        try {
            MenuComprasController menuCompras = (MenuComprasController) cambiarEscena("ViewCompras.fxml", 952, 699);
            menuCompras.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Este metodo carga la interfaz menu tipo producto.
     */
    public void menuTipoProducto() {
        try {
            MenuTipoProductoController menuTipoProducto = (MenuTipoProductoController) cambiarEscena("ViewTipoProducto.fxml", 840, 675);
            menuTipoProducto.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Este metodo carga el menu producto.
     */
    public void menuProducto() {
        try {
            MenuProductosController menuProducto = (MenuProductosController) cambiarEscena("ViewProductos.fxml", 1200, 710);
            menuProducto.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuFactura() {
        try {
            MenuFacturaController menuFactura = (MenuFacturaController) cambiarEscena("ViewFactura.fxml", 1032, 698);
            menuFactura.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuEmpleados() {
        try {
            MenuEmpleadosController menuEmpleado = (MenuEmpleadosController) cambiarEscena("ViewEmpleados.fxml", 1120, 720);
            menuEmpleado.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuDetalleFactura() {
        try {
            MenuDetalleFacturaController menuDetalleFactura = (MenuDetalleFacturaController) cambiarEscena("ViewDetalleFactura.fxml", 1032, 697);
            menuDetalleFactura.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuDetalleCompra() {
        try {
            MenuDetalleCompraController menuDetalleFactura = (MenuDetalleCompraController) cambiarEscena("ViewDetalleCompra.fxml", 952, 699);
            menuDetalleFactura.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuDetalleTelefono() {
        try {
            MenuTelefonoProveedorController menuDetalleTelefono = (MenuTelefonoProveedorController) cambiarEscena("ViewTelefonoProveedor.fxml", 1032, 697);
            menuDetalleTelefono.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuDetalleEmail() {
        try {
            MenuEmailProveedorController menuDetalleEmail = (MenuEmailProveedorController) cambiarEscena("ViewEmailProveedor.fxml", 1032, 697);
            menuDetalleEmail.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
