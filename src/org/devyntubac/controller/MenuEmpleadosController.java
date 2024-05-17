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
import javax.swing.JOptionPane;
import org.devyntubac.bean.CargoEmpleado;
import org.devyntubac.bean.Empleados;
import org.devyntubac.db.Conexion;
import org.devyntubac.system.Main;

/**
 * FXML Controller class
 *
 * @author devyn
 */
public class MenuEmpleadosController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    Button btnRegresar;
    @FXML
    private TableView tblEmpleados;

    @FXML
    private TableColumn colCodigoE;

    @FXML
    private TableColumn colNombresE;

    @FXML
    private TableColumn colApellidosE;

    @FXML
    private TableColumn colSueldoE;

    @FXML
    private TableColumn colDireccionE;

    @FXML
    private TableColumn colTurnoE;

    @FXML
    private TableColumn colCodCargoE;

    @FXML
    private TextField txtCodigoEmpleado;

    @FXML
    private TextField txtNombresE;

    @FXML
    private TextField txtApellidosE;

    @FXML
    private TextField txtSueldo;

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
    private TextField txtDireccionE;

    @FXML
    private TextField txtTurnoE;

    @FXML
    private ComboBox cbmCodCargoEmpleado;

    private ObservableList<Empleados> listarEmpleados;
    private ObservableList<CargoEmpleado> listarCargoEmpleado;

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

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<>();
        ResultSet resultado = null;
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_listarEmpleados();");
            resultado = p.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarEmpleados = FXCollections.observableList(lista);
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

    public void cargarDatos() {
        tblEmpleados.setItems(getEmpleados());
        colCodigoE.setCellValueFactory(new PropertyValueFactory<>("codigoEmpleado"));
        colNombresE.setCellValueFactory(new PropertyValueFactory<>("nombresEmpleado"));
        colApellidosE.setCellValueFactory(new PropertyValueFactory<>("apellidosEmpleado"));
        colSueldoE.setCellValueFactory(new PropertyValueFactory<>("sueldo"));
        colDireccionE.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTurnoE.setCellValueFactory(new PropertyValueFactory<>("turno"));
        colCodCargoE.setCellValueFactory(new PropertyValueFactory<>("codigoCargoEmpleado"));
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

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void desactivarControles() {
        txtCodigoEmpleado.setEditable(false);
        txtNombresE.setEditable(false);
        txtApellidosE.setEditable(false);
        txtSueldo.setEditable(false);
        txtDireccionE.setEditable(false);
        txtTurnoE.setEditable(false);
        cbmCodCargoEmpleado.setDisable(true);
    }

    public void activarControles() {
        txtCodigoEmpleado.setEditable(true);
        txtNombresE.setEditable(true);
        txtApellidosE.setEditable(true);
        txtSueldo.setEditable(true);
        txtDireccionE.setEditable(true);
        txtTurnoE.setEditable(true);
        cbmCodCargoEmpleado.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoEmpleado.clear();
        txtNombresE.clear();
        txtApellidosE.clear();
        txtSueldo.clear();
        txtDireccionE.clear();
        txtTurnoE.clear();
        cbmCodCargoEmpleado.getSelectionModel().getSelectedItem();
    }

    public void guardar() {
        Empleados registro = new Empleados();
        registro.setCodigoEmpleado(Integer.parseInt(txtCodigoEmpleado.getText()));
        registro.setNombresEmpleado(txtNombresE.getText());
        registro.setApellidosEmpleado(txtApellidosE.getText());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setDireccion(txtDireccionE.getText());
        registro.setTurno(txtTurnoE.getText());
        registro.setCodigoCargoEmpleado(((CargoEmpleado) cbmCodCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_agregarEmpleados(?,?,?,?,?,?,?);");
            p.setInt(1, registro.getCodigoEmpleado());
            p.setString(2, registro.getNombresEmpleado());
            p.setString(3, registro.getApellidosEmpleado());
            p.setDouble(4, registro.getSueldo());
            p.setString(5, registro.getDireccion());
            p.setString(6, registro.getTurno());
            p.setInt(7, registro.getCodigoCargoEmpleado());
            p.execute();
            listarEmpleados.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seleccionarElementos() {
        txtCodigoEmpleado.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNombresE.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado());
        txtApellidosE.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
        txtSueldo.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
        txtDireccionE.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion());
        txtTurnoE.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno());
        cbmCodCargoEmpleado.getSelectionModel().select(buscarCargoEmpleado(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
    }

    public CargoEmpleado buscarCargoEmpleado(int codigoCargoEmpleado) {
        CargoEmpleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_buscarCargoEmpleado(?);");
            procedimiento.setInt(1, codigoCargoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new CargoEmpleado(registro.getInt("codigoCargoEmpleado"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo"));
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminación del registro", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarEmpleados(?);");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listarEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Empleado para Eliminar");
                }
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarEmpleados(?,?,?,?,?,?,?);");
            Empleados registro = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();

            registro.setNombresEmpleado(txtNombresE.getText());
            registro.setApellidosEmpleado(txtApellidosE.getText());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDireccion(txtDireccionE.getText());
            registro.setTurno(txtTurnoE.getText());
            registro.setCodigoCargoEmpleado(((CargoEmpleado) cbmCodCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());

            p.setInt(1, registro.getCodigoEmpleado());
            p.setString(2, registro.getNombresEmpleado());
            p.setString(3, registro.getApellidosEmpleado());
            p.setDouble(4, registro.getSueldo());
            p.setString(5, registro.getDireccion());
            p.setString(6, registro.getTurno());
            p.setInt(7, registro.getCodigoCargoEmpleado());
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/devyntubac/images/actualizarIcono.png"));
                    imgReporte.setImage(new Image("/org/devyntubac/images/cancelarIcono.png"));
                    activarControles();
                    txtCodigoEmpleado.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar un Empleado para Actualizar");
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
        /**
         * Si el evento es igual al boton regresar muestra el menu principal.
         */
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cbmCodCargoEmpleado.setItems(getCargoEmpleado());
    }

}
