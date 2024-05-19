package org.devyntubac.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.devyntubac.system.Main;
import org.devyntubac.bean.*;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import org.devyntubac.db.Conexion;

/**
 * FXML Controller class
 *
 * @author devyn
 */
public class MenuEmailProveedorController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    Button btnRegresar;
    @FXML
    private TextField txtCodigoEmail;

    @FXML
    private TextField txtEmailP;

    @FXML
    private TableView tblEmailProveedor;

    @FXML
    private TableColumn colCodEmail;

    @FXML
    private TableColumn colEmailP;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colCodigoP;

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
    private TextField txtDescripcion;

    @FXML
    private TextField txtBuscar;
    
    @FXML
    private ComboBox cmbCodProveedor;

    private ObservableList<EmailProveedor> listarEmailProveedor;
    private ObservableList<EmailProveedor> buscarEmailProveedor;
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

    public ObservableList<EmailProveedor> getEmailProveedor() {
        ArrayList<EmailProveedor> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_listarEmailProveedor;");
            resultado = p.executeQuery();
            while (resultado.next()) {
                lista.add(new EmailProveedor(resultado.getInt("codigoEmailProveedor"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("descripcion"),
                        resultado.getInt("codigoProveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarEmailProveedor = FXCollections.observableList(lista);
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
        tblEmailProveedor.setItems(getEmailProveedor());
        colCodEmail.setCellValueFactory(new PropertyValueFactory<>("codigoEmailProveedor"));
        colEmailP.setCellValueFactory(new PropertyValueFactory<>("emailProveedor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colCodigoP.setCellValueFactory(new PropertyValueFactory<>("codigoProveedor"));
    }

    public void seleccionarElementos() {
        txtCodigoEmail.setText(String.valueOf(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
        txtEmailP.setText(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getEmailProveedor());
        txtDescripcion.setText(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getDescripcion());
        cmbCodProveedor.getSelectionModel().select(buscarProveedores(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
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

    @FXML
    public void handleButtonAction(ActionEvent event) {
        /**
         * Si el evento es igual al boton regresar muestra el menu principal.
         */
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuProveedorView();
        }
    }

    public void activarControles() {
        txtCodigoEmail.setEditable(true);
        txtEmailP.setEditable(true);
        txtDescripcion.setEditable(true);
        cmbCodProveedor.setDisable(false);
    }

    public void desactivarControles() {
        txtCodigoEmail.setEditable(false);
        txtEmailP.setEditable(false);
        txtDescripcion.setEditable(false);
        cmbCodProveedor.setDisable(true);
    }

    public void limpiarControles() {
        txtCodigoEmail.clear();
        txtEmailP.clear();
        txtDescripcion.clear();
        tblEmailProveedor.getSelectionModel().getSelectedItem();
        cmbCodProveedor.getSelectionModel().getSelectedItem();
    }

    public void guardar() {
        EmailProveedor registro = new EmailProveedor();
        registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEmail.getText()));
        registro.setEmailProveedor(txtEmailP.getText());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCodProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_agregarEmailProveedor(?,?,?,?)");
            p.setInt(1, registro.getCodigoEmailProveedor());
            p.setString(2, registro.getEmailProveedor());
            p.setString(3, registro.getDescripcion());
            p.setInt(4, registro.getCodigoProveedor());
            p.execute();
            listarEmailProveedor.add(registro);
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
                if (tblEmailProveedor.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminación del registro", "Eliminar Email Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarEmailProveedor(?);");
                            procedimiento.setInt(1, ((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
                            procedimiento.execute();
                            listarEmailProveedor.remove(tblEmailProveedor.getSelectionModel().getSelectedItem());
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
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarEmailProveedor(?,?,?,?);");
            EmailProveedor registro = (EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem();

            registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEmail.getText()));
            registro.setEmailProveedor(txtEmailP.getText());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCodProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());

            p.setInt(1, registro.getCodigoEmailProveedor());
            p.setString(2, registro.getEmailProveedor());
            p.setString(3, registro.getDescripcion());
            p.setInt(4, registro.getCodigoProveedor());
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
                if (tblEmailProveedor.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/devyntubac/images/actualizarIcono.png"));
                    imgReporte.setImage(new Image("/org/devyntubac/images/cancelarIcono.png"));
                    activarControles();
                    txtCodigoEmail.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Email para Actualizar");
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
    
    public void buscarEmailProveedor(KeyEvent event){
        String filtroEmailProveedor = txtBuscar.getText();
        if(filtroEmailProveedor.isEmpty()){
            tblEmailProveedor.setItems(listarEmailProveedor);
        }else{
            buscarEmailProveedor.clear();
            for(EmailProveedor c : listarEmailProveedor){
                if(String.valueOf(c.getCodigoEmailProveedor()).equals(filtroEmailProveedor) || c.getEmailProveedor().toLowerCase().contains(filtroEmailProveedor) || String.valueOf(c.getCodigoProveedor()).equals(filtroEmailProveedor)){
                    buscarEmailProveedor.add(c);
                }
            }
            tblEmailProveedor.setItems(buscarEmailProveedor);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCodProveedor.setItems(getProveedores());
        buscarEmailProveedor = FXCollections.observableArrayList();
    }

}
