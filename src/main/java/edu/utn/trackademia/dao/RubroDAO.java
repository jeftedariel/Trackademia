/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.dao;

import edu.utn.trackademia.controller.RubrosController;
import edu.utn.trackademia.database.DBAdapterFactory;
import edu.utn.trackademia.database.IDBAdapter;
import edu.utn.trackademia.entities.Rubro;
import java.sql.Connection;
import java.sql.DriverManager;
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
            String consultSQL = "SELECT r.id_rubro, r.nombre, r.ponderacion FROM rubros r JOIN rubros_grupo rg ON r.id_rubro = rg.id_rubro WHERE rg.id_grupo = ?";

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

    public void removeEvaluation(int id_rubro) {
        try {
            String query = "DELETE FROM rubros_grupo WHERE id_rubro = ? AND id_grupo = ?;";

            Connection connection = this.adapter.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id_rubro);
            ps.setInt(2, RubrosController.idGrupo);
            ps.execute();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            adapter.disconnect();
        }
    }

    public void addEvaluation(String name, int percentage, int idGrupo) {
        this.idGrupo = idGrupo;
        String insert1 = "INSERT INTO rubros (nombre, ponderacion) VALUES (?, ?)";
        String inser2 = "INSERT INTO rubros_grupo (id_rubro, id_grupo) VALUES (LAST_INSERT_ID(), ?)";
        Connection connection = this.adapter.getConnection();
        try (Connection conn = this.adapter.getConnection(); PreparedStatement psRubro = conn.prepareStatement(insert1); PreparedStatement psRubroGrupo = conn.prepareStatement(inser2)) {

            conn.setAutoCommit(false);

            psRubro.setString(1, name);
            psRubro.setInt(2, percentage);
            psRubro.executeUpdate();

            psRubroGrupo.setInt(1, idGrupo);
            psRubroGrupo.executeUpdate();

            conn.commit();
        } catch (Exception e) {
            System.out.println("Error:" + e);

        }

    }
    
    public Boolean getCourseEvaluationType(int courseId) {
        try {
            String consultSQL = "select evaluacion_colegiada from cursos where id_curso=?";

            Connection connection = this.adapter.getConnection();
            PreparedStatement ps = connection.prepareStatement(consultSQL);
            ps.setInt(1, courseId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Boolean evaluacion_colegiada = Boolean.parseBoolean(resultSet.getString("evaluacion_colegiada"));
                return evaluacion_colegiada;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }

        return false;
    }
}
