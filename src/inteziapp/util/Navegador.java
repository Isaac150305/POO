/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteziapp.util;

/**
 *
 * @author chris
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class Navegador {

    /**
     * @param layout
     * @param stage
     * @param tituloVentana
     * @param transparente
     */
    
    public static void aplicarEscena(Parent layout, Stage stage, String tituloVentana, boolean transparente) {
        Scene oldScene = stage.getScene();
        double ancho = (oldScene == null) ? 400 : oldScene.getWidth();
        double alto = (oldScene == null) ? 300 : oldScene.getHeight();

        Scene scene = new Scene(layout, ancho, alto);

        // Estilo CSS global
        URL cssURL = Navegador.class.getResource("/inteziapp/ui/fxml/login.css");
        if (cssURL != null) {
            scene.getStylesheets().add(cssURL.toExternalForm());
        } else {
            System.err.println("❌ No se encontró el archivo login.css");
        }

        // Ícono de la ventana
        if (stage.getIcons().isEmpty()) {
            Image icon = new Image("https://th.bing.com/th/id/OIP.E-JcM3sDq6QI4zSe2aX5ZAHaFg?pid=ImgDet&w=474&h=352&rs=1");
            stage.getIcons().add(icon);
        }

        // Maneja la transparencia solo para la ventana que lo necesita
        if (!stage.isShowing()) {
            if (transparente) {
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
            } else {
                stage.initStyle(StageStyle.DECORATED);
            }
        }

        stage.setScene(scene);
        stage.setTitle(tituloVentana);
        stage.show();
    }

    /**
     * @param <T>
     * @param rutaFXML
     * @param stage
     * @param titulo
     * @param claseController
     * @param configurador
     */
    
    public static <T> void irAFXML(String rutaFXML, Stage stage, String titulo, Class<T> claseController, Consumer<T> configurador) {
        try {
            URL fxmlUrl = Navegador.class.getClassLoader().getResource(rutaFXML);
            System.out.println("FXML URL encontrado: " + fxmlUrl);
            
            FXMLLoader loader = new FXMLLoader(Navegador.class.getClassLoader().getResource(rutaFXML));
            Parent root = loader.load();

            T controller = loader.getController();
            configurador.accept(controller);

            // Solo el login es transparente, lo demás normal
            boolean transparente = rutaFXML.toLowerCase().contains("loginui.fxml");
            aplicarEscena(root, stage, titulo, transparente);

        } catch (IOException | RuntimeException e) {
          System.err.println("❌ Error al cargar FXML: " + rutaFXML);
          e.printStackTrace();// Esto mostrará el error exacto
        }
    }
}
