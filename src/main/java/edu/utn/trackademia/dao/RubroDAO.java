/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.dao;

import edu.utn.trackademia.database.DBAdapterFactory;
import edu.utn.trackademia.database.IDBAdapter;
import edu.utn.trackademia.entities.Rubro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexledezma
 */
public class RubroDAO {
    
    private int idGrupo;
    
    private IDBAdapter adapter;

    public RubroDAO() {
        this.adapter = DBAdapterFactory.getAdapter();
    }
    
    public List<Rubro> getRubros(int idGrupo) {
        
        List<Rubro> rubros = new ArrayList<>();
        
        try {
             String consultSQL = "SELECT r.id_rubro, r.nombre, r.ponderacion " +
                    "FROM rubros r " +
                    "JOIN rubros_grupo rg ON r.id_rubro = rg.id_rubro " +
                    "WHERE rg.id_grupo = ?";
             
             Connection connection = this.adapter.getConnection();
             PreparedStatement ps = connection.prepareStatement(consultSQL);
             ps.setInt(1, idGrupo);
             ResultSet resultSet = ps.executeQuery(); 

             while (resultSet.next()) {
                int id_rubro = resultSet.getInt("id_rubro");
                String nombre = resultSet.getString("nombre");  
                int ponderacion = resultSet.getInt("ponderacion");    

                Rubro rubro = new Rubro(id_rubro, nombre, ponderacion);
                rubros.add(rubro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }
        return rubros;
    }
    
}
