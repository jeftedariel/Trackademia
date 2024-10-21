/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.dao;

import edu.utn.trackademia.database.DBAdapterFactory;
import edu.utn.trackademia.database.IDBAdapter;
import edu.utn.trackademia.entities.User;
import edu.utn.trackademia.entities.UserSession;

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
    
    
    //Validates that users exists and has that password.
    public boolean authUser(String email, String password) {
    for (User n : getUsers()) {
        if (n.email().equals(email) && n.password().equals(password)) {
            // Assuming you have a method to retrieve user ID (n.idUsuario())
            UserSession.getInstance().login(n.idUsuario(), n.name(), n.surname(), n.email(), n.role());
            return true;
        }
    }
    return false;
}

    //Getting a list of users (?) obvious
    public List<User> getUsers() {
    List<User> users = new ArrayList<>();
    try {
        // Update the SQL query to select the user ID (assumed to be `id_usuario`)
        String consultSQL = "SELECT u.id_usuario, u.correo, u.contraseña, u.rol, p.Nombre, p.Apellidos, p.Telefono, p.fecha_nacimiento " +
                            "FROM usuarios u LEFT JOIN personas p ON u.persona = p.id_persona;";
        Connection connection = this.adapter.getConnection();
        PreparedStatement ps = connection.prepareStatement(consultSQL);
        ResultSet resultSet = ps.executeQuery();
        
        while (resultSet.next()) {
            // Retrieve the id_usuario
            int idUsuario = resultSet.getInt("id_usuario");
            String email = resultSet.getString("correo");
            String password = resultSet.getString("contraseña");
            int role = resultSet.getInt("rol");
            String name = resultSet.getString("Nombre");
            String surname = resultSet.getString("Apellidos");
            
            // Create a User instance with the idUsuario
            users.add(new User(idUsuario, email, password, role, name, surname));
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        adapter.disconnect();
    }

       return users;
    }
}
