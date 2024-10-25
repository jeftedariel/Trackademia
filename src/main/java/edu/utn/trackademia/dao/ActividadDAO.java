/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.dao;

import edu.utn.trackademia.database.DBAdapterFactory;
import edu.utn.trackademia.database.IDBAdapter;
import edu.utn.trackademia.entities.Actividad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author alexledezma
 */
public class ActividadDAO {
    
    private IDBAdapter adapter;

    public ActividadDAO() {
        this.adapter = DBAdapterFactory.getAdapter();
    }
    
    public List<Actividad> getActividades(int idRubro) {
        
        List<Actividad> actividades = new ArrayList<>();
        
        try {
             String consultSQL = "SELECT a.id_actividad, a.nombre_actividad, a.descripcion, a.valor, a.fecha_entrega " +
                   "FROM actividades a " +
                   "INNER JOIN actividades_rubros ar ON a.id_actividad = ar.id_actividad " +
                   "INNER JOIN rubros r ON ar.id_rubro = r.id_rubro " +
                   "WHERE r.id_rubro = ?";
             
             Connection connection = this.adapter.getConnection();
             PreparedStatement ps = connection.prepareStatement(consultSQL);
             ps.setInt(1, idRubro);
             ResultSet resultSet = ps.executeQuery(); 

             while (resultSet.next()) {
                int id_actividad = resultSet.getInt("id_actividad");
                String nombre = resultSet.getString("nombre_actividad");  
                String descripcion = resultSet.getString("descripcion");
                int valor = resultSet.getInt("valor");
                Date fecha = resultSet.getDate("fecha_entrega");  

                Actividad actividad = new Actividad(id_actividad, nombre, descripcion, valor, fecha);
                actividades.add(actividad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }
        return actividades;
    }
    


    
}
