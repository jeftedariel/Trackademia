/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package edu.utn.trackademia.entities;

/**
 *
 * @author jefte
 */
public record EnrolledGroup(int idGroup, int groupNumber, String courseName, String teacher, int credits, String schedule, int classroom, String url, String platform, int hours) {

}
