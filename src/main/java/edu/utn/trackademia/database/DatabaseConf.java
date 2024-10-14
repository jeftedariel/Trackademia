/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package edu.utn.trackademia.database;

/**
 *
 * @author jefte
 */
public record DatabaseConf(String host, int port, String username, String database, String password, String dbtype) {
    // A simple record to store dbconf :)
}
