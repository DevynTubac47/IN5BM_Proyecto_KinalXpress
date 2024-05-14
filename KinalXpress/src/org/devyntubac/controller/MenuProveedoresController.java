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
import javax.swing.JOptionPane;
import org.devyntubac.bean.Proveedores;
import org.devyntubac.db.Conexion;
import org.devyntubac.system.Main;

/**
 * FXML Controller class
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 29/04/2024
 */
public class MenuProveedoresController implements Initializable {

    private Main escenarioPrincipal;
    /**
     * ID de los elementos utilizados en la interfaz.
     */
    @FXML
    Button btnRegresar;
    @FXML
    private TableView tblProveedores;
    @FXML
    private TableColumn colCodigoP;
    @FXML
    private TableColumn colNitP;
    @FXML
    private TableColumn colNombreP;
    @FXML
    private TableColumn colApellidoP;
    @FXML
    private TableColumn colDireccionP;
    @FXML
    private TableColumn colRazonSocial;
    @FXML
    private TableColumn colContactoPrincipal;
    @FXML
    private TableColumn colPaginaWeb;
    @FXML
    private TextField txtCodigoP;
    @FXML
    private TextField txtNitP;
    @FXML
    private TextField txtNombreP;
    @FXML
    private TextField txtApellidoP;
    @FXML
    private TextField txtDireccionP;
    @FXML
    private TextField txtRazonSocialP;
    @FXML
    private TextField txtContactoPrincipal;
    @FXML
    private TextField txtPaginaWeb;
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
    /**
     * ObservableList para enlistar los datos.
     */
    private ObservableList<Proveedores> listarProveedores;

