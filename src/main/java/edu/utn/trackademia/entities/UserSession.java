/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.entities;

/**
 *
 * @author jefte
 */
public class UserSession {

    public static UserSession instance;
    String name, surname, email;
    int role;
    int idUsuario;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void login(int idUsuario, String name, String surname, String email, int role) {
     this.idUsuario = idUsuario;
     this.name = name;
     this.surname = surname;
     this.email = email;
     this.role = role;
    }
    
    public void logout(){
        this.name=null;
        this.surname=null;
        this.email=null;
        this.role=0;
    }
    
    public int getIdUsuario() {
        return idUsuario; 
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
      
    public String getName() {
        return name;
    }

    public String getUserFullName(){
        String fullname = "";
        
        if(this.getName() != null){
            fullname += this.getName() + " ";
        }
        
        if(this.getSurname() != null){
            fullname += this.getSurname();
        }
        
        return fullname;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}
