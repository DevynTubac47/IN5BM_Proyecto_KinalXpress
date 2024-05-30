package org.devyntubac.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.devyntubac.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class ViewUbicacionesController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    private Button btnRegresar;
    @FXML
    private WebView imgMap;
    @FXML
    private Label lbDireccion;
    @FXML
    private Label lbNumero;
    @FXML
    private ChoiceBox<String> chbZona;
    private String[] zonaGuatemala = {"Zona 1", "Zona 2", "Zona 4", "Zona 7"};
    private String direccionMapa;
    private String numero;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        direccionMapa = "https://www.google.com/maps/@14.6352173,-90.5193861,14.29z?entry=ttu";
        WebEngine webEngine = imgMap.getEngine();
        webEngine.load(direccionMapa);

        chbZona.getItems().addAll(zonaGuatemala);
        chbZona.setOnAction(this::direccionTienda);
    }

    public void direccionTienda(ActionEvent event) {
        String zona = chbZona.getValue();
        if (zona != null && zona.equals("Zona 1")) {
            lbDireccion.setText("Kinal Xpress Centro, 6 Calle 5-41" + "\n" + zona);
            direccionMapa = "https://www.google.com/maps/place/Supermercado+La+Torre+Centro,+zona+1/@14.6426962,-90.5149841,17z/data=!4m6!3m5!1s0x8589a210f2776007:0x3919ed806e86bfff!8m2!3d14.6428596!4d-90.5149587!16s%2Fg%2F1hfgwls1j?entry=ttu";
            lbNumero.setText("1010-0001");
        }else if (zona != null && zona.equals("Zona 2")) {
            lbDireccion.setText("Kinal Xpress Asunción, 4 Calle 6-21" + "\n" + zona);
            direccionMapa = "https://www.google.com/maps/place/Supermercado+La+Torre+Sime%C3%B3n+Ca%C3%B1as,+zona+2/@14.65069,-90.5117281,17.01z/data=!4m6!3m5!1s0x8589a20ec2e4841f:0xa778032523b23a6e!8m2!3d14.6510549!4d-90.5132275!16s%2Fg%2F11bbwn_r5g?entry=ttu";
            lbNumero.setText("1010-0002");
        }else if (zona != null && zona.equals("Zona 4")) {
            lbDireccion.setText("Kinal Xpress 4 Grados, 7 Avenida 2-26" + "\n" + zona);
            direccionMapa = "https://www.google.com/maps/place/14%C2%B037'19.6%22N+90%C2%B030'54.4%22W/@14.6223648,-90.5171069,17z/data=!4m4!3m3!8m2!3d14.622111!4d-90.515119?entry=ttu";
            lbNumero.setText("1010-0003");
        }else if (zona != null && zona.equals("Zona 7")) {
            lbDireccion.setText("Kinal Xpress Fundación, 6 Avenida 13-54" + "\n" + zona);
            direccionMapa = "https://www.google.com/maps/place/Centro+Educativo+T%C3%A9cnico+Laboral+KINAL/@14.624562,-90.5360059,17z/data=!4m6!3m5!1s0x8589a190308858f1:0x6795822b2b81d4ac!8m2!3d14.6257038!4d-90.5358571!16s%2Fg%2F11b5ys19jy?entry=ttu";
            lbNumero.setText("1010-0004");
        } else {
            lbDireccion.setText(zona);
        }
        WebEngine webEngine = imgMap.getEngine();
        webEngine.load(direccionMapa);
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
