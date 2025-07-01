/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteziapp.ui;

import inteziapp.util.Navegador;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.List;
import javafx.stage.Stage;

public class ConfiguracionUIController {

    @FXML private Label lblPerfil, lblConfiguracion, lblPrivacidad, lblSeguridad, lblLicencias, lblUsuario;
    @FXML private VBox panelContenido;

    private List<Label> menuLabels;
    private String usuario;
    private Stage stage;
    
    public void inicializar(String usuario, Stage stage) {
        this.usuario = usuario;
        this.stage = stage;
        
        menuLabels = List.of(lblPerfil, lblConfiguracion, lblPrivacidad, lblSeguridad, lblLicencias);

        setupMenu(lblPerfil, "Perfil");
        setupMenu(lblConfiguracion, "Configuración");
        setupMenu(lblPrivacidad, "Privacidad");
        setupMenu(lblSeguridad, "Seguridad");
        setupMenu(lblLicencias, "Licencias");

        // Por defecto activa Perfil
        activarMenu(lblPerfil, "Perfil");
    }

    private void setupMenu(Label label, String seccion) {
        label.setOnMouseClicked(e -> activarMenu(label, seccion));
        label.setStyle("-fx-font-family: 'Inter Semibold'; -fx-font-size: 26px; -fx-text-fill: #888; -fx-underline: true; -fx-cursor: hand;");
    }

    private void activarMenu(Label seleccionado, String seccion) {
        for (Label l : menuLabels) {
            l.setStyle("-fx-font-family: 'Inter Semibold'; -fx-font-size: 26px; -fx-text-fill: #888; -fx-underline: true; -fx-cursor: hand;");
        }
        seleccionado.setStyle("-fx-font-family: 'Inter Semibold'; -fx-font-size: 26px; -fx-text-fill: #222; -fx-underline: true; -fx-background-color: #ececec; -fx-background-radius: 12; -fx-cursor: hand;");

        // Cambia el contenido dinámico
        panelContenido.getChildren().removeIf(n -> n instanceof Label); // Quita textos anteriores, mantiene el encabezado con el engranaje y línea
        Label contenido = new Label("Estás en la sección: " + seccion);
        contenido.setStyle("-fx-font-size: 20px; -fx-text-fill: #444; -fx-padding: 40 0 0 42;");
        panelContenido.getChildren().add(contenido);
    }
    
    @FXML
    private void volver() {
        Navegador.irAFXML(
            "inteziapp/ui/fxml/DashboardUI.fxml",
            stage,
            "Dashboard - Inteziapp",
            inteziapp.ui.DashboardUIController.class,
            c -> c.inicializar(usuario, stage)
        );
    }
}