/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inteziapp.ui;

import inteziapp.util.Navegador;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ReportesUIController {

    @FXML private HBox hboxUsuario;
    
    @FXML private Button btnGenerarLunes;
    @FXML private Button btnGenerarViernes;
    
    @FXML private Label lblUsuario;
    
    private ContextMenu menuUsuario;
    private final PauseTransition hideMenuDelay = new PauseTransition(Duration.millis(400));
    private String usuario;
    private Stage stage;
    
    public void inicializar(String usuario, Stage stage) {
        this.usuario = usuario;
        this.stage = stage;
        inicializarMenuUsuario();
        lblUsuario.setText(usuario.split("@")[0]);
        
        DayOfWeek hoy = LocalDate.now().getDayOfWeek();

        // Mensaje para el tooltip
        Tooltip tipLunes = new Tooltip("Solo disponible los lunes");
        Tooltip tipViernes = new Tooltip("Solo disponible los viernes");

        // Habilita o deshabilita según el día, y asigna tooltip si está deshabilitado
        if (hoy != DayOfWeek.MONDAY) {
            btnGenerarLunes.setDisable(true);
            btnGenerarLunes.setTooltip(tipLunes);
        } else {
            btnGenerarLunes.setDisable(false);
            btnGenerarLunes.setTooltip(null);
        }

        if (hoy != DayOfWeek.FRIDAY) {
            btnGenerarViernes.setDisable(true);
            btnGenerarViernes.setTooltip(tipViernes);
        } else {
            btnGenerarViernes.setDisable(false);
            btnGenerarViernes.setTooltip(null);
        }
    }

    private void cerrarSesion() {
        // Cierra la ventana actual (dashboard)
        stage.close();

        // Abre login en un Stage NUEVO
        Stage loginStage = new Stage();
        Navegador.irAFXML(
            "inteziapp/ui/fxml/LoginUI.fxml",
            loginStage,
            "Login - Inteziapp",
            inteziapp.ui.LoginUIController.class,
            controller -> controller.inicializar(loginStage)
        );
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
    
    private void inicializarMenuUsuario() {
        menuUsuario = new ContextMenu();
        MenuItem itemCerrarSesion = new MenuItem("Cerrar sesión");
        itemCerrarSesion.setOnAction(e -> cerrarSesion());
        menuUsuario.getItems().add(itemCerrarSesion);

        // Mostrar menú al entrar
        hboxUsuario.setOnMouseEntered(ev -> {
            hideMenuDelay.stop();
            if (!menuUsuario.isShowing()) {
                menuUsuario.show(hboxUsuario, javafx.geometry.Side.BOTTOM, 0, 0);
            }
        });

        // Iniciar delay para ocultar menú al salir
        hboxUsuario.setOnMouseExited(ev -> {
            hideMenuDelay.setOnFinished(e -> menuUsuario.hide());
            hideMenuDelay.playFromStart();
        });

        // Si vuelve a entrar, cancelar delay
        menuUsuario.setOnShowing(e -> hideMenuDelay.stop());
        menuUsuario.setOnHiding(e -> hideMenuDelay.stop());
    }
    
    @FXML
    private void mostrarFormulario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inteziapp/ui/fxml/FormularioReporte.fxml"));
            Parent root = loader.load();

            // Pasa el usuario al controlador del formulario
            FormularioReporteController controller = loader.getController();
            controller.inicializar(usuario);

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Nuevo Reporte");
            dialog.setScene(new Scene(root));
            dialog.setResizable(false);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}