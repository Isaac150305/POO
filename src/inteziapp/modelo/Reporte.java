/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteziapp.modelo;

/**
 *
 * @author chris
 */

import java.io.Serializable;
import java.time.LocalDate;

//Aqui cumplimos con el principio (Single responsibility) ya que solo definimos entidades

public class Reporte implements Serializable {
    private String autor;
    private LocalDate fecha;
    private String r1, r2, r3, r4, r5;

    public Reporte(String autor, LocalDate fecha, String r1, String r2, String r3, String r4, String r5) {
        this.autor = autor;
        this.fecha = fecha;
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.r4 = r4;
        this.r5 = r5;
    }

    public String getAutor() { return autor; }
    public LocalDate getFecha() { return fecha; }
    public String getR1() { return r1; }
    public String getR2() { return r2; }
    public String getR3() { return r3; }
    public String getR4() { return r4; }
    public String getR5() { return r5; }
}