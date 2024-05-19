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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import org.devyntubac.bean.Compras;
import org.devyntubac.bean.DetalleCompra;
import org.devyntubac.bean.Productos;
import org.devyntubac.db.Conexion;
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
    @FXML
    private TableView tblDetalleCompra;

    @FXML
    private TableColumn colCodigoDetalleC;

    @FXML
    private TableColumn colCostoUnitario;

    @FXML
    private TableColumn colCantidad;

    @FXML
    private TableColumn colCodigoProducto;

    @FXML
    private TableColumn colNumeroDocumento;

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
    private ComboBox cmbNumeroDocumento;

    @FXML
    private ComboBox cmbCodigoProducto;

    @FXML
    private TextField txtCodDetalleC;

    @FXML
    private TextField txtBuscar;
    
    @FXML
    private TextField txtCostoUnitario;

    @FXML
    private TextField txtCantidadDetalleC;

    private ObservableList<DetalleCompra> listarDetalleCompra;
    private ObservableList<Productos> listarProductos;
    private ObservableList<Compras> listarCompras;
    private ObservableList<DetalleCompra> buscarDetalleCompra;

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

    public ObservableList<DetalleCompra> getDetalleCompra() {
        ArrayList<DetalleCompra> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_listarDetalleCompra();");
            resultado = p.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getString("codigoProducto"),
                        resultado.getInt("numeroDocumento")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarDetalleCompra = FXCollections.observableList(lista);
    }

    /**
     * Este metodo tiene la función de listar los datos, y a su vez prepara y
     * ejecuta el procedimiento de listar de la base de datos.
     *
     * @return La lista de Compras
     */
    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement pc = Conexion.getInstance().getConexion().prepareCall("call sp_listarCompras();");
            resultado = pc.executeQuery();
            /**
             * El bucle while agrega los datos a la lista.
             */
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getDate("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarCompras = FXCollections.observableList(lista);
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
        tblDetalleCompra.setItems(getDetalleCompra());
        colCodigoDetalleC.setCellValueFactory(new PropertyValueFactory<>("codigoDetalleCompra"));
        colCostoUnitario.setCellValueFactory(new PropertyValueFactory<>("costoUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        colNumeroDocumento.setCellValueFactory(new PropertyValueFactory<>("numeroDocumento"));
    }

    public void desactivarControles() {
        txtCodDetalleC.setEditable(false);
        txtCostoUnitario.setEditable(false);
        txtCantidadDetalleC.setEditable(false);
        cmbCodigoProducto.setDisable(true);
        cmbNumeroDocumento.setEditable(true);
    }

    public void activarControles() {
        txtCodDetalleC.setEditable(true);
        txtCostoUnitario.setEditable(true);
        txtCantidadDetalleC.setEditable(true);
        cmbCodigoProducto.setDisable(false);
        cmbNumeroDocumento.setEditable(false);
    }

    public void limpiarControles() {
        txtCodDetalleC.clear();
        txtCostoUnitario.clear();
        txtCantidadDetalleC.clear();
        tblDetalleCompra.getSelectionModel().getSelectedItem();
        cmbCodigoProducto.getSelectionModel().getSelectedItem();
        cmbNumeroDocumento.getSelectionModel().getSelectedItem();
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

    public void guardar() {
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtCodDetalleC.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidadDetalleC.getText()));
        registro.setCodigoProducto(((Productos) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroDocumento(((Compras) cmbNumeroDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_agregarDetalleCompra(?,?,?,?,?);");
            p.setInt(1, registro.getCodigoDetalleCompra());
            p.setDouble(2, registro.getCostoUnitario());
            p.setInt(3, registro.getCantidad());
            p.setString(4, registro.getCodigoProducto());
            p.setInt(5, registro.getNumeroDocumento());
            p.execute();
            listarDetalleCompra.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seleccionarElementos() {
        txtCodDetalleC.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
        txtCostoUnitario.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtCantidadDetalleC.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbCodigoProducto.getSelectionModel().select(buscarProducto(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoProducto())));
        cmbNumeroDocumento.getSelectionModel().select(buscarCompra(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
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

    public Compras buscarCompra(int numeroDocumento) {
        Compras resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_buscarCompras(?);");
            p.setInt(1, numeroDocumento);
            ResultSet registro = p.executeQuery();
            while (registro.next()) {
                resultado = new Compras(registro.getInt("numeroDocumento"),
                        registro.getDate("fechaDocumento"),
                        registro.getString("descripcion"),
                        registro.getDouble("totalDocumento"));
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
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminación del registro", "Eliminar Detalle Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarDetalleCompra(?);");
                            procedimiento.setInt(1, ((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            procedimiento.execute();
                            listarDetalleCompra.remove(tblDetalleCompra.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Detalle Compra para Eliminar");
                }
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarDetalleCompra(?,?,?,?,?);");
            DetalleCompra registro = (DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem();
            registro.setCodigoDetalleCompra(Integer.parseInt(txtCodDetalleC.getText()));
            registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidadDetalleC.getText()));
            registro.setCodigoProducto(((Productos) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());
            registro.setNumeroDocumento(((Compras) cmbNumeroDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());

            p.setInt(1, registro.getCodigoDetalleCompra());
            p.setDouble(2, registro.getCostoUnitario());
            p.setInt(3, registro.getCantidad());
            p.setString(4, registro.getCodigoProducto());
            p.setInt(5, registro.getNumeroDocumento());
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
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/devyntubac/images/actualizarIcono.png"));
                    imgReporte.setImage(new Image("/org/devyntubac/images/cancelarIcono.png"));
                    activarControles();
                    txtCodDetalleC.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Detalle Compra para Actualizar");
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
    
    public void buscarDetalleCompra(KeyEvent event){
        String filtrarDetalleCompra = txtBuscar.getText();
        if(filtrarDetalleCompra.isEmpty()){
            tblDetalleCompra.setItems(listarDetalleCompra);
        }else{
            buscarDetalleCompra.clear();
            for(DetalleCompra c : listarDetalleCompra){
                if(String.valueOf(c.getCodigoDetalleCompra()).equals(filtrarDetalleCompra)){
                    buscarDetalleCompra.add(c);
                }
            }
            tblDetalleCompra.setItems(buscarDetalleCompra);
        }
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
        cargarDatos();
        cmbCodigoProducto.setItems(getProductos());
        cmbNumeroDocumento.setItems(getCompras());
        buscarDetalleCompra = FXCollections.observableArrayList();
    }

}
