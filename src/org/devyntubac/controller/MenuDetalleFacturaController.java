package org.devyntubac.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.devyntubac.system.Main;
import org.devyntubac.bean.*;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javax.swing.JOptionPane;
import org.devyntubac.db.Conexion;

/**
 * FXML Controller class
 *
 * @author devyn
 */
public class MenuDetalleFacturaController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    Button btnRegresar;
    @FXML
    private TableView tblDetalleFactura;

    @FXML
    private TableColumn colCodDetalleFactura;

    @FXML
    private TableColumn colPrecioUnitario;

    @FXML
    private TableColumn colCantidad;

    @FXML
    private TableColumn colNumeroFactura;

    @FXML
    private TableColumn colCodProducto;

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
    private ComboBox cmbCodigoProducto;

    @FXML
    private ComboBox cmbNumeroFactura;

    @FXML
    private TextField txtCodDetalleFactura;

    @FXML
    private TextField txtPrecioUnitario;

    @FXML
    private TextField txtCantidad;

    public ObservableList<DetalleFactura> listarDetalleFactura;
    public ObservableList<Factura> listarFacturas;
    public ObservableList<Productos> listarProductos;

    /**
     * Enumeradores para las operaciones que se utilizaran en el programa.
     */
    private enum operaciones {
        ELIMINAR, EDITAR, CANCELAR, ACTUALIZAR, NINGUNO
    }
    /**
     * Variable que indica el tipo de operación actual.
     */
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public ObservableList<DetalleFactura> getDetalleFactura() {
        ArrayList<DetalleFactura> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_listarDetallesFacturas();");
            resultado = p.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleFactura(resultado.getInt("codigoDetalleFactura"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("numeroFactura"),
                        resultado.getString("codigoProducto")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarDetalleFactura = FXCollections.observableList(lista);
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

    public ObservableList<Factura> getFacturas() {
        ArrayList<Factura> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_listarFactura();");
            resultado = p.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getDate("fechaFactura"),
                        resultado.getInt("clienteID"),
                        resultado.getInt("codigoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarFacturas = FXCollections.observableList(lista);
    }

    public void cargarDatos() {
        tblDetalleFactura.setItems(getDetalleFactura());
        colCodDetalleFactura.setCellValueFactory(new PropertyValueFactory<>("codigoDetalleFactura"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<>("numeroFactura"));
        colCodProducto.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));

    }

    public void guardar() {
        DetalleFactura registro = new DetalleFactura();
        registro.setCodigoDetalleFactura(Integer.parseInt(txtCodDetalleFactura.getText()));
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setNumeroFactura(((Factura) cmbNumeroFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
        registro.setCodigoProducto(((Productos) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_agregarDetalleFactura(?,?,?,?,?);");
            p.setInt(1, registro.getCodigoDetalleFactura());
            p.setDouble(2, registro.getPrecioUnitario());
            p.setInt(3, registro.getCantidad());
            p.setInt(4, registro.getNumeroFactura());
            p.setString(5, registro.getCodigoProducto());
            p.execute();
            listarDetalleFactura.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/devyntubac/images/guardarIcono.png"));
                imgEliminar.setImage(new Image("/org/devyntubac/images/cancelarIcono.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            /**
             * Si el tipo de Operaciones es ACTUALIZAR, se llama al metodo
             * guardar para realizar su respectiva función y desactiva y limpia
             * los textField, y a su vez los botones vuelven a su apariencia
             * normal.
             */
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/devyntubac/images/agregarCliente.png"));
                imgEliminar.setImage(new Image("/org/devyntubac/images/eliminarCliente.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public Factura buscarFactura(int numeroFactura) {
        Factura resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_buscarFactura(?);");
            p.setInt(1, numeroFactura);
            ResultSet registro = p.executeQuery();
            while (registro.next()) {
                resultado = new Factura(registro.getInt("numeroFactura"),
                        registro.getString("estado"),
                        registro.getDouble("totalFactura"),
                        registro.getDate("fechaFactura"),
                        registro.getInt("clienteID"),
                        registro.getInt("codigoEmpleado"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Productos buscarProducto(String codigoProducto) {
        Productos resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_buscarProductos(?);");
            p.setString(1, codigoProducto);
            ResultSet registro = p.executeQuery();
            while (registro.next()) {
                resultado = new Productos(registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getString("imagenProducto"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoTipoProducto"),
                        registro.getInt("codigoProveedor"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            /**
             * Si el tipo de Operaciones es ACTUALIZAR, se llama al metodo
             * guardar para realizar su respectiva función y desactiva y limpia
             * los textField, y a su vez los botones vuelven a su apariencia
             * normal.
             */
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/devyntubac/images/agregarCliente.png"));
                imgEliminar.setImage(new Image("/org/devyntubac/images/eliminarCliente.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            /**
             * Si no procede a mostrar un cuadro de dialogo para confirmar la
             * eliminación, prepara y ejecuta el procedimiento eliminar de la
             * base de datos.
             */
            default:
                if (tblDetalleFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminación del registro", "Eliminar Detalle Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarDetalleFactura(?);");
                            procedimiento.setInt(1, ((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura());
                            procedimiento.execute();
                            listarDetalleFactura.remove(tblDetalleFactura.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Registro para Eliminar");
                }
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarDetalleFactura(?,?,?,?,?);");
            DetalleFactura registro = (DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem();

            registro.setCodigoDetalleFactura(Integer.parseInt(txtCodDetalleFactura.getText()));
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setNumeroFactura(((Factura) cmbNumeroFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
            registro.setCodigoProducto(((Productos) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());

            p.setInt(1, registro.getCodigoDetalleFactura());
            p.setDouble(2, registro.getPrecioUnitario());
            p.setInt(3, registro.getCantidad());
            p.setInt(4, registro.getNumeroFactura());
            p.setString(5, registro.getCodigoProducto());
            p.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                /**
                 * Si tipo de operaciones es NINGUNO, primero verifica si hay
                 * registros en la tabla y si no muestra un cuadro de dialogo
                 * para que el usuario selecciono algun registro.
                 */
                if (tblDetalleFactura.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/devyntubac/images/actualizarIcono.png"));
                    imgReporte.setImage(new Image("/org/devyntubac/images/cancelarIcono.png"));
                    activarControles();
                    txtCodDetalleFactura.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Registro para Actualizar");
                }
                break;
            case ACTUALIZAR:
                /**
                 * Si es ACTUALIZAR llama al metodo actualizar y restaura los
                 * botones y textField.
                 */
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/devyntubac/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/devyntubac/images/reportesCliente.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                desactivarControles();
                limpiarControles();
                cargarDatos();
                break;
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/devyntubac/images/iconoEditar.png"));
                imgReporte.setImage(new Image("/org/devyntubac/images/reportesCliente.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    
    public void seleccionarElementos() {
        txtCodDetalleFactura.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura()));
        txtPrecioUnitario.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtCantidad.setText(String.valueOf(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbNumeroFactura.getSelectionModel().select(buscarFactura(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        cmbCodigoProducto.getSelectionModel().select(buscarProducto(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoProducto()));
    }

    public void activarControles() {
        txtCodDetalleFactura.setEditable(true);
        txtPrecioUnitario.setEditable(true);
        txtCantidad.setEditable(true);
        cmbNumeroFactura.setDisable(false);
        cmbCodigoProducto.setDisable(false);
    }

    public void desactivarControles() {
        txtCodDetalleFactura.setEditable(false);
        txtPrecioUnitario.setEditable(false);
        txtCantidad.setEditable(false);
        cmbNumeroFactura.setDisable(true);
        cmbCodigoProducto.setDisable(true);
    }

    public void limpiarControles() {
        txtCodDetalleFactura.clear();
        txtPrecioUnitario.clear();
        txtCantidad.clear();
        tblDetalleFactura.getSelectionModel().getSelectedItem();
        cmbNumeroFactura.getSelectionModel().getSelectedItem();
        cmbCodigoProducto.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        /**
         * Si el evento es igual al boton regresar muestra el menu principal.
         */
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuFactura();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbNumeroFactura.setItems(getFacturas());
        cmbCodigoProducto.setItems(getProductos());
    }

}
