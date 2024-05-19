package org.devyntubac.controller;

import javafx.scene.input.KeyEvent;
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
import org.devyntubac.bean.Clientes;
import org.devyntubac.db.Conexion;
import org.devyntubac.system.Main;

/**
 * FXML Controller class
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 24/04/2024
 */
public class MenuClientesController implements Initializable {

    /**
     * ID de los elementos utilizados en la interfaz.
     */
    @FXML
    private TextField txtcodigoCliente;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TextField txtNombreCliente;
    @FXML
    private TextField txtApellidoCliente;
    @FXML
    private TextField txtNitCliente;
    @FXML
    private TextField txtTelefonoCliente;
    @FXML
    private TextField txtDireccionCliente;
    @FXML
    private TextField txtCorreoCliente;
    @FXML
    private TableView tblClientes;
    @FXML
    private TableColumn colClienteID;
    @FXML
    private TableColumn colNombresCliente;
    @FXML
    private TableColumn colApellidosCliente;
    @FXML
    private TableColumn colNitClientes;
    @FXML
    private TableColumn colTelefonoCliente;
    @FXML
    private TableColumn colDireccionCliente;
    @FXML
    private TableColumn colCorreoCliente;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    /**
     * ObservableList para enlistar los datos.
     */
    private ObservableList<Clientes> listarClientes;
    private ObservableList<Clientes> buscarCliente;
    private Main escenarioPrincipal;

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
        buscarCliente = FXCollections.observableArrayList();
    }

    /**
     * Carga los datos en una tabla de clientes en la interfaz de usuario.
     */
    public void cargarDatos() {
        tblClientes.setItems(getClientes());
        colClienteID.setCellValueFactory(new PropertyValueFactory<>("clienteID"));
        colNombresCliente.setCellValueFactory(new PropertyValueFactory<>("nombresCliente"));
        colApellidosCliente.setCellValueFactory(new PropertyValueFactory<>("apellidosCliente"));
        colNitClientes.setCellValueFactory(new PropertyValueFactory<>("NITClientes"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory<>("direccionCliente"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<>("telefonoCliente"));
        colCorreoCliente.setCellValueFactory(new PropertyValueFactory<>("correoCliente"));
    }

    /**
     * Este metodo sirve para que los datos de la tupla, se coloquen en los
     * textField.
     */
    public void seleccionarElementos() {
        txtcodigoCliente.setText(String.valueOf(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getClienteID()));
        txtNombreCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombresCliente());
        txtApellidoCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getApellidosCliente());
        txtNitCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNITClientes());
        txtDireccionCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtTelefonoCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtCorreoCliente.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());
    }

    /**
     * Este metodo tiene la función de listar los datos, y a su vez prepara y
     * ejecuta el procedimiento de listar de la base de datos.
     *
     * @return La lista de Clientes
     */
    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarClientes();");
            resultado = procedimiento.executeQuery();
            /**
             * El bucle while agrega los datos a la lista.
             */
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("clienteID"),
                        resultado.getString("nombresCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("NITClientes"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarClientes = FXCollections.observableList(lista);
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
        Clientes registro = new Clientes();
        registro.setClienteID(Integer.parseInt(txtcodigoCliente.getText()));
        registro.setNombresCliente(txtNombreCliente.getText());
        registro.setApellidosCliente(txtApellidoCliente.getText());
        registro.setNITClientes(txtNitCliente.getText());
        registro.setDireccionCliente(txtDireccionCliente.getText());
        registro.setTelefonoCliente(txtTelefonoCliente.getText());
        registro.setCorreoCliente(txtCorreoCliente.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarClientes(?,?,?,?,?,?,?);");
            /**
             * Establece los parámetros del procedimiento con los valores del
             * objeto Cliente.
             */
            procedimiento.setInt(1, registro.getClienteID());
            procedimiento.setString(2, registro.getNombresCliente());
            procedimiento.setString(3, registro.getApellidosCliente());
            procedimiento.setString(4, registro.getNITClientes());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listarClientes.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
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
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminación del registro", "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getClienteID());
                            procedimiento.execute();
                            listarClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Cliente para Eliminar");
                }
                break;
        }
    }

    /**
     * Este metodo realiza la funcion de actualzar algun registro, su vez
     * restaura los controles y botones.
     */
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                /**
                 * Si tipo de operaciones es NINGUNO, primero verifica si hay
                 * registros en la tabla y si no muestra un cuadro de dialogo
                 * para que el usuario selecciono algun registro.
                 */
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/devyntubac/images/actualizarIcono.png"));
                    imgReporte.setImage(new Image("/org/devyntubac/images/cancelarIcono.png"));
                    activarControles();
                    txtcodigoCliente.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Cliente para Actualizar");
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

    /**
     * Este metodo actualiza la información en la base de datos con los nuevos
     * valores ingresados en la interfaz.
     */
    public void actualizar() {
        try {
            /**
             * Prepara y ejecuta el procedimiento de la base de datos.
             */
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarClientes(?,?,?,?,?,?,?);");
            Clientes registro = (Clientes) tblClientes.getSelectionModel().getSelectedItem();

            /**
             * Actualizar los datos del cliente con los valores ingresados.
             */
            registro.setNombresCliente(txtNombreCliente.getText());
            registro.setApellidosCliente(txtApellidoCliente.getText());
            registro.setNITClientes(txtNitCliente.getText());
            registro.setDireccionCliente(txtDireccionCliente.getText());
            registro.setTelefonoCliente(txtTelefonoCliente.getText());
            registro.setCorreoCliente(txtCorreoCliente.getText());
            /**
             * Establece los parámetros del procedimiento con los nuevos
             * valores.
             */
            procedimiento.setInt(1, registro.getClienteID());
            procedimiento.setString(2, registro.getNombresCliente());
            procedimiento.setString(3, registro.getApellidosCliente());
            procedimiento.setString(4, registro.getNITClientes());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo realiza la funcion de restaurar los botones a su estado
     * original.
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
     * Este metodo desactiva los textField.
     */
    public void desactivarControles() {
        txtcodigoCliente.setEditable(false);
        txtNombreCliente.setEditable(false);
        txtApellidoCliente.setEditable(false);
        txtNitCliente.setEditable(false);
        txtTelefonoCliente.setEditable(false);
        txtDireccionCliente.setEditable(false);
        txtCorreoCliente.setEditable(false);
    }

    /**
     * Este metodo activa los textField.
     */
    public void activarControles() {
        txtcodigoCliente.setEditable(true);
        txtNombreCliente.setEditable(true);
        txtApellidoCliente.setEditable(true);
        txtNitCliente.setEditable(true);
        txtTelefonoCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtCorreoCliente.setEditable(true);
    }

    /**
     * Este metodo limpia los textField.
     */
    public void limpiarControles() {
        txtcodigoCliente.clear();
        txtNombreCliente.clear();
        txtApellidoCliente.clear();
        txtNitCliente.clear();
        txtTelefonoCliente.clear();
        txtDireccionCliente.clear();
        txtCorreoCliente.clear();
    }

    public void buscarClienteNombre(KeyEvent event) {
        String filtroNombre = txtBuscar.getText();
        if (filtroNombre.isEmpty()) {
            tblClientes.setItems(listarClientes);
        } else {
            buscarCliente.clear();
            for (Clientes c : listarClientes) {
                if (String.valueOf(c.getClienteID()).equals(filtroNombre) || c.getNombresCliente().toLowerCase().contains(filtroNombre.toLowerCase()) || c.getApellidosCliente().toLowerCase().contains(filtroNombre.toLowerCase())) {
                    buscarCliente.add(c);
                }
            }

            tblClientes.setItems(buscarCliente);
        }

    }

    /**
     * Este metodo realiza la función para cada boton.
     *
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
