package org.devyntubac.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.devyntubac.bean.Usuario;
import org.devyntubac.system.Main;

/**
 * FXML Controller class
 *
 * @author devyn
 */
public class MenuCerrarSesionController implements Initializable {

    @FXML
    private Button btnSi;

    @FXML
    private Button btnNo;
    
    @FXML
    private Label lbNombreU;

    private Main escenarioPrincipal;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void nombreUsuario(){
        String nombre = SesionIniciada.usuarioActual();
        if(nombre != null){
            lbNombreU.setText(nombre);
        }else{
            lbNombreU.setText("Nombre de Usuario no disponble!!!");
        }
    }
    
    @FXML
    public void cerrarSesion(ActionEvent event) {
        if (event.getSource() == btnSi) {
            SesionIniciada.establecerSesion(false);
            SesionIniciada.obtenerUsuario(null);
            SesionIniciada.guardarEstado();
            Stage stage = (Stage) btnSi.getScene().getWindow();
            stage.close();
            File archivo = new File("C:\\Users\\devyn\\OneDrive\\Desktop\\Repositorio_Casa\\IN5BM_Proyecto_KinalXpress\\src\\org\\devyntubac\\images\\sonidoCerrar.mp3");
            Media audio = new Media(archivo.toURI().toString());
            MediaPlayer reproductor = new MediaPlayer(audio);
            reproductor.play();
            escenarioPrincipal.inicioSesion();
        }
        if (event.getSource() == btnNo) {
            Stage stage = (Stage) btnNo.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreUsuario();
    }
}
