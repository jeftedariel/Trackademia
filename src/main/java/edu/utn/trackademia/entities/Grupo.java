/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.entities;

/**
 *
 * @author alexledezma
 */
public record Grupo(
    int idGrupo,
    int numeroGrupo,
    int numeroAula,
    String horario,
    int capacidadMaxima,
    String curso,
    int creditos,
    int horas_lectivas){

    public int getIdGrupo() {
        return idGrupo;
    }

    public int getNumeroGrupo() {
        return numeroGrupo;
    }

    public int getNumeroAula() {
        return numeroAula;
    }

    public String getHorario() {
        return horario;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public String getCurso() {
        return curso;
    }

    public int getCreditos() {
        return creditos;
    }

    public int getHoras_lectivas() {
        return horas_lectivas;
    }
    
}
