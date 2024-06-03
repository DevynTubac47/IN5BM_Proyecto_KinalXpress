package org.devyntubac.controller;

import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.devyntubac.db.Conexion;
import org.devyntubac.system.Main;

/**
 * FXML Controller class
 *
 * @author devyn
 */
public class MenuCrearCuentaController implements Initializable {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private PasswordField txtRepetir;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private Button btnRegresar;

    @FXML
    private Button btnCrearCuenta;

    @FXML
    private Label lbCorreo;

    @FXML
    private Label lbRepetir;

    @FXML
    private Label lbNombreU;

    private Main escenarioPrincipal;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void crearUsuario(String nombre, String apellido, String email, String contrasena, String telefono, String nombreUsuario) {
        Conexion c = null;
        PreparedStatement ps = null;
        PreparedStatement psConsulta = null;
        PreparedStatement psNombre = null;
        ResultSet rs = null;

        try {
            c = Conexion.getInstance();
            String consultaCorreo = "select count(*) from Usuario where email = ?;";
            psConsulta = c.getConexion().prepareStatement(consultaCorreo);
            psConsulta.setString(1, email);
            rs = psConsulta.executeQuery();
            rs.next();
            int cuenta = rs.getInt(1);

            if (cuenta > 0) {
                lbCorreo.setText("El correo ya existe!!!");
                return;
            }

            String consulta = "insert into Usuario(email,contrasenia,nombreU,apellidoU,telefono,nombreUsuario) values (?,?,?,?,?,?);";
            ps = c.getConexion().prepareStatement(consulta);
            ps.setString(1, email);
            ps.setString(2, contrasena);
            ps.setString(3, nombre);
            ps.setString(4, apellido);
            ps.setString(5, telefono);
            ps.setString(6, nombreUsuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void crearCuenta(ActionEvent event) {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String email = txtEmail.getText();
        String contrasena = txtContrasena.getText();
        String telefono = txtTelefono.getText();
        String nombreUsuario = txtNombreUsuario.getText();
        String repetirContrasena = txtRepetir.getText();

        if (!email.contains("@") && !email.contains(".")) {
            lbCorreo.setText("Correo Invalido!");
        } else if (contrasena.equals(repetirContrasena)) {
            crearUsuario(nombre, apellido, email, contrasena, telefono, nombreUsuario);
            File archivo = new File("C:\\Users\\devyn\\OneDrive\\Desktop\\Repositorio_Casa\\IN5BM_Proyecto_KinalXpress\\src\\org\\devyntubac\\images\\audioLogin.mp3");
            Media audio = new Media(archivo.toURI().toString());
            MediaPlayer reproductor = new MediaPlayer(audio);
            reproductor.play();
            escenarioPrincipal.menuPrincipalView();
        } else {
            lbRepetir.setText("Las contraseñas no coinciden. Por favor, inténtelo de nuevo.");
        }
    }

    private boolean nombreUsuarioExiste(String nombreUsuario) {
        boolean existe = false;
        try {
            Conexion c = Conexion.getInstance();
            PreparedStatement psNombre = null;
            ResultSet rs = null;

            String consultaNombre = "select count(*) from Usuario where nombreUsuario = ?;";
            psNombre = c.getConexion().prepareStatement(consultaNombre);
            psNombre.setString(1, nombreUsuario);
            rs = psNombre.executeQuery();

            if (rs.next()) {
                int cuentaU = rs.getInt(1);
                if (cuentaU > 0) {
                    existe = true;
                    lbNombreU.setText(consultaNombre);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuCrearCuentaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.inicioSesion();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtNombreUsuario.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Verifica si el nuevo valor del TextField no está vacío
                if (!newValue.isEmpty()) {
                    // Realiza una consulta para verificar si el nombre de usuario ya existe
                    if (nombreUsuarioExiste(newValue)) {
                        lbNombreU.setText("El nombre de usuario \nya existe!!!");
                    } else {
                        lbNombreU.setText("");
                    }
                } else {
                    lbNombreU.setText("");
                }
            }
        });
    }
}
