/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.entities;

import java.time.LocalDate;

/**
 *
 * @author alexledezma
 */
public record Matricula(int idMatricula, int idEstudiante, LocalDate fechaMatricula) {
    
}
