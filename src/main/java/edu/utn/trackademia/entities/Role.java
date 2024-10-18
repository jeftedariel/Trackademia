/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package edu.utn.trackademia.entities;

import java.util.Set;

/**
 *
 * @author jefte
 */
public record Role(String name, Set<Permission> permissions) {
}