    /**
     * Enumeradores para las operaciones que se utilizaran en el programa.
     */
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, CANCELAR, ACTUALIZAR, NINGUNO
    }
    /**
     * Variable que indica el tipo de operación actual.
     */
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    /**
     * Getter y Setter
     */
    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    /**
     * Inizializa la clase controller, con el metodo cargarDatos.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    /**
     * Este metodo tiene la función de listar los datos, y a su vez prepara y
     * ejecuta el procedimiento de listar de la base de datos.
     *
     * @return La lista de Proveedores
     */
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

    /**
     * Carga los datos en una tabla de clientes en la interfaz de usuario.
     */
    public void cargarDatos() {
        tblProveedores.setItems(getProveedores());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<>("codigoProveedor"));
        colNitP.setCellValueFactory(new PropertyValueFactory<>("NITProvedor"));
        colNombreP.setCellValueFactory(new PropertyValueFactory<>("nombresProveedor"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory<>("apellidosProveedor"));
        colDireccionP.setCellValueFactory(new PropertyValueFactory<>("direccionProveedor"));
        colRazonSocial.setCellValueFactory(new PropertyValueFactory<>("razonSocial"));
        colContactoPrincipal.setCellValueFactory(new PropertyValueFactory<>("contactoPrincipal"));
        colPaginaWeb.setCellValueFactory(new PropertyValueFactory<>("paginaWeb"));
    }

    /**
     * Este metodo activa los textField.
     */
    public void activarControles() {
        txtCodigoP.setEditable(true);
        txtNitP.setEditable(true);
        txtNombreP.setEditable(true);
        txtApellidoP.setEditable(true);
        txtDireccionP.setEditable(true);
        txtRazonSocialP.setEditable(true);
        txtContactoPrincipal.setEditable(true);
        txtPaginaWeb.setEditable(true);
    }

    /**
     * Este metodo desactiva los textField.
     */
    public void desactivarControles() {
        txtCodigoP.setEditable(false);
        txtNitP.setEditable(false);
        txtNombreP.setEditable(false);
        txtApellidoP.setEditable(false);
        txtDireccionP.setEditable(false);
        txtRazonSocialP.setEditable(false);
        txtContactoPrincipal.setEditable(false);
        txtPaginaWeb.setEditable(false);
    }

    /**
     * Este metodo limpia los textField.
     */
    public void limpiarControles() {
        txtCodigoP.clear();
        txtNitP.clear();
        txtNombreP.clear();
        txtApellidoP.clear();
        txtDireccionP.clear();
        txtRazonSocialP.clear();
        txtContactoPrincipal.clear();
        txtPaginaWeb.clear();
    }

    /**
     * Este metodo sirve para que los datos de la tupla, se coloquen en los
     * textField.
     */
    public void seleccionarElementos() {
        txtCodigoP.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNitP.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNITProvedor());
        txtNombreP.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNombresProveedor());
        txtApellidoP.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getApellidosProveedor());
        txtDireccionP.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
        txtRazonSocialP.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial());
        txtContactoPrincipal.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal());
        txtPaginaWeb.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb());
    }

    /**
     * Este metodo realiza la funcion para agregar los datos, Dependiendo del
     * tipo de operación actual, activa o desactiva los controles
     * correspondientes, actualiza el texto y la apariencia de los botones, y
     * realiza acciones específicas para agregar o guardar datos.
     *
     */
    public void Agregar() {
        switch (tipoDeOperaciones) {
            /**
             * Si el tipo de Operaciones es NINGUNO, se activan los controles y
             * cambia la apariencia del boton.
             */
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
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

    /**
     * Este metodo tiene la funcionalidad de guardar los datos, y a su vez
     * prepara y ejecuta el procedimiento agregar de la base de datos.
     */
    public void guardar() {
        /**
         * Crear un nuevo objeto y asigna los valores de los campos de entrada
         * de texto.
         */
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
        registro.setNITProvedor(txtNitP.getText());
        registro.setNombresProveedor(txtNombreP.getText());
        registro.setApellidosProveedor(txtApellidoP.getText());
        registro.setDireccionProveedor(txtDireccionP.getText());
        registro.setRazonSocial(txtRazonSocialP.getText());
        registro.setContactoPrincipal(txtContactoPrincipal.getText());
        registro.setPaginaWeb(txtPaginaWeb.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarProveedores(?,?,?,?,?,?,?,?);");
            /**
             * Establece los parámetros del procedimiento con los valores del
             * objeto Cliente.
             */
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProvedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
            listarProveedores.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo realiza la funcion de eliminar un registro, a su vez restaura
     * los controles y botones.
     */
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
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminación del registro", "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarProveedores(?);");
                            procedimiento.setInt(1, ((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listarProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Proveedor para Eliminar");
                }
                break;
        }
    }

    /**
     * Este metodo realiza la funcion de actualzar algun registro,  su vez 
     * restaura los controles y botones.
     */
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                /**
                 * Si tipo de operaciones es NINGUNO, primero verifica si hay registros 
                 * en la tabla y si no muestra un cuadro de dialogo para que el usuario
                 * selecciono algun registro.
                 */
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/devyntubac/images/actualizarIcono.png"));
                    imgReporte.setImage(new Image("/org/devyntubac/images/cancelarIcono.png"));
                    activarControles();
                    txtCodigoP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Proveedor para Actualizar");
                }
                break;
            case ACTUALIZAR:
                /**
                 * Si es ACTUALIZAR llama al metodo actualizar y restaura los botones y textField.
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

    /**
     * Este metodo actualiza la información en la base de datos con los nuevos valores ingresados en la interfaz.
     */
    public void actualizar() {
        try {
            /**
             * Prepara y ejecuta el procedimiento de la base de datos.
             */
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarProveedores(?,?,?,?,?,?,?,?);");
            Proveedores registro = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();
            /**
             * Actualizar los datos del cliente con los valores ingresados.
            */
            registro.setNITProvedor(txtNitP.getText());
            registro.setNombresProveedor(txtNombreP.getText());
            registro.setApellidosProveedor(txtApellidoP.getText());
            registro.setDireccionProveedor(txtDireccionP.getText());
            registro.setRazonSocial(txtRazonSocialP.getText());
            registro.setContactoPrincipal(txtContactoPrincipal.getText());
            registro.setPaginaWeb(txtPaginaWeb.getText());
            /**
             * Establece los parámetros del procedimiento con los nuevos valores.
             */
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProvedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo realiza la funcion de restaurar los botones a su estado original.
     */
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

    /**
     * Este metodo realiza la función para cada boton.
     * @param event recibe este parametro para realizar acción. 
     */
    @FXML
    public void handleButtonAction(ActionEvent event) {
        /**
         * Si el evento es igual al boton regresar muestra el menu principal.
         */
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
