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
        } finally {

            adapter.disconnect();
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

        adapter.disconnect();
    }

    public void deleteEnrollment(int enrollment_id) {
        String delete1 = "DELETE FROM matriculas WHERE id_matricula = ?";
        String delete2 = "DELETE FROM matriculas_grupos WHERE id_matricula = ?";
        Connection connection = this.adapter.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(delete1)) {
            ps.setInt(1, enrollment_id);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error:" + e);
        }

        try (PreparedStatement ps = connection.prepareStatement(delete2)) {
            ps.setInt(1, enrollment_id);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Error:" + e);
        }

        adapter.disconnect();
    }

    public int getIdEnrollment(int group_number, int student_id) {
        String query = "SELECT mg.id_matricula  FROM matriculas_grupos mg JOIN matriculas m ON mg.id_matricula = m.id_matricula JOIN grupos g ON mg.id_grupo = g.id_grupo WHERE g.numero_grupo = ? AND m.id_estudiante = ?";
        Connection connection = this.adapter.getConnection();
        int id_matricula=0;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, group_number);
            ps.setInt(2, student_id);;

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                id_matricula = resultSet.getInt("id_matricula");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }

        return id_matricula;
    }

}
