package org.devyntubac.controller;

import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.devyntubac.db.Conexion;
import org.devyntubac.system.Main;

/**
 * FXML Controller class
 *
 * @author devyn
 */
public class MenuLoginController implements Initializable {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnCrearCuenta;
    @FXML
    private Label lbIncorrecto;
    private Main escenarioPrincipal;
    private String incorrecto;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void eventKey(KeyEvent event) {
        Object evt = event.getSource();

        if (evt.equals(txtEmail)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        } else if (evt.equals(txtContrasenia)) {
            if (event.getCharacter().equals(" ")) {
                event.consume();
            }
        }
    }
    
    @FXML
    public void login() {
        String email = txtEmail.getText();
        String contrasena = txtContrasenia.getText();

        try {
            Conexion conexion = Conexion.getInstance();
            String consulta = "SELECT * FROM Usuario WHERE email = ? AND contrasenia = ?";
            PreparedStatement s = conexion.getConexion().prepareStatement(consulta);
            s.setString(1, email);
            s.setString(2, contrasena);
            ResultSet r = s.executeQuery();
            if (r.next()) {
                SesionIniciada.establecerSesion(true);
                try {
                    String nombreUsuario = r.getString("nombreUsuario");
                    SesionIniciada.obtenerUsuario(nombreUsuario);
                    File archivo = new File("C:\\Users\\devyn\\OneDrive\\Desktop\\Repositorio_Casa\\IN5BM_Proyecto_KinalXpress\\src\\org\\devyntubac\\images\\audioLogin.mp3");
                    Media audio = new Media(archivo.toURI().toString());
                    MediaPlayer reproductor = new MediaPlayer(audio);
                    reproductor.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                escenarioPrincipal.menuPrincipalView();
            } else {
                lbIncorrecto.setText("Correo y/o Contraseña Incorrecta");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        /**
         * Muestra la interfaz de Clientes.
         */
        if (event.getSource() == btnCrearCuenta) {
            escenarioPrincipal.menuCrearCuenta();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
