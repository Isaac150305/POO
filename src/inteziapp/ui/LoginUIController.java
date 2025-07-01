/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inteziapp.ui;

/**
 * FXML Controller class
 *
 * @author chris
 */

import inteziapp.servicio.Autorizacion;
import inteziapp.util.Navegador;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginUIController {

    private Stage stage; 

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtClaveOculta;
    @FXML private TextField txtClaveVisible;
    @FXML private Label lblStatus;
    @FXML private Label lblOlvidarClave;
    @FXML private Button btnLogin;
    @FXML private Button btnToggleClave;
    @FXML private Button btnSalir;

    private boolean mostrarClave = false;

    @FXML
    public void inicializar(Stage stage) {
        this.stage = stage; 
        
        btnSalir.setOnAction(e -> {
            javafx.application.Platform.exit();
        });
        
        stage.setHeight(650);
        stage.setWidth(400);
        stage.setResizable(false);

        btnToggleClave.setOnAction(e -> toggleClaveVisible());
        
        btnLogin.setOnAction(e -> {
            String usuario = txtUsuario.getText();
            String clave = mostrarClave ? txtClaveVisible.getText() : txtClaveOculta.getText();

            if (usuario.isEmpty() || clave.isEmpty()) {
                lblStatus.setText("⚠ Todos los campos son obligatorios.");
                return;
            }

            if (Autorizacion.autenticar(usuario, clave)) {
                // NUEVO: Abrir un nuevo Stage para Dashboard
                javafx.stage.Stage dashboardStage = new javafx.stage.Stage();
                dashboardStage.setHeight(720);
                dashboardStage.setWidth(1280);

                Navegador.irAFXML(
                    "inteziapp/ui/fxml/DashboardUI.fxml",
                    dashboardStage,
                    "Dashboard - Inteziapp",
                    inteziapp.ui.DashboardUIController.class,
                    controller -> controller.inicializar(usuario, dashboardStage)
                );
                // Cerrar la ventana de login
                stage.close();
            } else {
                lblStatus.setText("❌ Credenciales incorrectas");
            }
        });

        lblOlvidarClave.setOnMouseClicked(e ->
            lblStatus.setText("🔐 Contacta con soporte para recuperar tu clave.")
        );
    }

    private void toggleClaveVisible() {
        mostrarClave = !mostrarClave;
        if (mostrarClave) {
            txtClaveVisible.setText(txtClaveOculta.getText());
            txtClaveVisible.setVisible(true);
            txtClaveVisible.setManaged(true);
            txtClaveOculta.setVisible(false);
            txtClaveOculta.setManaged(false);
            // (opcional) Cambiar icono de ojo por ojo-tachado
        } else {
            txtClaveOculta.setText(txtClaveVisible.getText());
            txtClaveOculta.setVisible(true);
            txtClaveOculta.setManaged(true);
            txtClaveVisible.setVisible(false);
            txtClaveVisible.setManaged(false);
        // (opcional) Cambiar icono a ojo normal
        }
    }    
}