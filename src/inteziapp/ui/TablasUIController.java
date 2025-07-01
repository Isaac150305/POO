/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package inteziapp.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import inteziapp.dao.TablaDAO;
import inteziapp.dao.TablaJSONDAO;
import inteziapp.util.Navegador;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class TablasUIController {

    @FXML private TableView<ObservableList<String>> tabla;
    
    @FXML private Button btnAgregarFila, btnEliminarFila, btnAgregarColumna, btnEliminarColumna, btnGuardar, btnMenu;
    
    @FXML private Label lblEstado, lblUsuario;
    
    @FXML private HBox hboxUsuario;

    private TablaDAO dao = new TablaJSONDAO();
    private ObservableList<ObservableList<String>> datos;
    private int columnasActuales = 0;
    private ContextMenu menuConfiguracion;
    private ContextMenu menuUsuario;
    private final PauseTransition hideMenuDelay = new PauseTransition(Duration.millis(400));
    private String usuario;
    private Stage stage;

    public void inicializar(String usuario, Stage stage) {
        this.usuario = usuario;
        this.stage = stage;
        inicializarMenuUsuario();
        inicializarMenuConfiguracion();
        cargarDatos();
    }

    @FXML
    private void initialize() {
        // No llamar aquí a cargarDatos porque aún no tenemos el usuario ni el stage.
        btnAgregarFila.setOnAction(e -> agregarFila());
        btnEliminarFila.setOnAction(e -> eliminarFila());
        btnAgregarColumna.setOnAction(e -> agregarColumna());
        btnEliminarColumna.setOnAction(e -> eliminarColumna());
        btnGuardar.setOnAction(e -> guardarTabla());
    }

    private void cargarDatos() {
        List<List<String>> lista = dao.cargar();
        if (lista == null || lista.isEmpty()) {
            // Si no hay datos, crea una tabla de 6x6 vacía
            lista = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                List<String> fila = new ArrayList<>();
                for (int j = 0; j < 6; j++) fila.add("");
                lista.add(fila);
            }
        }
        columnasActuales = lista.get(0).size();
        datos = FXCollections.observableArrayList();
        for (List<String> fila : lista) {
            ObservableList<String> obsFila = FXCollections.observableArrayList(fila);
            datos.add(obsFila);
        }
        tabla.getColumns().clear();

        for (int col = 0; col < columnasActuales; col++) {
            final int idx = col;
            TableColumn<ObservableList<String>, String> columna = new TableColumn<>(String.valueOf((char)('A' + col)));
            columna.setCellValueFactory(data -> new SimpleStringProperty(
                    idx < data.getValue().size() ? data.getValue().get(idx) : ""));
            columna.setCellFactory(tc -> {
                TableCell<ObservableList<String>, String> cell = new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : item);
                    }
                };
                cell.setOnMouseClicked(event -> {
                    if (!cell.isEmpty()) tabla.edit(cell.getIndex(), tc);
                });
                return cell;
            });
            columna.setCellFactory(TextFieldTableCell.forTableColumn());
            columna.setOnEditCommit(evt -> {
                evt.getRowValue().set(idx, evt.getNewValue());
            });
            tabla.getColumns().add(columna);
        }
        tabla.setItems(datos);
    }

    private void agregarFila() {
        ObservableList<String> nueva = FXCollections.observableArrayList();
        for (int i = 0; i < columnasActuales; i++) nueva.add("");
        datos.add(nueva);
        tabla.refresh();
    }

    private void eliminarFila() {
        int idx = tabla.getSelectionModel().getSelectedIndex();
        if (idx >= 0 && datos.size() > 1) {
            datos.remove(idx);
            tabla.refresh();
        }
    }

    private void agregarColumna() {
        columnasActuales++;
        TableColumn<ObservableList<String>, String> columna = new TableColumn<>(String.valueOf((char)('A' + (columnasActuales-1))));
        final int idx = columnasActuales-1;
        columna.setCellValueFactory(data -> new SimpleStringProperty(
                idx < data.getValue().size() ? data.getValue().get(idx) : ""));
        columna.setCellFactory(TextFieldTableCell.forTableColumn());
        columna.setOnEditCommit(evt -> {
            ObservableList<String> row = evt.getRowValue();
            while (row.size() <= idx) row.add("");
            row.set(idx, evt.getNewValue());
        });
        tabla.getColumns().add(columna);
        for (ObservableList<String> fila : datos) {
            while (fila.size() < columnasActuales) fila.add("");
        }
        tabla.refresh();
    }

    private void eliminarColumna() {
        if (columnasActuales <= 1) return;
        int lastCol = columnasActuales - 1;
        tabla.getColumns().remove(lastCol);
        for (ObservableList<String> fila : datos) {
            if (fila.size() > lastCol) fila.remove(lastCol);
        }
        columnasActuales--;
        tabla.refresh();
    }

    private void guardarTabla() {
        List<List<String>> aGuardar = new ArrayList<>();
        for (ObservableList<String> fila : datos) {
            aGuardar.add(new ArrayList<>(fila));
        }
        dao.guardar(aGuardar);
        lblEstado.setText("✅ Tabla guardada correctamente");
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
}