package org.devyntubac.controller;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
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
import javax.swing.JOptionPane;
import org.devyntubac.bean.Productos;
import org.devyntubac.bean.Proveedores;
import org.devyntubac.bean.TipoProducto;
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
    private ComboBox cmbCodTipoProducto;

    @FXML
    private ComboBox cmbCodProveedor;

    private ObservableList<Productos> listarProductos;
    private ObservableList<TipoProducto> listarTipoProducto;
    private ObservableList<Proveedores> listarProveedores;

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

    //PreparedStatement preparedStatement = 
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

    public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarTipoProducto();");
            resultado = procedimiento.executeQuery();
            /**
             * El bucle while agrega los datos a la lista.
             */
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarTipoProducto = FXCollections.observableList(lista);
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarProveedores();");
            resultado = procedimiento.executeQuery();
            /**
             * El bucle while agrega los datos a la lista.
             */
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProvedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarProveedores = FXCollections.observableList(lista);
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
        Productos registro = new Productos();
        registro.setCodigoProducto(txtCodigoProducto.getText());
        registro.setDescripcionProducto(txtDescripcion.getText());
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
        registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
        registro.setImagenProducto(txtImagen.getText()); 
        registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
        registro.setCodigoTipoProducto(((TipoProducto) cmbCodTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
        registro.setCodigoProveedor(((Proveedores) cmbCodProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarProductos(?,?,?,?,?,?,?,?,?);");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();
            listarProductos.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seleccionarElementos() {
        txtCodigoProducto.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
        txtDescripcion.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecioUnitario.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioDocena.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioMayor.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtImagen.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getImagenProducto()));
        txtExistencia.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));;
        cmbCodTipoProducto.getSelectionModel().select(buscarTipoProducto(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        cmbCodProveedor.getSelectionModel().select(buscarProveedores(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }

    public TipoProducto buscarTipoProducto(int codigoTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarTipoProducto(?);");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                        registro.getString("descripcion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Proveedores buscarProveedores(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarProveedores(?);");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("NITProvedor"),
                        registro.getString("nombresProveedor"),
                        registro.getString("apellidosProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb"));
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
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminación del registro", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarProductos(?);");
                            procedimiento.setString(1, ((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            procedimiento.execute();
                            listarProductos.remove(tblProductos.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Producto para Eliminar");
                }
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarProductos(?,?,?,?,?,?,?,?,?);");
            Productos registro = (Productos) tblProductos.getSelectionModel().getSelectedItem();

            registro.setDescripcionProducto(txtDescripcion.getText());
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
            registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
            registro.setImagenProducto(txtImagen.getText());
            registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
            registro.setCodigoTipoProducto(((TipoProducto) cmbCodTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
            registro.setCodigoProveedor(((Proveedores) cmbCodProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());

            p.setString(1, registro.getCodigoProducto());
            p.setString(2, registro.getDescripcionProducto());
            p.setDouble(3, registro.getPrecioUnitario());
            p.setDouble(4, registro.getPrecioDocena());
            p.setDouble(5, registro.getPrecioMayor());
            p.setString(6, registro.getImagenProducto());
            p.setInt(7, registro.getExistencia());
            p.setInt(8, registro.getCodigoTipoProducto());
            p.setInt(9, registro.getCodigoProveedor());
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
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/devyntubac/images/actualizarIcono.png"));
                    imgReporte.setImage(new Image("/org/devyntubac/images/cancelarIcono.png"));
                    activarControles();
                    txtCodigoProducto.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Producto para Actualizar");
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

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    public void desactivarControles() {
        txtCodigoProducto.setEditable(false);
        txtDescripcion.setEditable(false);
        txtPrecioUnitario.setEditable(false);
        txtPrecioDocena.setEditable(false);
        txtPrecioMayor.setEditable(false);
        txtImagen.setEditable(false);
        txtExistencia.setEditable(false);
        cmbCodTipoProducto.setDisable(true);
        cmbCodProveedor.setDisable(true);
    }

    public void activarControles() {
        txtCodigoProducto.setEditable(true);
        txtDescripcion.setEditable(true);
        txtPrecioUnitario.setEditable(true);
        txtPrecioDocena.setEditable(true);
        txtPrecioMayor.setEditable(true);
        txtImagen.setEditable(true);
        txtExistencia.setEditable(true);
        cmbCodTipoProducto.setDisable(false);
        cmbCodProveedor.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoProducto.clear();
        txtDescripcion.clear();
        txtPrecioUnitario.clear();
        txtPrecioDocena.clear();
        txtPrecioMayor.clear();
        txtImagen.clear();
        txtExistencia.clear();
        tblProductos.getSelectionModel().getSelectedItem();
        cmbCodTipoProducto.getSelectionModel().getSelectedItem();
        cmbCodProveedor.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCodTipoProducto.setItems(getTipoProducto());
        cmbCodProveedor.setItems(getProveedores());
    }

}
