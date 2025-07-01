/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteziapp.servicio;

/**
 *
 * @author chris
 */

import inteziapp.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

//Aqui cumplimos con el principio (Single responsibility) ya que solo manejamos verificacion de credenciales
public class Autorizacion {
    private static final List<Usuario> ListaUsuarios = new ArrayList<>();

    public static boolean autenticar(String usuario, String clave) {
        return ListaUsuarios.stream()
                .anyMatch(u -> u.getUsuario().equals(usuario) && u.verificarClave(clave));
    }

    public static void registrarUsuario(String usuario, String clave) {
        ListaUsuarios.add(new Usuario(usuario, clave));
    }
    
    public static List<Usuario> obtenerUsuariosRegistrados() {
    return new ArrayList<>(ListaUsuarios);
    }
}

