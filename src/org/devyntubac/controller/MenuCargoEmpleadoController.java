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
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import org.devyntubac.bean.CargoEmpleado;
import org.devyntubac.db.Conexion;
import org.devyntubac.system.Main;

/**
 * FXML Controller class
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 24/04/2024
 */
public class MenuCargoEmpleadoController implements Initializable {

    private Main escenarioPrincipal;
    /**
     * ID de los elementos utilizados en la interfaz.
     */
    @FXML
    Button btnRegresar;
    @FXML
    private TableView tblCargoEmpleado;
    @FXML
    private TextField txtBuscar;
    @FXML
    private TextField txtCodigoCargoE;
    @FXML
    private TextField txtNombreCargoE;
    @FXML
    private TextField txtDescripcionCargoE;
    @FXML
    private TableColumn colCodigoCargoE;
    @FXML
    private TableColumn colNombreCargoE;
    @FXML
    private TableColumn colDescripcionCargoE;
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
    private ObservableList<CargoEmpleado> listarCargoEmpleado;
    private ObservableList<CargoEmpleado> buscarCargoEmpleado;

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

    /**
     * Este metodo activa los textField.
     */
    public void activarControles() {
        txtCodigoCargoE.setEditable(true);
        txtNombreCargoE.setEditable(true);
        txtDescripcionCargoE.setEditable(true);
    }

    /**
     * Este metodo desactiva los textField.
     */
    public void desactivarControles() {
        txtCodigoCargoE.setEditable(false);
        txtNombreCargoE.setEditable(false);
        txtDescripcionCargoE.setEditable(false);
    }

    /**
     * Este metodo limpia los textField.
     */
    public void limpiarControles() {
        txtCodigoCargoE.clear();
        txtNombreCargoE.clear();
        txtDescripcionCargoE.clear();
    }

    /**
     * Este metodo tiene la función de listar los datos, y a su vez prepara y
     * ejecuta el procedimiento de listar de la base de datos.
     *
     * @return La lista de CargoEmpleado
     */
    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_listarCargoEmpleado();");
            resultado = procedimiento.executeQuery();
            /**
             * El bucle while agrega los datos a la lista.
             */
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarCargoEmpleado = FXCollections.observableList(lista);
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
        CargoEmpleado registro = new CargoEmpleado();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCargoE.getText()));
        registro.setNombreCargo(txtNombreCargoE.getText());
        registro.setDescripcionCargo(txtDescripcionCargoE.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_agregarCargoEmpleado(?,?,?);");
            /**
             * Establece los parámetros del procedimiento con los valores del
             * objeto Cliente.
             */
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
            listarCargoEmpleado.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            case ACTUALIZAR:
                /**
                 * Si el tipo de Operaciones es ACTUALIZAR, se llama al metodo
                 * guardar para realizar su respectiva función y desactiva y
                 * limpia los textField, y a su vez los botones vuelven a su
                 * apariencia normal.
                 */
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
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            /**
             * Si no procede a mostrar un cuadro de dialogo para confirmar la
             * eliminación, prepara y ejecuta el procedimiento eliminar de la
             * base de datos.
             */
            default:
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminación del registro", "Eliminar Cargo Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarCargoEmpleado(?);");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listarCargoEmpleado.remove(tblCargoEmpleado.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Cargo para Eliminar");
                }
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarCargoEmpleado(?,?,?);");
            CargoEmpleado registro = (CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem();

            /**
             * Actualizar los datos del cliente con los valores ingresados.
             */
            registro.setNombreCargo(txtNombreCargoE.getText());
            registro.setDescripcionCargo(txtDescripcionCargoE.getText());
            /**
             * Establece los parámetros del procedimiento con los nuevos
             * valores.
             */
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
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
                if (tblCargoEmpleado.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/devyntubac/images/actualizarIcono.png"));
                    imgReporte.setImage(new Image("/org/devyntubac/images/cancelarIcono.png"));
                    activarControles();
                    txtCodigoCargoE.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Cargo para Actualizar");
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
     * Carga los datos en una tabla de CargoEmpleado en la interfaz de usuario.
     */
    public void cargarDatos() {
        tblCargoEmpleado.setItems(getCargoEmpleado());
        colCodigoCargoE.setCellValueFactory(new PropertyValueFactory<>("codigoCargoEmpleado"));
        colNombreCargoE.setCellValueFactory(new PropertyValueFactory<>("nombreCargo"));
        colDescripcionCargoE.setCellValueFactory(new PropertyValueFactory<>("descripcionCargo"));
    }

    /**
     * Este metodo sirve para que los datos de la tupla, se coloquen en los
     * textField.
     */
    public void seleccionarElementos() {
        txtCodigoCargoE.setText(String.valueOf(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNombreCargoE.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescripcionCargoE.setText(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getDescripcionCargo());
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

    public void buscarCargoEmpleado(KeyEvent event){
        String filtrarCargo = txtBuscar.getText();
        if(filtrarCargo.isEmpty()){
            tblCargoEmpleado.setItems(listarCargoEmpleado);
        }else{
            buscarCargoEmpleado.clear();
            for(CargoEmpleado c: listarCargoEmpleado){
                if(String.valueOf(c.getCodigoCargoEmpleado()).equals(filtrarCargo) || c.getNombreCargo().toLowerCase().contains(filtrarCargo)){
                    buscarCargoEmpleado.add(c);
                }
            }
            tblCargoEmpleado.setItems(buscarCargoEmpleado);
            
        }
    }
    
    /**
     * Inizializa la clase controller, con el metodo cargarDatos.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        buscarCargoEmpleado = FXCollections.observableArrayList();
    }

}
