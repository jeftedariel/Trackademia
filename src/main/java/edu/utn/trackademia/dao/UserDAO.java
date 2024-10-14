/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.dao;

import edu.utn.trackademia.database.DBAdapterFactory;
import edu.utn.trackademia.database.IDBAdapter;
import edu.utn.trackademia.entities.User;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jefte
 */

public class UserDAO {

    private IDBAdapter adapter;

    public UserDAO() {
        this.adapter = DBAdapterFactory.getAdapter();
    }
    
    public boolean authUser(String email, String password){
        for (User n : getUsers()) {
             if(n.email().equals(email) && n.password().equals(password)){
                 return true;
             }
        }
        return false;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            String consultSQL = "SELECT u.correo, u.contrasena, u.rol, p.id_persona, p.Nombre, p.Apellidos, p.Telefono, p.fecha_nacimiento FROM Usuarios u JOIN Personas p ON u.persona = p.id_persona;";
            Connection connection = this.adapter.getConnection();
            PreparedStatement ps = connection.prepareStatement(consultSQL);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String email =  resultSet.getString("correo");
                String password = resultSet.getString("contraseña");
                String role = resultSet.getString("rol");
                String name = resultSet.getString("Nombre");
                String surname = resultSet.getString("Apellidos");
                
                users.add(new User(email, password, role, name, surname));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }

        return users;
    }
}
