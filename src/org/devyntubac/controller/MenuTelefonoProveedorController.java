package org.devyntubac.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.devyntubac.bean.Proveedores;
import org.devyntubac.bean.TelefonoProveedor;
import org.devyntubac.system.Main;
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
public class MenuTelefonoProveedorController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    Button btnRegresar;
    @FXML
    private TableView tblTelefonoProveedor;

    @FXML
    private TableColumn colTelefono;

    @FXML
    private TableColumn colNumPrincipal;

    @FXML
    private TableColumn colNumSecundario;

    @FXML
    private TableColumn colObservaciones;

    @FXML
    private TableColumn colCodProveedor;

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
    private TextField txtObservaciones;

    @FXML
    private ComboBox cmbCodProveedor;

    @FXML
    private TextField txtCodigoTelefono;

    @FXML
    private TextField txtNumPrincipal;

    @FXML
    private TextField txtNumSecundario;

    private ObservableList<TelefonoProveedor> listarTelefonoProveedor;
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

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public ObservableList<TelefonoProveedor> getTelefonoProveedor() {
        ArrayList<TelefonoProveedor> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_listarTelefonoProveedor;");
            resultado = p.executeQuery();
            while (resultado.next()) {
                lista.add(new TelefonoProveedor(resultado.getInt("codigoTelefonoProveedor"),
                        resultado.getString("numeroPrincipal"),
                        resultado.getString("numeroSecundario"),
                        resultado.getString("observaciones"),
                        resultado.getInt("codigoProveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarTelefonoProveedor = FXCollections.observableList(lista);
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

    public void guardar() {
        TelefonoProveedor registro = new TelefonoProveedor();
        registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodigoTelefono.getText()));
        registro.setNumeroPrincipal(txtNumPrincipal.getText());
        registro.setNumeroSecundario(txtNumSecundario.getText());
        registro.setObservaciones(txtObservaciones.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCodProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_agregarTelefonoProveedor(?,?,?,?,?);");
            p.setInt(1, registro.getCodigoTelefonoProveedor());
            p.setString(2, registro.getNumeroPrincipal());
            p.setString(3, registro.getNumeroSecundario());
            p.setString(4, registro.getObservaciones());
            p.setInt(5, registro.getCodigoProveedor());
            p.execute();
            listarTelefonoProveedor.add(registro);
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

    public void actualizar() {
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarTelefonoProveedor(?,?,?,?,?);");
            TelefonoProveedor registro = (TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem();

            registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodigoTelefono.getText()));
            registro.setNumeroPrincipal(txtNumPrincipal.getText());
            registro.setNumeroSecundario(txtNumSecundario.getText());
            registro.setObservaciones(txtObservaciones.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCodProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());

            p.setInt(1, registro.getCodigoTelefonoProveedor());
            p.setString(2, registro.getNumeroPrincipal());
            p.setString(3, registro.getNumeroSecundario());
            p.setString(4, registro.getObservaciones());
            p.setInt(5, registro.getCodigoProveedor());
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
                if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/devyntubac/images/actualizarIcono.png"));
                    imgReporte.setImage(new Image("/org/devyntubac/images/cancelarIcono.png"));
                    activarControles();
                    txtCodigoTelefono.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Telefono para Actualizar");
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

    public void cargarDatos() {
        tblTelefonoProveedor.setItems(getTelefonoProveedor());
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("codigoTelefonoProveedor"));
        colNumPrincipal.setCellValueFactory(new PropertyValueFactory<>("numeroPrincipal"));
        colNumSecundario.setCellValueFactory(new PropertyValueFactory<>("numeroSecundario"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        colCodProveedor.setCellValueFactory(new PropertyValueFactory<>("codigoProveedor"));
    }

    public void activarControles() {
        txtCodigoTelefono.setEditable(true);
        txtNumPrincipal.setEditable(true);
        txtNumSecundario.setEditable(true);
        txtObservaciones.setEditable(true);
        cmbCodProveedor.setDisable(false);
    }

    public void desactivarControles() {
        txtCodigoTelefono.setEditable(false);
        txtNumPrincipal.setEditable(false);
        txtNumSecundario.setEditable(false);
        txtObservaciones.setEditable(false);
        cmbCodProveedor.setDisable(true);
    }

    public void limpiarControles() {
        txtCodigoTelefono.clear();
        txtNumPrincipal.clear();
        txtNumSecundario.clear();
        txtObservaciones.clear();
        tblTelefonoProveedor.getSelectionModel().getSelectedItem();
        cmbCodProveedor.getSelectionModel().getSelectedItem();
    }

    public void seleccionarElementos() {
        txtCodigoTelefono.setText(String.valueOf(((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor()));
        txtNumPrincipal.setText(((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getNumeroPrincipal());
        txtNumSecundario.setText(((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getNumeroSecundario());
        txtObservaciones.setText(((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getObservaciones());
        cmbCodProveedor.getSelectionModel().select(buscarProveedores(((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
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
                if (tblTelefonoProveedor.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminación del registro", "Eliminar Telefono Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarTelefonoProveedor(?);");
                            procedimiento.setInt(1, ((TelefonoProveedor) tblTelefonoProveedor.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
                            procedimiento.execute();
                            listarTelefonoProveedor.remove(tblTelefonoProveedor.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Telefono para Eliminar");
                }
                break;
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        /**
         * Si el evento es igual al boton regresar muestra el menu principal.
         */
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuProveedorView();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCodProveedor.setItems(getProveedores());
    }

}
