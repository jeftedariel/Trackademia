/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package edu.utn.trackademia.entities;

/**
 *
 * @author jefte
 */
public record User(int idUsuario, String email, String password, int role, String name, String surname) {

}
