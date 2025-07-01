/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inteziapp;

/**
 *
 * @author chris
 */

import inteziapp.servicio.Autorizacion;
import inteziapp.ui.LoginUIController;
import inteziapp.util.Navegador;
import javafx.application.Application;
import javafx.stage.Stage;

public class Inteziapp extends Application {

    @Override
    public void start(Stage stage) {
        // Usuarios precargados
        Autorizacion.registrarUsuario("chris@intezia.com", "clave123");
        Autorizacion.registrarUsuario("isaac@intezia.com", "clave456");
        Autorizacion.registrarUsuario("lia@intezia.com", "clave789");
        Autorizacion.registrarUsuario("a", "1");

        // Ir al login (FXML)
        Navegador.irAFXML(
            "inteziapp/ui/fxml/LoginUI.fxml",
            stage,
            "Login - Inteziapp",
            LoginUIController.class,
            controller -> controller.inicializar(stage)  // <--- Este método debes definirlo en el controller
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/* Hecho por: Christian Vilera 32.235.441
              Isaac Rodriguez 30.330.878  

⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠤⠶⣶⣶⣶⡒⠶⣄⡀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⢠⡞⠁⠀⠀⠘⡿⠛⠻⣆⠹⡏⢱⡄⠀⠀
⠀⠀⠀⠀⠀⠀⢀⣠⣿⣷⣤⠀⠀⠀⠀⢠⡶⣮⠀⡇⢸⠃⠀⠀
⠀⠀⠀⠀⠀⠀⡏⣄⢻⡏⠁⣀⣄⠀⠀⠘⠉⠙⢧⡇⣿⠀⠀⠀
⠀⠀⠀⠀⠀⠀⣧⢻⣄⠱⡈⣿⠟⠀⢠⣶⣶⠀⠀⢹⣾⡧⠀⠀
⠀⠀⠀⠀⠀⠀⢻⡄⠉⠙⠻⡇⡆⠀⠀⠙⢿⠴⣲⡏⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠉⠉⢙⣶⣤⡗⠒⠀⠚⠓⠊⠹⣷⠀⠀⠀⠀
⠀⢀⣀⠀⠀⠀⠀⣠⡞⠉⠟⠀⠀⠀⠀⠀⠀⠀⠀⢿⡇⠀⠀⠀
⣴⡷⢋⣴⠆⢀⣾⢻⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⠀⠀⠀
⠈⢀⣾⠁⢠⠟⠁⠈⢿⣆⠀⠀⠀⢀⣀⠀⠀⠀⠀⢈⢻⠀⠀⠀
⠀⢸⣿⢀⡟⠀⠀⠀⢸⣿⣦⠀⠀⠈⡟⠀⠀⠀⣠⡿⠀⢇⠀⠀
⠀⠘⣿⣼⡇⠀⠀⠀⣼⣿⣿⠀⠀⠀⣧⣀⣤⣾⣿⠀⠀⢸⡀⠀
⠀⠀⠹⣿⣧⣠⣶⣶⣿⣿⣿⠆⠀⢀⣼⡿⠟⠉⡼⠀⠠⠾⢧⡀
⠀⠀⠀⠈⠻⡉⢀⠀⣄⢈⡇⠀⠛⠋⢁⢣⠀⠀⠱⣤⡀⣳⢱⣹
⠀⠀⠀⠀⠀⠉⠉⠁⠉⠁⠙⠲⠤⠼⠼⠟⠀⠀⠀⠀⠉⠉⠉⠀
*/