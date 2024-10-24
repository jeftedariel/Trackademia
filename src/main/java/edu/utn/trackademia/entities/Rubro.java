/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.entities;

/**
 *
 * @author alexledezma
 */
public record Rubro(int id_rubro, String nombre, int ponderacion) {

    public int getId_rubro() {
        return id_rubro;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPonderacion() {
        return ponderacion;
    }
    
}
