/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteziapp.dao;

/**
 *
 * @author chris
 */

import inteziapp.modelo.Reporte;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Aqui cumplimos con el principio (Single responsibility) ya que solo manejamos la persistencia en los archivos JSON
// ademas tambien cumplimos con el principio (Liskov Substitution) ya que pueden remplazar a su interfaz ReporteDAO
public class ReporteJSONDAO implements ReporteDAO {
    private static final String RUTA = "reportes.dat";

    //Aqui aplicamos el principio (Open/Closed) ya que agregamos funcionalidad sin modificar el resto del sistema
    @Override
    public void guardar(Reporte reporte) {
        List<Reporte> reportes = cargarTodos();
        reportes.add(reporte);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA))) {
            oos.writeObject(reportes);
        } catch (IOException e) {}
    }

    @Override
    public List<Reporte> cargarTodos() {
        File archivo = new File(RUTA);
        if (!archivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA))) {
            return (List<Reporte>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
