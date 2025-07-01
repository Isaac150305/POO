/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inteziapp.ui;

import inteziapp.util.Navegador;
import java.io.InputStream;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class DashboardUIController {

    // Referencias a los elementos del FXML
    @FXML private StackPane previewTablas, previewReportes, previewReuniones;

    @FXML private HBox hboxUsuario;

    @FXML private ImageView imgTablas, imgReportes, imgReuniones;

    @FXML private Button btnMenu;

    @FXML private Label lblTablas, lblReportes, lblReuniones, lblUsuario;

    private ContextMenu menuConfiguracion;
    private ContextMenu menuUsuario;
    private final PauseTransition hideMenuDelay = new PauseTransition(Duration.millis(400));
    private String usuario;
    private Stage stage;

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
    
    private void inicializarMenuConfiguracion() {
        menuConfiguracion = new ContextMenu();
        MenuItem itemConfiguracion = new MenuItem("Configuración");

        itemConfiguracion.setOnAction(e -> {
            // Obtén el stage actual
            Stage stage = (Stage) btnMenu.getScene().getWindow();
            // Abre la ventana de configuración con Navegador
            Navegador.irAFXML(
                "inteziapp/ui/fxml/ConfiguracionUI.fxml",
                stage,
                "Configuración - Inteziapp",
                inteziapp.ui.ConfiguracionUIController.class,
                controller -> controller.inicializar(lblUsuario.getText(), stage)
            );
        });

        menuConfiguracion.getItems().add(itemConfiguracion);

        btnMenu.setOnMouseEntered((MouseEvent e) -> {
            menuConfiguracion.show(btnMenu, e.getScreenX(), e.getScreenY());
        });

        btnMenu.setOnMouseExited(e -> {
            // Cierra el menú si el mouse sale del área del botón (opcional)
            // Espera un poco para permitir hover en el menú
            btnMenu.setOnMouseMoved(ev -> {
                if (!menuConfiguracion.isShowing()) {
                    menuConfiguracion.hide();
                }
            });
        });
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
    
    // Llamado por el Navegador
    public void inicializar(String usuario, Stage stage) {
        this.usuario = usuario;
        this.stage = stage;
        cargarPreviews();
        inicializarMenuUsuario();
        inicializarMenuConfiguracion();
        lblUsuario.setText(usuario.split("@")[0]);
    }

    private void cargarPreviews() {
        // Asume que las imágenes están en src/main/resources/inteziapp/ui/images/
        asignarPreview(previewTablas, imgTablas, lblTablas, "Tablas", "/inteziapp/ui/images/Tablas.png",
            () -> Navegador.irAFXML("inteziapp/ui/fxml/TablasUI.fxml", stage, "Tablas - Inteziapp", TablasUIController.class, c -> c.inicializar(usuario, stage))
        );

        asignarPreview(previewReportes, imgReportes, lblReportes, "Reportes", "/inteziapp/ui/images/Reportes.png",
            () -> Navegador.irAFXML("inteziapp/ui/fxml/ReportesUI.fxml", stage, "Reportes - Inteziapp", ReportesUIController.class, c -> c.inicializar(usuario, stage))
        );

        asignarPreview(previewReuniones, imgReuniones, lblReuniones, "Reuniones", "/inteziapp/ui/images/Reuniones.png",
            () -> Navegador.irAFXML("inteziapp/ui/fxml/ReunionUI.fxml", stage, "Reunión - Inteziapp", ReunionUIController.class, c -> c.inicializar(usuario, stage))
        );
    }

    private void asignarPreview(StackPane preview, ImageView img, Label lbl, String titulo, String ruta, Runnable accion) {
        // Carga la imagen
        InputStream prueba = getClass().getResourceAsStream(ruta);
        System.out.println("¿Existe " + ruta + "? " + (prueba != null));
        if (prueba != null) {
            img.setImage(new Image(prueba));
        } else {
            System.err.println("No se pudo cargar la imagen de preview: " + ruta);
            // Opcional: pon una imagen default o un color
            // img.setImage(new Image(...));
        }
        try {
            img.setImage(new Image(getClass().getResourceAsStream(ruta)));
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de preview: " + ruta);
        }
        lbl.setText(titulo);
        preview.setOnMouseClicked(e -> accion.run());
    }
}