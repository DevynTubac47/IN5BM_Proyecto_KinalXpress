package org.devyntubac.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.devyntubac.bean.Productos;
import org.devyntubac.db.Conexion;
import org.devyntubac.system.Main;

/**
 * FXML Controller class
 *
 * @author devyn
 */
public class MenuProductosController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    private Button btnRegresar;
    @FXML
    private TableView tblProductos;

    @FXML
    private TableColumn colCodigo;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colPrecioUnitario;

    @FXML
    private TableColumn colPrecioDocena;

    @FXML
    private TableColumn colPrecioMayor;

    @FXML
    private TableColumn colImagen;

    @FXML
    private TableColumn colExistencia;

    @FXML
    private TableColumn colCodTipoProducto;

    @FXML
    private TableColumn colCodProveedor;

    @FXML
    private TextField txtCodigoProducto;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtPrecioUnitario;

    @FXML
    private TextField txtPrecioDocena;

    @FXML
    private TextField txtPrecioMayor;

    @FXML
    private Button btnAgregar;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReporte;

    @FXML
    private ImageView imgReporte;

    @FXML
    private TextField txtImagen;

    @FXML
    private TextField txtExistencia;

    @FXML
    private TextField txtCodTipoProducto;

    @FXML
    private TextField txtCodProveedor;
    private ObservableList<Productos> listarProductos;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_listarProductos();");
            resultado = p.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarProductos = FXCollections.observableList(lista);
    }

    public void cargarDatos() {
        tblProductos.setItems(getProductos());
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcionProducto"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colPrecioDocena.setCellValueFactory(new PropertyValueFactory<>("precioDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<>("precioMayor"));
        colImagen.setCellValueFactory(new PropertyValueFactory<>("imagenProducto"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<>("Existencia"));
        colCodTipoProducto.setCellValueFactory(new PropertyValueFactory<>("codigoTipoProducto"));
        colCodProveedor.setCellValueFactory(new PropertyValueFactory<>("codigoProveedor"));
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

}
