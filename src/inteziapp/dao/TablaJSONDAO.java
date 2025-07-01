/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteziapp.dao;

/**
 *
 * @author chris
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Aqui cumplimos con el principio (Single responsibility) ya que solo manejamos la persistencia en los archivos JSON
// ademas tambien cumplimos con el principio (Liskov Substitution) ya que pueden remplazar a su interfaz TablaDAO
public class TablaJSONDAO implements TablaDAO {
    private static final String ARCHIVO = "tabla.dat";

    //Aqui aplicamos el principio (Open/Closed) ya que agregamos funcionalidad sin modificar el resto del sistema
    @Override
    public void guardar(List<List<String>> datos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(datos);
        } catch (IOException e) {}
    }

    @Override
    public List<List<String>> cargar() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<List<String>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}