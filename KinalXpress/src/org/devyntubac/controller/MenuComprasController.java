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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.devyntubac.bean.Compras;
import org.devyntubac.db.Conexion;
import org.devyntubac.system.Main;

/**
 * FXML Controller class
 *
 * @author Devyn Orlando Tubac Gomez Carne: 2020247 Codigo Tecnico: IN5BM Fecha
 * de Creación: 10/04/2024 Fecha de Modificaciones: 24/04/2024
 */
public class MenuComprasController implements Initializable {

    private Main escenarioPrincipal;
    /**
     * ID de los elementos utilizados en la interfaz.
     */
    @FXML
    private TextField txtNumeroDocumento;

    @FXML
    private DatePicker dateFechaDocumento;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtTotalDocumento;

    @FXML
    private TableView tblCompras;

    @FXML
    private TableColumn colNumeroD;

    @FXML
    private TableColumn colFechaD;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colTotal;

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
    private Button btnRegresar;

    /**
     * ObservableList para enlistar los datos.
     */
    private ObservableList<Compras> listarCompras;

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
     * Este metodo desactiva los textField y el datePicker.
     */
    public void desactivarControles() {
        txtNumeroDocumento.setEditable(false);
        dateFechaDocumento.setEditable(false);
        txtDescripcion.setEditable(false);
        txtTotalDocumento.setEditable(false);
    }

    /**
     * Este metodo activa los textField y el datePicker.
     */
    public void activarControles() {
        txtNumeroDocumento.setEditable(true);
        dateFechaDocumento.setEditable(true);
        txtDescripcion.setEditable(true);
        txtTotalDocumento.setEditable(true);
    }

    /**
     * Este metodo limpia los textField y el datePicker .
     */
    public void limpiarControles() {
        txtNumeroDocumento.clear();
        dateFechaDocumento.setValue(null);
        txtDescripcion.clear();
        txtTotalDocumento.clear();
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
        /**
         * Esta sección del código asigna un valor a la variable fechaDocumento
         * de tipo Date de mysql verifica si el control dtePicker llamado
         * dateFechaDocumento tiene un valor seleccionado, Si hay un valor
         * seleccionado, convierte ese valor a Date de mysql utilizando el
         * método 'valueOf()' y lo asigna a la variable, De lo contrario, la
         * variable permanece como null.
         */
        java.sql.Date fechaDocumento = null;
        if (dateFechaDocumento.getValue() != null) {
            fechaDocumento = java.sql.Date.valueOf(dateFechaDocumento.getValue());
        }
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

    /**
     * Carga los datos en una tabla de clientes en la interfaz de usuario.
     */
    public void cargarDatos() {
        tblCompras.setItems(getCompras());
        colNumeroD.setCellValueFactory(new PropertyValueFactory<>("numeroDocumento"));
        colFechaD.setCellValueFactory(new PropertyValueFactory<>("fechaDocumento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalDocumento"));
    }

    /**
     * Este metodo tiene la funcionalidad de guardar los datos, y a su vez
     * prepara y ejecuta el procedimiento agregar de la base de datos.
     */
    public void guardar() {
        /**
         * Esta sección del código asigna un valor a la variable fechaDocumento
         * de tipo Date de mysql verifica si el control dtePicker llamado
         * dateFechaDocumento tiene un valor seleccionado, Si hay un valor
         * seleccionado, convierte ese valor a Date de mysql utilizando el
         * método 'valueOf()' y lo asigna a la variable, De lo contrario, la
         * variable permanece como null.
         */
        java.sql.Date fechaDocumento = null;
        if (dateFechaDocumento.getValue() != null) {
            fechaDocumento = java.sql.Date.valueOf(dateFechaDocumento.getValue());
        }
        /**
         * Crear un nuevo objeto y asigna los valores de los campos de entrada
         * de texto.
         */
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumento.getText()));
        registro.setFechaDocumento(fechaDocumento);
        registro.setDescripcion(txtDescripcion.getText());
        registro.setTotalDocumento(Double.parseDouble(txtTotalDocumento.getText()));
        try {
            PreparedStatement ps = Conexion.getInstance().getConexion().prepareCall("call sp_agregarCompras(?,?,?,?);");
            /**
             * Establece los parámetros del procedimiento con los valores del
             * objeto Cliente.
             */
            ps.setInt(1, registro.getNumeroDocumento());
            ps.setDate(2, registro.getFechaDocumento());
            ps.setString(3, registro.getDescripcion());
            ps.setDouble(4, registro.getTotalDocumento());
            ps.execute();
            listarCompras.add(registro);
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
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminación del registro", "Eliminar Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_eliminarCompras(?);");
                            p.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            p.execute();
                            listarCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar una Compra para Eliminar");
                }
                break;
        }
    }

    /**
     * Este metodo actualiza la información en la base de datos con los nuevos valores ingresados en la interfaz.
     */
    public void actualizar() {
        /**
         * Esta sección del código asigna un valor a la variable fechaDocumento
         * de tipo Date de mysql verifica si el control dtePicker llamado
         * dateFechaDocumento tiene un valor seleccionado, Si hay un valor
         * seleccionado, convierte ese valor a Date de mysql utilizando el
         * método 'valueOf()' y lo asigna a la variable, De lo contrario, la
         * variable permanece como null.
         */
        java.sql.Date fechaDocumento = null;
        if (dateFechaDocumento.getValue() != null) {
            fechaDocumento = java.sql.Date.valueOf(dateFechaDocumento.getValue());
        }
        try {
            /**
             * Prepara y ejecuta el procedimiento de la base de datos.
             */
            PreparedStatement p = Conexion.getInstance().getConexion().prepareCall("call sp_actualizarCompras(?,?,?,?);");
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();

            /**
             * Actualizar los datos del cliente con los valores ingresados.
            */
            registro.setFechaDocumento(fechaDocumento);
            registro.setDescripcion(txtDescripcion.getText());
            registro.setTotalDocumento(Double.parseDouble(txtTotalDocumento.getText()));
            /**
             * Establece los parámetros del procedimiento con los nuevos valores.
             */
            p.setInt(1, registro.getNumeroDocumento());
            p.setDate(2, registro.getFechaDocumento());
            p.setString(3, registro.getDescripcion());
            p.setDouble(4, registro.getTotalDocumento());
            p.execute();
        } catch (Exception e) {
            e.printStackTrace();
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
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/devyntubac/images/actualizarIcono.png"));
                    imgReporte.setImage(new Image("/org/devyntubac/images/cancelarIcono.png"));
                    activarControles();
                    txtNumeroDocumento.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de Seleccionar una Compra para Actualizar");
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

    private void mostrarFechaSeleccionada() {
        Compras compraSeleccionada = (Compras) tblCompras.getSelectionModel().getSelectedItem();
        if (compraSeleccionada != null) {
            dateFechaDocumento.setValue(compraSeleccionada.getFechaDocumento().toLocalDate());
        }
    }

    public void seleccionarElementos() {
        Compras compraSeleccionada = (Compras) tblCompras.getSelectionModel().getSelectedItem();
        if (compraSeleccionada != null) {
            txtNumeroDocumento.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
            dateFechaDocumento.setValue(compraSeleccionada.getFechaDocumento().toLocalDate());
            txtDescripcion.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion());
            txtTotalDocumento.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

}
