/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteziapp.ui;

import inteziapp.dao.ReporteDAO;
import inteziapp.dao.ReporteJSONDAO;
import inteziapp.modelo.Reporte;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class FormularioReporteController {

    @FXML private TextField p1;
    @FXML private TextField p2;
    @FXML private TextField p3;
    @FXML private TextField p4;
    @FXML private TextField p5;
    @FXML private Label lblEstado;
    @FXML private Label lblTitulo;

    private String usuario;
    private final ReporteDAO dao = new ReporteJSONDAO();

    public void inicializar(String usuario) {
        this.usuario = usuario;
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblTitulo.setText("Formulario del día " + hoy.format(formato));
    }

    @FXML
    private void guardarReporte() {
        if (p1.getText().isBlank() || p2.getText().isBlank() || p3.getText().isBlank()
                || p4.getText().isBlank() || p5.getText().isBlank()) {
            lblEstado.setText("⚠️ Todos los campos son obligatorios.");
            return;
        }

        Reporte nuevo = new Reporte(
            usuario,
            LocalDate.now(),
            p1.getText(),
            p2.getText(),
            p3.getText(),
            p4.getText(),
            p5.getText()
        );

        dao.guardar(nuevo);
        lblEstado.setText("✅ Reporte guardado correctamente");

        // Opcional: cerrar la ventana después de guardar con un pequeño delay
        Stage stage = (Stage) p1.getScene().getWindow();
        new Thread(() -> {
            try { Thread.sleep(800); } catch (InterruptedException ignored) {}
            javafx.application.Platform.runLater(stage::close);
        }).start();
    }

    @FXML
    private void cancelar() {
        // Cierra la ventana de formulario sin guardar
        Stage stage = (Stage) p1.getScene().getWindow();
        stage.close();
    }
}