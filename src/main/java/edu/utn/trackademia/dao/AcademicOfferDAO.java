/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.dao;

import edu.utn.trackademia.database.IDBAdapter;
import edu.utn.trackademia.database.DBAdapterFactory;
import java.util.List;
import java.util.ArrayList;
import edu.utn.trackademia.entities.Grupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author alexledezma
 */
public class AcademicOfferDAO {

    private IDBAdapter adapter;

    public AcademicOfferDAO() {
        this.adapter = DBAdapterFactory.getAdapter();
    }

    public boolean hasAvailableGroups(int idUsuario) {
        return (getGrupos(idUsuario).stream().count() > 0);
    }

    public List<Grupo> getGrupos(int idUsuario) {
        List<Grupo> grupos = new ArrayList<>();
        try {
            String consultSQL = "SELECT g.id_grupo, g.numero_grupo, g.numero_aula, g.horario, g.capacidad_maxima, c.nombre_curso, c.creditos, c.horas_lectivas "+
                "FROM grupos g "+
                "INNER JOIN cursos c ON g.curso = c.id_curso "+
                "INNER JOIN cursos_carrera cc ON c.id_curso = cc.id_curso "+
                "INNER JOIN carreras ca ON cc.id_carrera = ca.id_carrera "+
                "INNER JOIN usuarios u ON u.nivel = c.nivel "+
                "WHERE u.id_usuario = ? "+
                "AND u.nivel = c.nivel "+
                "AND u.carrera = ca.id_carrera "+
                "AND NOT EXISTS ( "+
                "SELECT 1 "+
                "FROM matriculas_grupos mg "+
                "INNER JOIN matriculas m ON mg.id_matricula = m.id_matricula "+
                "WHERE mg.id_grupo = g.id_grupo "+
                "AND m.id_estudiante = u.id_usuario "+
                ")";

            Connection connection = this.adapter.getConnection();
            PreparedStatement ps = connection.prepareStatement(consultSQL);
            ps.setInt(1, idUsuario);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int idGrupo = resultSet.getInt("id_grupo");
                int numeroGrupo = resultSet.getInt("numero_grupo");
                int numeroAula = resultSet.getInt("numero_aula");
                String horario = resultSet.getString("horario");
                int capacidadMaxima = resultSet.getInt("capacidad_maxima");
                String nombreCurso = resultSet.getString("nombre_curso");
                int creditos = resultSet.getInt("creditos");
                int horasLectivas = resultSet.getInt("horas_lectivas");

                Grupo grupo = new Grupo(idGrupo, numeroGrupo, numeroAula, horario, capacidadMaxima, nombreCurso, creditos, horasLectivas);
                grupos.add(grupo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter.disconnect();
        }
        return grupos;
    }

}
