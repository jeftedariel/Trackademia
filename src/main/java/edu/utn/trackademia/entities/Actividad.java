/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.entities;

import java.sql.Date;

/**
 *
 * @author alexledezma
 */
public record Actividad(int id_actividad, String nombre_actividad, String descripcion, int valor, Date fecha_entrega) {

    public int getId_actividad() {
        return id_actividad;
    }

    public String getNombre_actividad() {
        return nombre_actividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getValor() {
        return valor;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }
    
}
