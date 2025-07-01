/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package inteziapp.dao;

/**
 *
 * @author chris
 */

import java.util.List;


//Aqui aplicamos el principio (Open/Closed) ya que permitimos nuevas implementaciones sin modificarse
// ademas tambien cumplimos con el principio (Interface Segregation) ya que como lo indica el nombre separamos por completo la interfaz
public interface TablaDAO {
    void guardar(List<List<String>> datos);
    List<List<String>> cargar();
}
