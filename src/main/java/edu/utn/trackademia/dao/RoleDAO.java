/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.dao;

import edu.utn.trackademia.database.DBAdapterFactory;
import edu.utn.trackademia.database.IDBAdapter;
import edu.utn.trackademia.entities.Permission;
import edu.utn.trackademia.entities.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jefte
 */
public class RoleDAO {

    private IDBAdapter adapter;

    public RoleDAO() {
        this.adapter = DBAdapterFactory.getAdapter();
    }
    
    
    //Return role name by givin it its id.
    public String getRole(int id_rol){
        String role_name="";
        try {
            String consultSQL = "SELECT nombre_rol from roles WHERE id_rol = ?";
            Connection connection = this.adapter.getConnection();
            PreparedStatement ps = connection.prepareStatement(consultSQL);
            ps.setInt(1, id_rol);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                role_name = resultSet.getString("nombre_rol");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }
        return role_name;
    }
    
    //Retrieve a list of existing roles
    //Can be used to obtain role`s permissions using .stream().anyMatch(n->n.name().equals("Pemission Name"))
    public Set<Role> getRoles() {
        Set<Role> roles = new HashSet<>();
        try {
            String consultSQL = "select * from roles";
            Connection connection = this.adapter.getConnection();
            PreparedStatement ps = connection.prepareStatement(consultSQL);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id_rol = resultSet.getInt("id_rol");
                String name = resultSet.getString("nombre_rol");
                roles.add(new Role(name, getPermissions(id_rol)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }
        return roles;
    }
    
    
    //A simple Return of a set of permissions
    public Set<Permission> getPermissions(int id_rol){
        Set<Permission> permissions = new HashSet<>();
        try {
            String consultSQL = "SELECT p.nombre_permiso FROM permisos_rol pr JOIN permisos p ON pr.id_permiso = p.id_permiso WHERE pr.id_rol = ?";
            Connection connection = this.adapter.getConnection();
            PreparedStatement ps = connection.prepareStatement(consultSQL);
            ps.setInt(1, id_rol);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                permissions.add(new Permission(resultSet.getString("nombre_permiso")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }
        return permissions;
    }
    
    
}
