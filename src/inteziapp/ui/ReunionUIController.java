/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inteziapp.ui;

import inteziapp.util.Navegador;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;
import java.util.stream.Collectors;

public class ReunionUIController {

    @FXML private GridPane gridUsuarios;
    @FXML private StackPane areaSeleccionados;
    
    @FXML private Button btnAgendar;
    @FXML private Button btnVolver;
    
    @FXML private Label lblUsuario;
    @FXML private Label lblEstado;

    // Lista de usuarios de prueba
    private final String[] nombresUsuarios = {"Isaac", "Lia", "Chris", "Juan", "Rodrigo", "José"};
    private final String avatarRuta = "/inteziapp/ui/images/usuario.png";

    private final List<String> seleccionados = new ArrayList<>();
    private String usuario;
    private Stage stage;

    public void inicializar(String usuario, Stage stage) {
        this.usuario = usuario;
        this.stage = stage;
        lblUsuario.setText(usuario.contains("@") ? usuario.split("@")[0] : usuario);
        cargarUsuarios();
        btnAgendar.setOnAction(e -> agendarReunion());
        btnVolver.setOnAction(e -> volver());
    }

    private void cargarUsuarios() {
        gridUsuarios.getChildren().clear();
        int col = 0, row = 0;
        for (String nombre : nombresUsuarios) {
            VBox card = crearUsuarioCard(nombre);
            gridUsuarios.add(card, col, row);
            col++;
            if (col == 3) { col = 0; row++; }
        }
    }

    private VBox crearUsuarioCard(String nombre) {
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(avatarRuta)));
        img.setFitHeight(104); img.setFitWidth(104);
        img.setStyle("-fx-effect: dropshadow(gaussian, #e7e4e4, 7, 0.18, 0, 2);");
        Label lbl = new Label(nombre);
        lbl.setStyle("-fx-font-family: 'Inter Semibold'; -fx-font-size: 24px; -fx-text-fill: #bbb;");
        VBox card = new VBox(6, img, lbl);
        card.setAlignment(javafx.geometry.Pos.CENTER);
        card.setOnMouseClicked(e -> toggleSeleccion(nombre, card));
        card.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        return card;
    }

    private void toggleSeleccion(String nombre, VBox card) {
        if (seleccionados.contains(nombre)) {
            seleccionados.remove(nombre);
            card.setStyle("-fx-background-color: transparent;");
        } else {
            seleccionados.add(nombre);
            card.setStyle("-fx-background-color: #e2e0e0; -fx-background-radius: 20;");
        }
        actualizarAreaSeleccionados();
    }

    private void actualizarAreaSeleccionados() {
        areaSeleccionados.getChildren().clear();
        if (seleccionados.isEmpty()) {
            areaSeleccionados.getChildren().add(new Label("Ningun usuario seleccionado"));
        } else {
            FlowPane lista = new FlowPane(10, 12);
            lista.setPrefWrapLength(380);
            for (String nombre : seleccionados) {
                Label lbl = new Label(nombre);
                lbl.setStyle("-fx-font-family: 'Inter Semibold'; -fx-font-size: 20px; -fx-background-color: #fff; -fx-background-radius: 10; -fx-padding: 8 18 8 18; -fx-text-fill: #888; -fx-border-color: #ddd; -fx-border-radius: 10;");
                lista.getChildren().add(lbl);
            }
            areaSeleccionados.getChildren().add(lista);
        }
    }

    private void agendarReunion() {
    if (seleccionados.isEmpty()) {
        lblEstado.setStyle("-fx-text-fill: #c94d4d; -fx-font-size: 16px;");
        lblEstado.setText("⚠ Selecciona al menos un usuario para agendar la reunión.");
        return;
    }
    String nombres = String.join(", ", seleccionados);
    lblEstado.setStyle("-fx-text-fill: #72b568; -fx-font-size: 16px;");
    lblEstado.setText("✅ Reunión agendada con: " + nombres);

    // Limpiar la selección (opcional)
    seleccionados.clear();
    cargarUsuarios();
    actualizarAreaSeleccionados();
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