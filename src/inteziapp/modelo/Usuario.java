/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteziapp.modelo;

/**
 *
 * @author chris
 */

//Aqui cumplimos con el principio (Single responsibility) ya que solo definimos entidades
public class Usuario {
    private String usuario;
    private String clave;

    public Usuario(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public boolean verificarClave(String input) {
        return clave.equals(input);
    }

    public String getUsuario() {
        return usuario;
    }
    
    @Override
    public String toString() {
        return usuario;
    }
}
