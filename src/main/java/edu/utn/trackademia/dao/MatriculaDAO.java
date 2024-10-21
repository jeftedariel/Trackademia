/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.dao;

import edu.utn.trackademia.database.DBAdapterFactory;
import edu.utn.trackademia.database.IDBAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author alexledezma
 */
public class MatriculaDAO {
    
    private IDBAdapter adapter;

    public MatriculaDAO() {
        this.adapter = DBAdapterFactory.getAdapter();
    }
    
    public int insertarMatricula(int idUsuario) throws SQLException {
        String query = "INSERT INTO matriculas (id_estudiante, fecha_matricula) VALUES (?, NOW())";
        Connection connection = this.adapter.getConnection();
        
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Return the ID of the generated
            }
            throw new SQLException("No se pudo obtener el ID de la matrícula generada.");
        }
    }

    public void insertarMatriculaGrupo(int idMatricula, int idGrupo) throws SQLException {
        String query = "INSERT INTO matriculas_grupos (id_matricula, id_grupo) VALUES (?, ?)";
        Connection connection = this.adapter.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idMatricula);
            ps.setInt(2, idGrupo);
            ps.executeUpdate();
        }
    }
    
}
